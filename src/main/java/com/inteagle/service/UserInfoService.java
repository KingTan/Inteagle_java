package com.inteagle.service;

import java.security.AlgorithmParameters;
import java.security.Security;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inteagle.common.exception.BusinessException;
import com.inteagle.common.redis.RedisService;
import com.inteagle.common.wechat.util.WeChatUtil;
import com.inteagle.config.app.AppConfig;
import com.inteagle.dao.userInfo.UserInfoMapper;
import com.inteagle.modal.base.BaseEntity;
import com.inteagle.modal.base.IdCardAuditEntity;
import com.inteagle.modal.base.JsonResult;
import com.inteagle.modal.sms.IdentityCodeEnum;
import com.inteagle.modal.userInfo.UserInfo;
import com.inteagle.service.base.AbstractService;
import com.inteagle.utils.Check_idCardAudit_util;
import com.inteagle.utils.Md5Util;
import com.inteagle.utils.ParamUtil;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Service
@SuppressWarnings({ "restriction", "unused" })
public class UserInfoService extends AbstractService<UserInfo, UserInfoMapper> {

	@Autowired
	private UserInfoMapper userInfoMapper;

	@Autowired
	private RedisService redisService;

	/**
	 * @description 获取微信手机号、快捷绑定登录或注册
	 * @author IVAn
	 * @date 2019年10月15日 下午6:58:14
	 * @param encryptedData
	 * @param session_key
	 * @param iv
	 * @return
	 */
	@Transactional
	public Map<String, Object> loginOrResgisterforMiniProgram(String encryptedData, String session_key, String iv,
			String openId, String wechat_userInfo) {

		Map<String, Object> return_map = new HashMap<String, Object>();

		byte[] dataByte = Base64.decode(encryptedData);
		byte[] keyByte = Base64.decode(session_key);
		byte[] ivByte = Base64.decode(iv);
		try {
			int base = 16;
			if (keyByte.length % base != 0) {
				int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
				byte[] temp = new byte[groups * base];
				Arrays.fill(temp, (byte) 0);
				System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
				keyByte = temp;
			}
			// 初始化
			Security.addProvider(new BouncyCastleProvider());
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
			AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
			parameters.init(new IvParameterSpec(ivByte));
			cipher.init(Cipher.DECRYPT_MODE, spec, parameters);
			byte[] resultByte = cipher.doFinal(dataByte);
			if (null != resultByte && resultByte.length > 0) {
				String result = new String(resultByte, "UTF-8");
				JSONObject jsonObject = JSONObject.parseObject(result);

				// 用户手机号
				String phoneNumer = jsonObject.getString("phoneNumber");
				// 根据手机号查询是否存在该用户
				UserInfo userInfoByPhone = userInfoMapper.getUserInfoByPhone(phoneNumer);

				if (userInfoByPhone != null) {
					// openid和已存在用户绑定
					userInfoByPhone.setOpenId(openId);
					userInfoMapper.updateOpenId(userInfoByPhone.getUserId(), openId);
					return_map.put("userInfo", userInfoByPhone);
					return return_map;
				} else {
					// 根据手机号注册新用户 设置默认密码
					// 当前微信用户对象
					JSONObject wechat_object = JSONObject.parseObject(wechat_userInfo);
					// 微信昵称
					String userName = wechat_object.getString("nickName");
					// 微信性别
					int gender = wechat_object.getInteger("gender");
					String gender_text = "";
					if (gender == 1) {
						gender_text = "男";
					} else if (gender == 2) {
						gender_text = "女";
					}
					// 微信头像
					String headPortrait = wechat_object.getString("avatarUrl");
					try {
						// 注册新用户
						UserInfo userInfo = new UserInfo();
						userInfo.setUserId(BaseEntity.getUUID());
						userInfo.setUserName(userName);
						userInfo.setPhone(phoneNumer);
						userInfo.setPassword(Md5Util.encoderByMd5("123456")); // 设置初始密码
						userInfo.setGender(gender_text);
						// 设置默认头像
						userInfo.setHeadPortrait(headPortrait);
						userInfo.setOpenId(openId);
						int register_result = userInfoMapper.registerForMiniProgram(userInfo);
						if (register_result > 0) {
							return_map.put("userInfo", userInfo);
							return return_map;
						}
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @description 微信小程序
	 * @author IVAn
	 * @date 2019年10月15日 下午5:03:52
	 * @param userName
	 * @param password
	 * @param openId
	 * @return
	 */
	@Transactional
	public UserInfo loginforMiniProgram(String userName, String password, String code) {
		// 当前登录用户对象
		UserInfo userInfo = getUserInfoByParamAndPwd(userName, password);
		// 根据小程序穿过来的code想这个url发送请求
		String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + AppConfig.appid + "&secret="
				+ AppConfig.secret + "&js_code=" + code + "&grant_type=authorization_code";
		// 发送请求，返回Json字符串
		String str = WeChatUtil.httpRequest(url, "GET", null);
		// 转成Json对象 获取openid
		JSONObject jsonObject = JSONObject.parseObject(str);

		String openid = jsonObject.get("openid").toString();
		userInfo.setOpenId(openid);

		userInfoMapper.updateOpenId(userInfo.getUserId(), openid);
		return userInfo;
	}

	/**
	 * @description 通过appId查询用户对象
	 * @author IVAn
	 * @date 2019年10月12日 下午5:10:00
	 * @param appId
	 * @return
	 */
	public UserInfo getUserInfoByOpenId(String appId) {
		return userInfoMapper.getUserInfoByOpenId(appId);
	}

	/**
	 * @description 通过手机号修改密码
	 * @author IVAn
	 * @date 2019年9月2日 下午5:23:10
	 * @param phone
	 * @param newPwd
	 * @param identityCode
	 * @return
	 */
	@Transactional
	public JsonResult<Object> updatePwdByPhone(String phone, String newPwd, String identityCode) {

		// 效验 验证码是否正确
		redisService.validateIdentifyingCode(phone, identityCode, IdentityCodeEnum.Forget.getValue());

		int result = userInfoMapper.updatePwdByPhoneNum(phone, newPwd);
		if (result > 0) {
			return new JsonResult<Object>(JsonResult.SUCCESS, "修改成功");
		}
		return new JsonResult<Object>(JsonResult.ERROR, "密码修改失败");
	}

	/**
	 * @description 注册新用户
	 * @author IVAn
	 * @date 2019年8月27日 下午6:55:04
	 * @param userName
	 * @param phone
	 * @param idCardNum
	 * @param password
	 * @param IdentityCode
	 * @param codeType
	 * @return
	 */
	@Transactional
	public JsonResult<Object> register(String userName, String phone, String idCardNum, String password,
			String IdentityCode, String codeType) {
		// 根据手机号查询是否已注册用户
		UserInfo exist_user = userInfoMapper.getUserInfoByPhone(phone);
		if (ParamUtil.notNullForParam(exist_user)) {
			throw new BusinessException("该手机号已注册过用户");
		}
		// 校验 验证码是否正确
		redisService.validateIdentifyingCode(phone, IdentityCode, codeType);
		// 错误次数
		int error_counts = 0;
		// 取出redis中的对象
		Object redis_idCardAudit_object = redisService.get(phone + "-idCardAudit");
		if (redis_idCardAudit_object != null) {
			JSONObject strObj = JSON.parseObject(redis_idCardAudit_object.toString());
			IdCardAuditEntity idCardAuditEntity_redis = JSON.toJavaObject(strObj, IdCardAuditEntity.class);
			if (idCardAuditEntity_redis != null) {
				error_counts = idCardAuditEntity_redis.getErrorCounts();
				if (error_counts > 2) {
					throw new BusinessException("该手机号实名认证失败次数已达上限,<br/>请24小时之后再注册");
				}
			}
		}
		Map<String, Object> result_map = Check_idCardAudit_util.check_idCardAudit(idCardNum, userName);
		// 匹配结果
		boolean identity_result = (boolean) result_map.get("result");
		// 性别
		String gender = result_map.get("gender").toString();
		// 效验身份证号是否正确
		if (!identity_result) {
			error_counts++;
			IdCardAuditEntity idCardAuditEntity = new IdCardAuditEntity(idCardNum, userName, phone, error_counts);
			// 保存到redis(保存时间为一天)
			redisService.set(idCardAuditEntity.getCacheKey(), JSON.toJSONString(idCardAuditEntity),
					Long.parseLong("1440"));
			throw new BusinessException("姓名与身份证信息不匹配");
		}

		try {
			// 注册新用户
			UserInfo userInfo = new UserInfo();
			userInfo.setUserName(userName);
			userInfo.setPhone(phone);
			userInfo.setIdCardNum(idCardNum);
			userInfo.setPassword(password);
			userInfo.setGender(gender);
			// 设置默认头像
			userInfo.setHeadPortrait("https://www.inteagle.com.cn/tag/default_icon.png");
			int result = userInfoMapper.register(userInfo);
			if (result > 0) {
				return new JsonResult<Object>(JsonResult.SUCCESS, "注册成功");
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return new JsonResult<Object>(JsonResult.ERROR_MESSAGE);
	}

	/**
	 * @description 修改用户信息
	 * @author IVAn
	 * @date 2019年9月18日 上午11:18:43
	 * @param userInfo
	 * @return
	 */
	public JsonResult<Object> updateUserInfo(UserInfo userInfo) {
		int result = userInfoMapper.updateUserInfo(userInfo);
		if (result > 0) {
			return new JsonResult<Object>(JsonResult.SUCCESS, "修改成功");
		}
		return new JsonResult<>(JsonResult.ERROR, "修改失败");
	}

	/**
	 * @description 根据手机号查询用户信息
	 * @author IVAn
	 * @date 2019年8月27日 下午6:49:01
	 * @param searchParam
	 * @param passWord
	 * @return
	 */
	public UserInfo getUserInfoByPhone(String phoneNumber) {
		return userInfoMapper.getUserInfoByPhone(phoneNumber);
	}

	/**
	 * @description 根据手机号验证码登录
	 * @author IVAn
	 * @date 2019年8月27日 下午6:49:01
	 * @param searchParam
	 * @param passWord
	 * @return
	 */
	public UserInfo loginByIndentityCode(String phoneNumber, String IndentityCode) {
		// 根据手机号查询是否已注册用户
		UserInfo exist_user = userInfoMapper.getUserInfoByPhone(phoneNumber);
		if (ParamUtil.isNullForParam(exist_user)) {
			throw new BusinessException("该手机号未注册用户");
		}
		// 校验 验证码是否正确
		redisService.validateIdentifyingCode(phoneNumber, IndentityCode, IdentityCodeEnum.Login.getValue());
		return exist_user;
	}

	/**
	 * @description 根据身份证号,手机号或公司名称 和登录密码 查询登录用户信息
	 * @author IVAn
	 * @date 2019年8月27日 下午6:49:01
	 * @param searchParam
	 * @param passWord
	 * @return
	 */
	public UserInfo getUserInfoByParamAndPwd(String searchParam, String passWord) {
		ParamUtil.validateParam(searchParam, "账户名不能为空");
		ParamUtil.validateParam(passWord, "密码不能为空");
		// 当前账户名查询的用户对象
		UserInfo userInfoByParam = userInfoMapper.getUserInfoByParam(searchParam);
		ParamUtil.validateParam(userInfoByParam, "用户名或手机号不存在");
		// 当前账户名、密码查询的用户对象
		UserInfo userInfo = userInfoMapper.getUserInfoByParamAndPwd(searchParam, passWord);
		ParamUtil.validateParam(userInfo, "密码有误");
		return userInfo;
	}

	/**
	 * @description 查询用户列表
	 * @author IVAn
	 * @date 2019年8月27日 下午6:49:10
	 * @return
	 */
	public List<UserInfo> getAllUserList() {
		return userInfoMapper.getAllUserInfoList();
	}

}
