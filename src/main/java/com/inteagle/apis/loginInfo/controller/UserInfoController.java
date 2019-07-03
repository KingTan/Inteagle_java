package com.inteagle.apis.loginInfo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.inteagle.common.entity.JsonResult;
import com.inteagle.apis.loginInfo.entity.UserInfo;
import com.inteagle.apis.loginInfo.service.UserInfoService;

@Controller
@RequestMapping("/userInfo")
public class UserInfoController {

	@Autowired
	private UserInfoService userInfoService;

	@RequestMapping("/getAllUserInfoList")
	@ResponseBody
	public JsonResult<List<UserInfo>> getAllUserInfoList() {
		return new JsonResult<List<UserInfo>>(userInfoService.getAllUserList());
	}

	@RequestMapping("/login")
	@ResponseBody
	public JsonResult<UserInfo> getUserInfoByParamAndPwd(@RequestParam(value = "searchParam") String searchParam,
			@RequestParam(value = "passWord") String passWord) {
		return new JsonResult<UserInfo>(userInfoService.getUserInfoByParamAndPwd(searchParam, passWord));
	}

}