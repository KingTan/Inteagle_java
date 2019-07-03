package com.inteagle.apis.loginInfo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.inteagle.common.base.dao.BaseMapper;
import com.inteagle.apis.loginInfo.entity.UserInfo;

@Repository
public interface UserInfoMapper extends BaseMapper<UserInfo> {
	int insert(UserInfo record);

	int insertSelective(UserInfo record);

	/**
	 * 
	 * @Title: getAllUserInfoList @Description: TODO(查询所有登录用户信息) @param @return
	 *         参数 @return List<UserInfo> 返回类型 @throws
	 */
	List<UserInfo> getAllUserInfoList();

	/**
	 * 
	 * @Title: getUserInfoByParamAndPwd @Description: TODO(根据身份证号,手机号或公司名称 和登录密码
	 *         查询登录用户信息) @param @param searchParam @param @param
	 *         passWord @param @return 参数 @return UserInfo 返回类型 @throws
	 */
	UserInfo getUserInfoByParamAndPwd(@Param("searchParam") String searchParam, @Param("passWord") String passWord);

	/**
	 * 
	 * @Title: getUserInfoByParam @Description: TODO(根据身份证号,手机号或公司名称
	 *         查询登录用户对象) @param @param searchParam @param @return 参数 @return
	 *         UserInfo 返回类型 @throws
	 */
	UserInfo getUserInfoByParam(String searchParam);

}