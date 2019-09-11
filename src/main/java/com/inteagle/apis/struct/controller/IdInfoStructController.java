package com.inteagle.apis.struct.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.inteagle.apis.struct.entity.DeviceActionStruct;
import com.inteagle.apis.struct.entity.HelmetSensorDataStruct;
import com.inteagle.apis.struct.entity.IdInfoStruct;
import com.inteagle.apis.struct.service.DeviceActionService;
import com.inteagle.apis.struct.service.HelmetSensorService;
import com.inteagle.apis.struct.service.IdInfoStructService;
import com.inteagle.common.entity.JsonResult;
import com.inteagle.common.excel.entity.ExportFoundation;
import com.inteagle.common.excel.util.ExcelUtil;
import com.inteagle.common.util.PageUtil;

@Controller
public class IdInfoStructController {

	@Autowired
	private IdInfoStructService idInfoStructService;

	@Autowired
	private HelmetSensorService helmetSensorService;

	@Autowired
	private DeviceActionService deviceActionService;

	@RequestMapping("/addDeviceAction")
	@ResponseBody
	public JsonResult<Integer> addDeviceAction() {

		DeviceActionStruct deviceActionStruct = new DeviceActionStruct();
		deviceActionStruct.setAction((byte) 0);
		deviceActionStruct.setDevice_type((byte) 0);
		deviceActionStruct.setPriority((byte) 0);

		return new JsonResult<Integer>(deviceActionService.insert(deviceActionStruct));
	}

	@RequestMapping("/getDeviceActionList")
	@ResponseBody
	public JsonResult<PageInfo<DeviceActionStruct>> getDeviceActionList(Integer page, Integer limit) {
		PageUtil.setPage(page, limit);
		List<DeviceActionStruct> list = deviceActionService.getDeviceActionList();
		return PageUtil.getPageJsonResult(list);
	}

	@RequestMapping("/addHelmetSensor")
	@ResponseBody
	public JsonResult<Integer> addHelmetSensor() {

		HelmetSensorDataStruct helmetSensorDataStruct = new HelmetSensorDataStruct();
		helmetSensorDataStruct.setId(Short.parseShort("001"));
		helmetSensorDataStruct.setVol(20);
		helmetSensorDataStruct.setTemp(30);
		helmetSensorDataStruct.setHelmet_on(1);
		return new JsonResult<Integer>(helmetSensorService.insertSensor(helmetSensorDataStruct));
	}

	@RequestMapping("/layuiTableData")
	@ResponseBody
	public JsonResult<PageInfo<HelmetSensorDataStruct>> getlayuiTableData(String deviceId, Integer page,
			Integer limit) {
		PageUtil.setPage(page, limit);
		List<HelmetSensorDataStruct> list = helmetSensorService.selectAllHelmetSensorDataStructListById(deviceId);
		return PageUtil.getPageJsonResult(list);
	}

	/**
	 * @description 查询人员定位数据
	 * @author IVAn
	 * @date 2019年9月11日 下午2:43:35
	 * @param page
	 * @param limit
	 * @return
	 */
	@RequestMapping("/getHelmetData")
	@ResponseBody
	public JsonResult<PageInfo<IdInfoStruct>> getHelmetData(Integer page, Integer limit) {
		PageUtil.setPage(page, limit);
		List<IdInfoStruct> list = idInfoStructService.getIdInfoStructList();
		return PageUtil.getPageJsonResult(list);
	}

	/**
	 * @description 导出安全帽定位数据
	 * @author IVAn
	 * @date 2019年9月11日 下午2:45:56
	 * @param request
	 * @param response
	 */
	@RequestMapping("/exportHelmetPositionData")
	public void exportHelmetPositionData(HttpServletRequest request, HttpServletResponse response) {
		List<IdInfoStruct> list = idInfoStructService.getIdInfoStructList();
		String table_header = "安全帽定位数据";
		String table_sheetName = "安全帽定位数据";
		String table_name = "安全帽定位数据";
		table_name = table_name.concat(".xls");
		ExcelUtil.exportExcel(list, table_header, table_sheetName, IdInfoStruct.class, table_name, true, response);
	}

	/**
	 * @description 导出设备电池情况数据Excel
	 * @author IVAn
	 * @date 2019年9月10日 上午10:42:08
	 * @param request
	 * @param response
	 * @param deviceId
	 */
	@RequestMapping("/exportHelmetSensorData")
	public void exportHelmetSensorData(HttpServletRequest request, HttpServletResponse response, String deviceId) {
		List<HelmetSensorDataStruct> list = helmetSensorService.selectAllHelmetSensorDataStructListById(deviceId);
		String table_header = "电池情况数据";
		String table_sheetName = "设备电池情况数据";
		String table_name = "电池情况数据";
		table_name = table_name.concat(".xls");
		ExcelUtil.exportExcel(list, table_header, table_sheetName, HelmetSensorDataStruct.class, table_name, true,
				response);
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
