package com.inteagle.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.inteagle.common.exception.HttpException;

/**
 * HttpUtil
 * 
 * @author IVAn
 * @CreateDate 2018年6月23日 上午9:29:09
 */
public class HttpUtil {

	// 字符编码
	private static final String CHATSET = "UTF-8";

	private HttpUtil() {
		throw new RuntimeException("new HttpUtil instance error");
	}

	/**
	 * get请求
	 * 
	 * @param url:请求路径
	 * @return String:json字符串
	 * @author IVAn
	 * @createDate 2018年6月23日 上午9:32:46
	 */
	public static String doGet(String url) {
		CloseableHttpClient httpClient = null;
		try {
			httpClient = HttpClients.createDefault();
			// 发送get请求
			HttpGet request = new HttpGet(url);
			HttpResponse response = httpClient.execute(request);
			// 发送请求，并得到响应
			int statusCode = response.getStatusLine().getStatusCode();
			// 请求成功
			if (statusCode == HttpStatus.SC_OK) {
				return EntityUtils.toString(response.getEntity());
			}
			// 请求失败
			else {
				HttpException
						.throwException("request url failed, status code : " + statusCode + ", url address : " + url);
			}
		} catch (IOException e) {
			HttpException.throwException(e);
		} finally {
			try {
				if (httpClient != null) {
					httpClient.close();
				}
			} catch (IOException e) {
				HttpException.throwException(e);
			}
		}
		return null;
	}

	/**
	 * @description 发送携带Headers的请求
	 * @author IVAn
	 * @date 2019年8月24日 下午3:51:55
	 * @param host
	 * @param path
	 * @param method
	 * @param headers
	 * @param querys
	 * @return
	 * @throws Exception
	 */
	public static HttpResponse doGetWithHeaders(String host, String path, String method, Map<String, String> headers,
			Map<String, String> querys) throws Exception {
		HttpClient httpClient = wrapClient(host);

		HttpGet request = new HttpGet(buildUrl(host, path, querys));
		for (Map.Entry<String, String> e : headers.entrySet()) {
			request.addHeader(e.getKey(), e.getValue());
		}

		return httpClient.execute(request);
	}

	/**
	 * post请求
	 * 
	 * @param url:请求路径
	 * @param bean：javaBean参数
	 * @return String：json返回值
	 * @author IVAn
	 * @createDate 2018年6月23日 上午9:39:44
	 */
	public static String doBeanPost(String url, Object bean) {
		return doMapPost(url, BeanUtil.beanToMap(bean));
	}

