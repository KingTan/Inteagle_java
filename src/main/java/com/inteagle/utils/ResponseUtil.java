package com.inteagle.utils;


import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

/**
 * response工具类
 * 
 * @author IVAn
 * @CreateDate 2019年6月27日
 */
public class ResponseUtil {

	private ResponseUtil() {
		throw new RuntimeException("new ResponseUtil instance error");
	}

	/**
	 * 发送json数据
	 * 
	 * @param response：响应对象
	 * @param obj：java对象
	 * @author IVAn
	 * @throws IOException
	 * @createDate 2018年5月30日 下午1:58:26
	 */
	public static void sendJsonMessage(HttpServletResponse response, Object param) {
		PrintWriter writer = null;
		try {
			response.setContentType("application/json; charset=utf-8");
			writer = response.getWriter();
			String json = JSONObject.toJSONString(param);
			writer.write(json);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			if (writer != null) {
				writer.close();
				try {
					response.flushBuffer();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}
}

