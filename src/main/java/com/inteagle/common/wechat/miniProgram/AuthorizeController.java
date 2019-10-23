package com.inteagle.common.wechat.miniProgram;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSONObject;
import com.inteagle.common.wechat.util.WeChatUtil;
import com.inteagle.config.app.AppConfig;
import com.inteagle.modal.base.JsonResult;
import com.inteagle.modal.userInfo.UserInfo;
import com.inteagle.service.UserInfoService;

import java.security.AlgorithmParameters;
import java.security.Security;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import com.alibaba.fastjson.JSONObject;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

/**
 * 
 * @ClassName: AuthorizeController
 * @Description: TODO(微信小程序授权接口类)
 * @author IVAn
 * @date 2019年10月11日下午7:11:05
 *
 */
@Controller
@RequestMapping("/miniProgram")
@SuppressWarnings({ "restriction", "unused" })
public class AuthorizeController {

	@Autowired
	private UserInfoService userInfoService;

	/**
	 * @description 登录获取openId
	 * @author IVAn
	 * @date 2019年10月14日 上午10:00:27
	 * @param request
	 * @param response
	 * @param code
	 * @return
	 */
	@ResponseBody
	@GetMapping(value = "/loginForCode")
	public JsonResult<Map<String, Object>> getOpenId(HttpServletRequest request, HttpServletResponse response,
			String code, String encryptedData, String iv) {

		Map<String, Object> return_map = new HashMap<String, Object>();

		// 根据小程序穿过来的code想这个url发送请求
		String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + AppConfig.appid + "&secret="
				+ AppConfig.secret + "&js_code=" + code + "&grant_type=authorization_code";
		// 发送请求，返回Json字符串
		String str = WeChatUtil.httpRequest(url, "GET", null);
		// 转成Json对象 获取openid
		JSONObject jsonObject = JSONObject.parseObject(str);
		// openId
		String openid = jsonObject.get("openid").toString();
		// sessionKey
		String sessionkey = jsonObject.get("session_key").toString();

		UserInfo user = userInfoService.getUserInfoByOpenId(openid);
		return_map.put("userInfo", user);
		return_map.put("openid", openid);
		return_map.put("sessionkey", sessionkey);

		// 微信用户信息
		String wechat_info_string = WeChatUtil.decryptData(encryptedData, sessionkey, iv);
		return_map.put("wechat_info_string", wechat_info_string);

		return new JsonResult<Map<String, Object>>(JsonResult.SUCCESS, return_map, JsonResult.SUCCESS_MESSAGE);
	}

	/**
	 * @description 获取微信手机号码
	 * @author IVAn
	 * @date 2019年10月15日 下午6:14:51
	 * @return
	 */
	@ResponseBody
	@PostMapping(value = "/getphoneNumber")
	public JsonResult<Map<String,Object>> getphoneNumber(String encryptedData, String session_key, String iv, String openId,
			@RequestParam("wechat_userInfo") String wechat_userInfo) {
		return new JsonResult<Map<String,Object>>(userInfoService.loginOrResgisterforMiniProgram(encryptedData, session_key, iv,
				openId, wechat_userInfo));
	}

	/**
	 * @description 小程序账号密码登录
	 * @author IVAn
	 * @date 2019年10月15日 下午4:51:12
	 * @param userName
	 * @param password
	 * @return
	 */
	@ResponseBody
	@PostMapping(value = "/login")
	public JsonResult<UserInfo> loginByAccount(@RequestParam("userName") String userName,
			@RequestParam("password") String password, @RequestParam("code") String code) {

		return new JsonResult<UserInfo>(userInfoService.loginforMiniProgram(userName, password, code));
	}

}
