package com.inteagle.apis.loginInfo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inteagle.common.base.service.AbstractService;
import com.inteagle.common.entity.JsonResult;
import com.inteagle.common.exception.BusinessException;
import com.inteagle.common.redis.RedisService;
import com.inteagle.common.util.ParamUtil;
import com.inteagle.apis.loginInfo.dao.UserInfoMapper;
import com.inteagle.apis.loginInfo.entity.UserInfo;

@Service
public class UserInfoService extends AbstractService<UserInfo, UserInfoMapper> {

	@Autowired
	private UserInfoMapper userInfoMapper;

	@Autowired
	private RedisService redisService;

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
		try {
			// 注册新用户
			UserInfo userInfo = new UserInfo();
			userInfo.setUserName(userName);
			userInfo.setPhone(phone);
			userInfo.setIdCardNum(idCardNum);
			userInfo.setPassword(password);
			int result = userInfoMapper.register(userInfo);
			if (result > 0) {
				return new JsonResult<Object>(JsonResult.SUCCESS, "注册成功");
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return new JsonResult<Object>(JsonResult.ERROR, JsonResult.ERROR_MESSAGE);
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