	/**
	 * post请求(用于key-value格式的参数)
	 * 
	 * @param url:请求路径
	 * @param paramMap：键值对参数
	 * @return String：json返回值
	 * @author IVAn
	 * @createDate 2018年6月23日 上午9:39:44
	 */
	public static String doMapPost(String url, Map<?, ?> paramMap) {
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse response = null;
		BufferedReader in = null;
		try {
			// 定义HttpClient
			httpClient = HttpClients.createDefault();
			// 实例化HTTP方法
			HttpPost httpPost = new HttpPost(url);
			// 创建HttpClient
			httpClient = HttpClients.createDefault();
			// 绑定参数
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			for (@SuppressWarnings("rawtypes")
			Iterator iter = paramMap.keySet().iterator(); iter.hasNext();) {
				String name = (String) iter.next();
				String value = String.valueOf(paramMap.get(name));
				nvps.add(new BasicNameValuePair(name, value));
			}
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, CHATSET));
			// 发送请求并获取响应
			response = httpClient.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			// 请求成功
			if (statusCode == HttpStatus.SC_OK) {
				in = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), CHATSET));
				StringBuffer sb = new StringBuffer("");
				String line = "";
				String NL = System.getProperty("line.separator");
				while ((line = in.readLine()) != null) {
					sb.append(line + NL);
				}
				in.close();
				return sb.toString();
			}
			// 请求失败
			else {
				HttpException
						.throwException("request url failed, status code : " + statusCode + ", url address : " + url);
			}
		} catch (IOException e) {
			HttpException.throwException(e);
		} finally {
			try {
				if (httpClient != null) {
					httpClient.close();
				}
				if (response != null) {
					response.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				HttpException.throwException(e);
			}
		}
		return null;
	}

	/**
	 * post请求（用于请求json格式的参数）
	 * 
	 * @param url:请求路径
	 * @param jsonStr:json参数
	 * @return String：json返回值
	 * @author IVAn
	 * @createDate 2018年6月23日 上午9:37:18
	 */
	public static String doJsonPost(String url, String jsonStr) {
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse response = null;
		try {
			// 创建HttpClient
			httpClient = HttpClients.createDefault();
			// 创建HttpPost
			HttpPost httpPost = new HttpPost(url);
			// 设置请求头
			httpPost.setHeader("Accept", "application/json");
			httpPost.setHeader("Content-Type", "application/json");
			// 绑定参数
			StringEntity entity = new StringEntity(jsonStr, CHATSET);
			httpPost.setEntity(entity);
			// 发送请求并获取响应
			response = httpClient.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			// 请求成功
			if (statusCode == HttpStatus.SC_OK) {
				return EntityUtils.toString(response.getEntity());
			}
			// 请求失败
			else {
				HttpException
						.throwException("request url failed, status code : " + statusCode + ", url address : " + url);
			}
		} catch (IOException e) {
			HttpException.throwException(e);
		} finally {
			try {
				if (httpClient != null) {
					httpClient.close();
				}
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				HttpException.throwException(e);
			}
		}
		return null;
	}

	/**
	 * post请求
	 * 
	 * @param url:请求路径
	 * @param xmlStr:xml字符串
	 * @return String：json返回值
	 * @author IVAn
	 * @createDate 2018年8月15日 下午2:19:49
	 */
	public static String doXmlPost(String url, String xmlStr) {
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse response = null;
		try {
			// 创建HttpClient
			httpClient = HttpClients.createDefault();
			// 创建HttpPost
			HttpPost httpPost = new HttpPost(url);
			// 设置请求头
			httpPost.setHeader("Content-Type", "text/xml; charset=UTF-8");
			// 绑定Entity参数
			StringEntity entityParams = new StringEntity(xmlStr, CHATSET);
			httpPost.setEntity(entityParams);
			// 发送请求并获取响应
			response = httpClient.execute(httpPost);
			// 获取响应码
			int statusCode = response.getStatusLine().getStatusCode();
			// 请求成功
			if (statusCode == HttpStatus.SC_OK) {
				return EntityUtils.toString(response.getEntity(), CHATSET);
			}
			// 请求失败
			else {
				HttpException
						.throwException("request url failed, status code : " + statusCode + ", url address : " + url);
			}
		} catch (Exception e) {
			HttpException.throwException(e);
		} finally {
			try {
				if (httpClient != null) {
					httpClient.close();
				}
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				HttpException.throwException(e);
			}
		}
		return null;
	}

	private static HttpClient wrapClient(String host) {
		HttpClient httpClient = new DefaultHttpClient();
		if (host.startsWith("https://")) {
			sslClient(httpClient);
		}

		return httpClient;
	}

	private static void sslClient(HttpClient httpClient) {
		try {
			SSLContext ctx = SSLContext.getInstance("TLS");
			X509TrustManager tm = new X509TrustManager() {
				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}

				public void checkClientTrusted(X509Certificate[] xcs, String str) {

				}

				public void checkServerTrusted(X509Certificate[] xcs, String str) {

				}
			};
			ctx.init(null, new TrustManager[] { tm }, null);
			SSLSocketFactory ssf = new SSLSocketFactory(ctx);
			ssf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			ClientConnectionManager ccm = httpClient.getConnectionManager();
			SchemeRegistry registry = ccm.getSchemeRegistry();
			registry.register(new Scheme("https", 443, ssf));
		} catch (KeyManagementException ex) {
			throw new RuntimeException(ex);
		} catch (NoSuchAlgorithmException ex) {
			throw new RuntimeException(ex);
		}
	}

	private static String buildUrl(String host, String path, Map<String, String> querys)
			throws UnsupportedEncodingException {
		StringBuilder sbUrl = new StringBuilder();
		sbUrl.append(host);
		if (!StringUtils.isBlank(path)) {
			sbUrl.append(path);
		}
		if (null != querys) {
			StringBuilder sbQuery = new StringBuilder();
			for (Map.Entry<String, String> query : querys.entrySet()) {
				if (0 < sbQuery.length()) {
					sbQuery.append("&");
				}
				if (StringUtils.isBlank(query.getKey()) && !StringUtils.isBlank(query.getValue())) {
					sbQuery.append(query.getValue());
				}
				if (!StringUtils.isBlank(query.getKey())) {
					sbQuery.append(query.getKey());
					if (!StringUtils.isBlank(query.getValue())) {
						sbQuery.append("=");
						sbQuery.append(URLEncoder.encode(query.getValue(), "UTF-8"));
					}
				}
			}
			if (0 < sbQuery.length()) {
				sbUrl.append("?").append(sbQuery);
			}
			
		}

		return sbUrl.toString();
	}

}
