package com.inteagle.utils.sms;

import java.io.IOException;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class SendSMS {
	/**
	 * 短信提供商开设账号时提供一下参数: 账号、密码、通信key、ip、端口
	 */
	static String account;// 账号
	static String password;// 密码
	static String veryCode;// 通信key
	static String http_url;// ip+端口

	/**
	 * 默认字符编码集
	 */
	public static final String CHARSET_UTF8 = "UTF-8";
	static {
		/* Properties p = new Properties(); */
		try {
			// 1.读取参数
			/*
			 * p.load(SendSMS.class.getClassLoader().getResourceAsStream(
			 * "sendMessage.properties")); account = p.getProperty("account"); password =
			 * p.getProperty("password"); veryCode = p.getProperty("veryCode"); http_url=
			 * p.getProperty("http_url");
			 */
			account = "JSM43017";
			password = "R27WdRwH";
			veryCode = "aFbjjkaCbEHT";
			http_url = "http://112.74.76.186:8030";
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 发送普通短信 普通短信发送需要人工审核
	 * 
	 * @param mobile  手机号码, 多个号码以英文逗号隔开,最多支持100个号码
	 * @param content 短信内容
	 * @return String xml字符串，格式请参考文档说明
	 */
	public static String sendSms(String mobile, String content) {
		String sendSmsUrl = http_url + "/service/httpService/httpInterface.do?method=sendMsg";
		StringBuilder param = new StringBuilder();
		param.append("&username=").append(account);
		param.append("&password=").append(password);
		param.append("&veryCode=").append(veryCode);
		param.append("&mobile=").append(mobile);
		param.append("&content=").append(content);
		param.append("&msgtype=").append("1");
		param.append("&code=").append("utf-8");
		StringEntity entity;
		entity = new StringEntity(param.toString(), "utf-8");
		entity.setContentType("application/x-www-form-urlencoded");
		entity.setContentEncoding("UTF-8");
		String result = sendPostHttp(sendSmsUrl, entity);
		return result;
	}

	/**
	 * 模版短信,无需人工审核，直接发送 (短信模版的创建参考客户端操作手册)
	 * 模版：@1@会员，动态验证码@2@(五分钟内有效)。请注意保密，勿将验证码告知他人。 参数值:@1@=某某,@2@=4293
	 * 手机接收内容：【短信签名】某某会员，动态验证码4293(五分钟内有效)。请注意保密，勿将验证码告知他人。 注意:
	 * 1.发送模板短信变量值不能包含英文逗号和等号（, =） 2.特殊字符+ / # ?无需转义 3.& %需要转义 + %2b 空格 %20 & %26 %
	 * %25
	 * 
	 * @param mobile  手机号码
	 * @param tplId   模板编号，对应客户端模版编号
	 * @param content 模板参数值，以英文逗号隔开，如：@1@=某某,@2@=4293
	 * @return xml字符串，格式请参考文档说明
	 */
	public static String sendTplSms(String mobile, String tplId, String content) {
		String sendTplSmsUrl = http_url + "/service/httpService/httpInterface.do?method=sendMsg";
		StringBuilder param = new StringBuilder();
		param.append("&username=").append(account);
		param.append("&password=").append(password);
		param.append("&veryCode=").append(veryCode);
		param.append("&mobile=").append(mobile);
		param.append("&content=").append(content);
		param.append("&msgtype=").append("2");// 2-模板短信
		param.append("&tempid=").append(tplId);// 模板编号
		param.append("&code=").append("utf-8");
		StringEntity entity;
		entity = new StringEntity(param.toString(), "utf-8");
		entity.setContentType("application/x-www-form-urlencoded");
		entity.setContentEncoding("UTF-8");
		String result = sendPostHttp(sendTplSmsUrl, entity);
		return result;
	}

	/**
	 * @methodName getStatusVal @description:获取状态码 @param str @return String @throws
	 */
	public static String getStatusVal(String str) {
		return str.substring(str.indexOf("<status>") + 8, str.indexOf("</status>")).trim();
	}

	/**
	 * 发送HttpPOST请求
	 * 
	 * @param postUrl 请求地址
	 * @param entity  请求参数实体
	 * @return String
	 */
	public static String sendPostHttp(String postUrl, StringEntity entity) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		String str = "";
		HttpPost method = new HttpPost(postUrl);
		method.setEntity(entity);
		try {
			HttpResponse result = httpClient.execute(method);
			if (result.getStatusLine().getStatusCode() == 200) {
				str = EntityUtils.toString(result.getEntity());
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return str;
	}

}
