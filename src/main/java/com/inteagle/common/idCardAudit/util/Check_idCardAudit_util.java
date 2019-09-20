package com.inteagle.common.idCardAudit.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;
import com.inteagle.common.idCardAudit.entity.IdCardAuditAPIAccount;
import com.inteagle.common.util.HttpUtil;

/**
 * 
 * @ClassName: Check_idCardAudit_util
 * @Description: TODO(身份证实名认证工具类)
 * @author IVAn
 * @date 2019年8月29日上午10:28:12
 *
 */
public class Check_idCardAudit_util {

	/**
	 * @description 调用API 实名验证
	 * @author IVAn
	 * @date 2019年8月29日 上午10:29:52
	 * @param idCard
	 * @param name
	 * @return
	 */
	public static JSONObject CheckIdCardAudit_(String idCard, String name) {
		String method = "GET";
		String path = "/idcardAudit";
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Authorization", "APPCODE " + IdCardAuditAPIAccount.APP_CODE);
		Map<String, String> querys = new HashMap<String, String>();
		querys.put("idcard", idCard);
		querys.put("name", name);
		try {
			HttpResponse response = HttpUtil.doGetWithHeaders(IdCardAuditAPIAccount.HOST, path, method, headers,
					querys);
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
	 * @description 身份证实名认证
	 * @author IVAn
	 * @date 2019年8月29日 上午10:40:13
	 * @param idCard
	 * @param name
	 * @return
	 */
	public static Map<String, Object> check_idCardAudit(String idCard, String name) {

		Map<String, Object> result_map = new HashMap<String, Object>();

		String method = "GET";
		String path = "/idcardAudit";
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Authorization", "APPCODE " + IdCardAuditAPIAccount.APP_CODE);
		Map<String, String> querys = new HashMap<String, String>();
		querys.put("idcard", idCard);
		querys.put("name", name);
		try {
			HttpResponse response = HttpUtil.doGetWithHeaders(IdCardAuditAPIAccount.HOST, path, method, headers,
					querys);
			// 获取response的body
			String JsonString = EntityUtils.toString(response.getEntity());
			// json字符串转json对象
			JSONObject object = JSONObject.parseObject(JsonString);

			// 验证结果
			int result = Integer.parseInt(object.getJSONObject("showapi_res_body").getString("ret_code"));
			if (result == 0) {
				String gender = object.getJSONObject("showapi_res_body").getString("sex");
				if (gender.equals("M")) {
					gender = "男";
				} else if (gender.equals("WM")) {
					gender = "女";
				}
				result_map.put("result", true);
				result_map.put("gender", gender);
				return result_map;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		result_map.put("result", false);
		return result_map;
	}

}
