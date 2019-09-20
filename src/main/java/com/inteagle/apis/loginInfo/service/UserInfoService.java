package com.inteagle.apis.loginInfo.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inteagle.common.base.service.AbstractService;
import com.inteagle.common.entity.JsonResult;
import com.inteagle.common.exception.BusinessException;
import com.inteagle.common.idCardAudit.entity.IdCardAuditEntity;
import com.inteagle.common.idCardAudit.util.Check_idCardAudit_util;
import com.inteagle.common.redis.RedisService;
import com.inteagle.common.sms.entity.IdentityCodeEnum;
import com.inteagle.common.util.ParamUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.inteagle.apis.loginInfo.dao.UserInfoMapper;
import com.inteagle.apis.loginInfo.entity.UserInfo;

@Service
public class UserInfoService extends AbstractService<UserInfo, UserInfoMapper> {

	@Autowired
	private UserInfoMapper userInfoMapper;

	@Autowired
	private RedisService redisService;

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
