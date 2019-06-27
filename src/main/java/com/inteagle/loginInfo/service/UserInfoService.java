package com.inteagle.loginInfo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.inteagle.common.base.service.AbstractService;
import com.inteagle.common.util.ParamUtil;
import com.inteagle.loginInfo.dao.UserInfoMapper;
import com.inteagle.loginInfo.entity.UserInfo;

@Service
public class UserInfoService extends AbstractService<UserInfo, UserInfoMapper> {

	@Autowired
	private UserInfoMapper userInfoMapper;

	/**
	 * 
	 * @Title: getUserInfoByParamAndPwd @Description: TODO(根据身份证号,手机号或公司名称 和登录密码
	 *         查询登录用户信息) @param @param searchParam @param @param
	 *         passWord @param @return 参数 @return UserInfo 返回类型 @throws
	 */
	public UserInfo getUserInfoByParamAndPwd(String searchParam, String passWord) {
		ParamUtil.validateParam(searchParam, "账户名不能为空");
		ParamUtil.validateParam(passWord, "密码不能为空");
		// 当前账户名查询的用户对象
		UserInfo userInfoByParam = userInfoMapper.getUserInfoByParam(searchParam);
		ParamUtil.validateParam(userInfoByParam, "账户名不存在");
		// 当前账户名、密码查询的用户对象
		UserInfo userInfo = userInfoMapper.getUserInfoByParamAndPwd(searchParam, passWord);
		ParamUtil.validateParam(userInfo, "密码有误");
		return userInfo;
	}

	/**
	 * 
	 * @Title: getAllUserList @Description: TODO(查询所有登录用户信息) @param @return
	 *         参数 @return List<UserInfo> 返回类型 @throws
	 */
	public List<UserInfo> getAllUserList() {
		return userInfoMapper.getAllUserInfoList();
	}

}
