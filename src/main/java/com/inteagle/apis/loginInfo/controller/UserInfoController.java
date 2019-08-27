package com.inteagle.apis.loginInfo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.inteagle.common.entity.JsonResult;
import com.inteagle.common.sms.entity.IdentityCodeEnum;
import com.inteagle.apis.loginInfo.entity.UserInfo;
import com.inteagle.apis.loginInfo.service.UserInfoService;

@Controller
@RequestMapping("/userInfo")
public class UserInfoController {

	@Autowired
	private UserInfoService userInfoService;

	/**
	 * @description 登录
	 * @author IVAn
	 * @date 2019年8月27日 下午6:03:31
	 * @param searchParam
	 * @param passWord
	 * @return
	 */
	@RequestMapping("/login")
	@ResponseBody
	public JsonResult<UserInfo> getUserInfoByParamAndPwd(@RequestParam(value = "searchParam") String searchParam,
			@RequestParam(value = "passWord") String passWord) {
		return new JsonResult<UserInfo>(userInfoService.getUserInfoByParamAndPwd(searchParam, passWord));
	}

	/**
	 * @description 注册
	 * @author IVAn
	 * @date 2019年8月27日 下午6:03:39
	 * @return
	 */
	@RequestMapping("/register")
	@ResponseBody
	public JsonResult<Object> registerUser(String userName, String phone, String idCardNum, String password,
			String IdentityCode) {
		return userInfoService.register(userName, phone, idCardNum, password, IdentityCode, "register");
	}

	/**
	 * @description 查询所有用户列表
	 * @author IVAn
	 * @date 2019年8月27日 下午6:03:10
	 * @return
	 */
	@RequestMapping("/getAllUserInfoList")
	@ResponseBody
	public JsonResult<List<UserInfo>> getAllUserInfoList() {
		return new JsonResult<List<UserInfo>>(userInfoService.getAllUserList());
	}
}
