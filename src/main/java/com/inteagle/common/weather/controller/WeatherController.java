package com.inteagle.common.weather.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.inteagle.common.entity.JsonResult;
import com.inteagle.common.util.HttpUtil;
import com.inteagle.common.util.ParamUtil;
import com.inteagle.common.weather.account.WeatherApiAccount;

/**
 * 
 * @ClassName: WeatherController
 * @Description: TODO(获取天气数据控制器)
 * @author IVAn
 * @date 2019年8月24日下午3:53:55
 *
 */
@Controller
@RequestMapping("/weather")
public class WeatherController {

	/**
	 * 重要提示如下: HttpUtils请从
	 * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
	 * 下载
	 *
	 * 相应的依赖请参照
	 * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
	 */

	/**
	 * @description 根据城市名获取城市id
	 * @author IVAn
	 * @date 2019年8月24日 下午3:53:37
	 * @param areaName
	 * @return
	 */
	@RequestMapping("/getAreaIdByAreaName")
	@ResponseBody
	public JsonResult<Object> getAreaIdByAreaName(String areaName) {

		ParamUtil.validateParam(areaName, "城市名不能为空");

		String path = "/area-to-id";
		String method = "GET";
		Map<String, String> headers = new HashMap<String, String>();
		// 最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
		headers.put("Authorization", "APPCODE " + WeatherApiAccount.APP_CODE);
		Map<String, String> querys = new HashMap<String, String>();
		querys.put("area", areaName);
		try {
			HttpResponse response = HttpUtil.doGetWithHeaders(WeatherApiAccount.HOST, path, method, headers, querys);
			// System.out.println(response.toString());
			// 获取response的body
			String JsonString = EntityUtils.toString(response.getEntity());
			// json字符串转json对象
			JSONObject object = JSONObject.parseObject(JsonString);
			return new JsonResult<Object>(object);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new JsonResult<Object>(JsonResult.ERROR, JsonResult.ERROR_MESSAGE);
	}

	/**
	 * @description 根据城市名和城市ID查询天气
	 * @author IVAn
	 * @date 2019年8月24日 下午4:17:42
	 * @param areaName
	 * @param areaId
	 * @return
	 */
	@RequestMapping("/getWeatherByAreaNameAndAreaId")
	@ResponseBody
	public JsonResult<Object> getWeatherByAreaNameAndAreaId(String areaName, String areaId) {

//		ParamUtil.validateParam(areaName, "城市名不能为空");
//		ParamUtil.validateParam(areaId, "城市ID不能为空");

		String path = "/area-to-weather";
		String method = "GET";

		Map<String, String> headers = new HashMap<String, String>();
		// 最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
		headers.put("Authorization", "APPCODE " + WeatherApiAccount.APP_CODE);
		Map<String, String> querys = new HashMap<String, String>();

		if (areaName != null) {
			querys.put("area", areaName);
		}
		if (areaId != null) {
			querys.put("areaid", areaId);
		}
		querys.put("need3HourForcast", "0");
		querys.put("needAlarm", "0");
		querys.put("needHourData", "0");
		querys.put("needIndex", "0");
		querys.put("needMoreDay", "0");
		try {
			/**
			 * 重要提示如下: HttpUtils请从
			 * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
			 * 下载
			 *
			 * 相应的依赖请参照
			 * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
			 */
			HttpResponse response = HttpUtil.doGetWithHeaders(WeatherApiAccount.HOST, path, method, headers, querys);
			// System.out.println(response.toString());
			// 获取response的body
			String JsonString = EntityUtils.toString(response.getEntity());
			// json字符串转json对象
			JSONObject object = JSONObject.parseObject(JsonString);
			return new JsonResult<Object>(object);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new JsonResult<Object>(JsonResult.ERROR, JsonResult.ERROR_MESSAGE);
	}

}
