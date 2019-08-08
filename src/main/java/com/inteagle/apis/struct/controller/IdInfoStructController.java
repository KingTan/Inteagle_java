package com.inteagle.apis.struct.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.inteagle.apis.struct.entity.HelmetSensorDataStruct;
import com.inteagle.apis.struct.entity.IdInfoStruct;
import com.inteagle.apis.struct.service.HelmetSensorService;
import com.inteagle.apis.struct.service.IdInfoStructService;
import com.inteagle.common.entity.JsonResult;

@Controller
public class IdInfoStructController {

	@Autowired
	private IdInfoStructService idInfoStructService;

	@Autowired
	private HelmetSensorService helmetSensorService;

	@RequestMapping("/addHelmetSensor")
	@ResponseBody
	public JsonResult<Integer> addHelmetSensor() {

		HelmetSensorDataStruct helmetSensorDataStruct = new HelmetSensorDataStruct();
		helmetSensorDataStruct.setId(Short.parseShort("002"));
		helmetSensorDataStruct.setVol(20);
		helmetSensorDataStruct.setTemp(30);
		helmetSensorDataStruct.setHelmet_on(1);

		return new JsonResult<Integer>(helmetSensorService.insert(helmetSensorDataStruct));
	}

	@RequestMapping("/getHelmetSensorList")
	@ResponseBody
	public JsonResult<List<HelmetSensorDataStruct>> getHelmetSensorList(String deviceId) {

		return new JsonResult<List<HelmetSensorDataStruct>>(
				helmetSensorService.selectAllHelmetSensorDataStructListById(deviceId));
	}

	@RequestMapping("/addInfo")
	@ResponseBody
	public JsonResult<Integer> addInfo(IdInfoStruct idInfoStruct) {

		return new JsonResult<Integer>(idInfoStructService.insert(idInfoStruct));
	}

	@RequestMapping("/addInfo_oth")
	@ResponseBody
	public JsonResult<Integer> addInfooth() {

//		try {
//			byte[] test = { 1, 2, 3 };
//			AnalysisUtil.validate(test);
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return new JsonResult<Integer>(1);
	}

}
