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
	 * @description 注册
	 * @author IVAn
	 * @date 2019年8月27日 下午6:05:17
	 * @param userInfo
	 * @return
	 */
	int register(UserInfo userInfo);

	/**
	 * @description 查询所有用户列表
	 * @author IVAn
	 * @date 2019年8月27日 下午6:05:30
	 * @return
	 */
	List<UserInfo> getAllUserInfoList();

	/**
	 * @description (根据身份证号,手机号或公司名称 和登录密码 查询登录用户信息)
	 * @author IVAn
	 * @date 2019年8月27日 下午6:05:48
	 * @param searchParam
	 * @param passWord
	 * @return
	 */
	UserInfo getUserInfoByParamAndPwd(@Param("searchParam") String searchParam, @Param("passWord") String passWord);

	/**
	 * @description 根据身份证号,手机号或公司名称 查询登录用户对象
	 * @author IVAn
	 * @date 2019年8月27日 下午6:06:08
	 * @param searchParam
	 * @return
	 */
	UserInfo getUserInfoByParam(String searchParam);

	/**
	 * @description 通过手机号 查询用户
	 * @author IVAn
	 * @date 2019年8月27日 下午6:56:31
	 * @param phone
	 * @return
	 */
	UserInfo getUserInfoByPhone(String phone);

}