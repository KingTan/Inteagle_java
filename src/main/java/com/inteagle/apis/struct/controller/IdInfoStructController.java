package com.inteagle.apis.struct.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.inteagle.apis.struct.entity.IdInfoStruct;
import com.inteagle.apis.struct.service.IdInfoStructService;
import com.inteagle.common.entity.JsonResult;
import com.inteagle.common.struct.AnalysisUtil;

@Controller
public class IdInfoStructController {

	@Autowired
	private IdInfoStructService idInfoStructService;

	@RequestMapping("/addInfo")
	@ResponseBody
	public JsonResult<Integer> addInfo(IdInfoStruct idInfoStruct) {

		return new JsonResult<Integer>(idInfoStructService.insert(idInfoStruct));
	}

	@RequestMapping("/addInfo_oth")
	@ResponseBody
	public JsonResult<Integer> addInfooth() {
		AnalysisUtil.validate("123");
		return new JsonResult<Integer>(1);
	}

}
