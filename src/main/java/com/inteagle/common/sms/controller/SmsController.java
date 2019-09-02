package com.inteagle.common.sms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.inteagle.apis.loginInfo.entity.UserInfo;
import com.inteagle.apis.loginInfo.service.UserInfoService;
import com.inteagle.common.constant.Constant;
import com.inteagle.common.entity.JsonResult;
import com.inteagle.common.exception.BusinessException;
import com.inteagle.common.redis.RedisCacheUtil;
import com.inteagle.common.redis.RedisService;
import com.inteagle.common.sms.entity.IdentityCodeEnum;
import com.inteagle.common.sms.util.SMS_util;
import com.inteagle.common.sms.util.SendSMS;
import com.inteagle.common.util.ParamUtil;
import com.inteagle.common.util.RandomUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @ClassName: SmsController
 * @Description: TODO(短信控制器)
 * @author IVAn
 * @date 2019年8月26日下午3:08:14
 *
 */
@RequestMapping("/sms")
@Controller
@Slf4j
public class SmsController {

	@Autowired
	private RedisService redisService;

	@Autowired
	private UserInfoService userInfoService;

	/**
	 * @description 发送验证码
	 * @author IVAn
	 * @date 2019年8月27日 上午10:12:27
	 * @param phoneNumber
	 * @param codeType
	 * @return
	 */
	@RequestMapping("/sendSMSCode")
	@ResponseBody
	public JsonResult<Object> sendSMSCode(String phoneNumber, String codeType) {

		// 模板编号
		String tNum = "";
		// 验证码类型 Register_Code-注册验证码、 Login_Code-登录验证码
		if (codeType.equals(IdentityCodeEnum.Register.getValue())) {
			tNum = IdentityCodeEnum.Register_Code.getValue();
			// 根据手机号查询是否已注册用户
			UserInfo exist_user = userInfoService.getUserInfoByPhone(phoneNumber);
			if (ParamUtil.notNullForParam(exist_user)) {
				throw new BusinessException("该手机号已注册过用户");
			}
		} else if (codeType.equals(IdentityCodeEnum.Login.getValue())) {
			tNum = IdentityCodeEnum.Login_Code.getValue();
		}

		// 真实验证码开关
		if (!Constant.IdentifyingCodeFlag) {
			// 保存验证码到缓存中
			redisService.saveIdentifyingCode(phoneNumber, "666666", codeType);
			return new JsonResult<>("发送成功");
		}

		// 随机6位验证码
		String code = RandomUtil.getRandomNumberCode(6);
		// 验证码参数JSON格式
		JSONObject content_obejct = new JSONObject();
		content_obejct.put("code", code);
		String content = content_obejct.toJSONString();
		// 发送短信
		JSONObject object = SMS_util.sendTplSms(phoneNumber, tNum, content);

		// 短信发送状态
		if (!object.getJSONObject("showapi_res_body").getString("remark")
				.equals(IdentityCodeEnum.Success_Code.getValue())) {
			log.error("短信发送失败，错误码：" + object.getJSONObject("showapi_res_body").getString("showapi_fee_code") + "错误信息："
					+ object.getJSONObject("showapi_res_body").getString("remark"));
			System.err.println("短信发送失败，错误码：" + object.getJSONObject("showapi_res_body").getString("showapi_fee_code")
					+ "错误信息：" + object.getJSONObject("showapi_res_body").getString("remark"));
			throw new BusinessException(BusinessException.BUSINESS_ERROR_CODE, "验证码发送失败");
		}
		// 保存验证码到缓存中
		redisService.saveIdentifyingCode(phoneNumber, code, codeType);

		return new JsonResult<>(object);
	}

	/**
	 * @description 效验 短信验证码
	 * @author IVAn
	 * @date 2019年9月2日 下午5:29:59
	 * @param phone
	 * @param identityCode
	 * @param codeType
	 * @return
	 */
	@RequestMapping("/ValidateCode")
	@ResponseBody
	public JsonResult<Object> ValidateCode(String phone, String identityCode, String codeType) {
		redisService.validateIdentifyingCode(phone, identityCode, codeType);
		return new JsonResult<>(JsonResult.SUCCESS, "效验成功");
	}

	/**
	 * @description 修改短信模板内容
	 * @author IVAn
	 * @date 2019年8月27日 下午5:23:32
	 * @param content
	 * @param notiPhone
	 * @param tNum
	 * @param title
	 * @return
	 */
	@RequestMapping("/updateTemplate")
	@ResponseBody
	public JsonResult<Object> updateTemplate(String content, String notiPhone, String tNum, String title) {

		return new JsonResult<Object>(SMS_util.updateTemplate(content, notiPhone, tNum, title));
	}

	/**
	 * @description 添加短信模板
	 * @author IVAn
	 * @date 2019年8月26日 下午4:15:17
	 * @param content
	 * @param notiPhone
	 * @param title
	 * @return
	 */
	@RequestMapping("/createTemplate")
	@ResponseBody
	public JsonResult<Object> createTemplate() {

		// 注册验证码短信模板
		String content = "您的验证码是:[code],在10分钟内有效。如非本人操作请忽略本短信。";
		String notiPhone = "18820316570";
		String title = "鹰腾智能";

//		// 登录验证短信模板
//		String content = "验证码:[code]，此验证码只用于登录系统验证身份，请勿转发他人，10分钟内有效。";
//		String notiPhone = "18820316570";
//		String title = "鹰腾智能";
		return new JsonResult<Object>(SMS_util.createTemplate(content, notiPhone, title));
	}

	/**
	 * @description 查询短信模板
	 * @author IVAn
	 * @date 2019年8月26日 下午4:15:25
	 * @param page
	 * @return
	 */
	@RequestMapping("/searchTemplate")
	@ResponseBody
	public JsonResult<Object> searchTemplate(String page) {
		return new JsonResult<Object>(SMS_util.searchTemplate(page));
	}

	/**
	 * @description 发送短信验证码(江苏美圣科技)
	 * @author IVAn
	 * @date 2019年8月26日 下午4:15:35
	 * @return
	 */
	@RequestMapping("/sendCode")
	@ResponseBody
	public JsonResult<Object> sendCode() {
		// 获取验证码
		String code = RandomUtil.getRandomNumberCode(6);

		// 发送短信
		String sendResult = SendSMS.sendTplSms("18820316570", "JSM43017-0001", "@1@=" + code);
		// 发送短信结果状态
		String resultCode = SendSMS.getStatusVal(sendResult);
		if (!"0".equals(resultCode)) {
			log.error("短信发送失败，错误码：" + resultCode + "错误信息：" + sendResult);
			System.err.println("短信发送失败，错误码：" + resultCode + "错误信息：" + sendResult);
			throw new BusinessException(BusinessException.BUSINESS_ERROR_CODE, "验证码发送失败");
		}

		return new JsonResult<Object>(JsonResult.SUCCESS, "验证码发送成功");
	}

}
