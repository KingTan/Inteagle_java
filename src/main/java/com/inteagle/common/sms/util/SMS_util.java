package com.inteagle.common.sms.util;

import java.util.HashMap;
import java.util.Map;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import com.alibaba.fastjson.JSONObject;
import com.inteagle.common.util.HttpUtil;

public class SMS_util {

	public static String APP_KEY = "24981964";
	public static String APP_SECRET = "dd55ecf7f0c795b9d89cf32e30da8386";
	public static String APP_CODE = "5619664a9da343f0a87eea51ad4dad0d";
	public static String HOST = "https://ali-sms.showapi.com";
	/**
	 * 默认字符编码集
	 */
	public static final String CHARSET_UTF8 = "UTF-8";

	/**
	 * @description 发送模板短信
	 * @author IVAn
	 * @date 2019年8月26日 下午3:44:48
	 * @param mobile  手机号
	 * @param tplId   模板ID
	 * @param content 短信内容
	 * @return
	 */
	public static JSONObject sendTplSms(String mobile, String tNum, String content) {
		String method = "GET";
		String path = "/sendSms";
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Authorization", "APPCODE " + APP_CODE);
		Map<String, String> querys = new HashMap<String, String>();
		querys.put("mobile", mobile);
		querys.put("content", content);
		querys.put("tNum", tNum);
		try {
			HttpResponse response = HttpUtil.doGetWithHeaders(HOST, path, method, headers, querys);
			// 获取response的body
			String JsonString = EntityUtils.toString(response.getEntity());
			// json字符串转json对象
			JSONObject object = JSONObject.parseObject(JsonString);
			return object;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @description 查询短信模板
	 * @author IVAn
	 * @date 2019年8月26日 下午3:53:40
	 * @param nomalTemplate 模板类型:1.系统内置模板 2.自定义模板(自建模板)
	 * @param page          页码
	 * @return
	 */
	public static JSONObject searchTemplate(String page) {
		String method = "GET";
		String path = "/searchTemplate";
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Authorization", "APPCODE " + APP_CODE);
		Map<String, String> querys = new HashMap<String, String>();
		querys.put("nomalTemplate", "2");
		querys.put("page", page);
		try {
			HttpResponse response = HttpUtil.doGetWithHeaders(HOST, path, method, headers, querys);
			// 获取response的body
			String JsonString = EntityUtils.toString(response.getEntity());
			// json字符串转json对象
			JSONObject object = JSONObject.parseObject(JsonString);
			return object;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @description 创建短信模板
	 * @author IVAn
	 * @date 2019年8月26日 下午3:57:04
	 * @param content   短信主体内容
	 * @param notiPhone 模板联系人的电话
	 * @param title     短信的签名
	 * @return
	 */
	public static JSONObject createTemplate(String content, String notiPhone, String title) {
		String method = "GET";
		String path = "/createTemplate";

		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Authorization", "APPCODE " + APP_CODE);

		Map<String, String> querys = new HashMap<String, String>();
		querys.put("content", content);
		querys.put("notiPhone", notiPhone);
		querys.put("title", title);
		try {
			HttpResponse response = HttpUtil.doGetWithHeaders(HOST, path, method, headers, querys);
			// 获取response的body
			String JsonString = EntityUtils.toString(response.getEntity());
			// json字符串转json对象
			JSONObject object = JSONObject.parseObject(JsonString);
			return object;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @description 修改短信模板
	 * @author IVAn
	 * @date 2019年8月26日 下午3:57:04
	 * @param content   短信主体内容
	 * @param notiPhone 模板联系人的电话
	 * @param title     短信的签名
	 * @return
	 */
	public static JSONObject updateTemplate(String content, String notiPhone, String tNum, String title) {
		String method = "GET";
		String path = "/updateTemplate";

		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Authorization", "APPCODE " + APP_CODE);

		Map<String, String> querys = new HashMap<String, String>();
		
		//可选参数
		if (content != null && content != "") {
			querys.put("content", content);
		}
		if (notiPhone != null && notiPhone != "") {
			querys.put("notiPhone", notiPhone);
		}
		if (title != null && title != "") {
			querys.put("title", title);
		}
		querys.put("tNum", tNum);

		try {
			HttpResponse response = HttpUtil.doGetWithHeaders(HOST, path, method, headers, querys);
			// 获取response的body
			String JsonString = EntityUtils.toString(response.getEntity());
			// json字符串转json对象
			JSONObject object = JSONObject.parseObject(JsonString);
			return object;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
