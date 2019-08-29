package com.inteagle.common.idCardAudit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.inteagle.common.entity.JsonResult;
import com.inteagle.common.idCardAudit.util.Check_idCardAudit_util;

@Controller
@RequestMapping("idCardAudit")
public class IdCardAuditController {
	
	/**
	 * @description 身份证实名认证
	 * @author IVAn
	 * @date 2019年8月29日 上午10:32:18
	 * @param idCard
	 * @param name
	 * @return
	 */
	@RequestMapping("check")
	@ResponseBody
	public JsonResult<Object> check_idCardAudit(String idCard, String name) {

		return new JsonResult<>(Check_idCardAudit_util.CheckIdCardAudit_(idCard, name));
	}
}
