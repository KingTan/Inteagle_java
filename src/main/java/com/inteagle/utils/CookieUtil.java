package com.inteagle.utils;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.inteagle.common.exception.BusinessException;


/**
 * cookie工具类
 * 
 * @author IVAn
 * @CreateDate 2019年6月27日
 */
public class CookieUtil {

	private CookieUtil() {
		throw new RuntimeException("new CookieUtil instance error");
	}

	// 1天
	public static final int One_DAY = 24 * 60 * 60;

	public static final String TOKEN_SCOPE = "/";

	/**
	 * 根据cookie名称获取值
	 * 
	 * @param request
	 * @param cookieName
	 * @return String
	 * @author IVAn
	 * @createDate 2018年5月20日 上午11:34:32
	 */
	public static String getCookieValue(HttpServletRequest request, String cookieName) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(cookieName)) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}

	/**
	 * 设置cookie
	 * 
	 * @param request
	 * @param response
	 * @param cookieValue
	 * @author IVAn
	 * @createDate 2018年5月24日 下午7:42:31
	 */
	public static void setCookie(HttpServletResponse response, String cookieName, String cookieValue, int age,
			String path) {
		Cookie cookie = new Cookie(cookieName, cookieValue);
		cookie.setMaxAge(age);
		cookie.setPath(path);
		response.addCookie(cookie);
	}

	/**
	 * 销毁cookie
	 * 
	 * @author IVAn
	 * @createDate 2018年5月24日 下午8:06:49
	 */
	public static void destroyCookie(HttpServletResponse response, String cookieName) {
		Cookie cookie = new Cookie(cookieName, "");
		cookie.setMaxAge(0);
		cookie.setPath(TOKEN_SCOPE);
		response.addCookie(cookie);
	}

	/**
	 * 设置cookie，默认30天有效期
	 * 
	 * @param request
	 * @param cookieName
	 * @param cookieValue
	 * @return Cookie
	 * @author IVAn
	 * @createDate 2018年5月23日 上午11:44:24
	 */
	public static void saveUserToken(HttpServletResponse response, int days, String tokenName, String tokenValue) {
		if (days < 1) {
			throw new BusinessException(BusinessException.BUSINESS_ERROR_CODE, "cookie有效期不能小于一天");
		}
		setCookie(response, tokenName, tokenValue, days * One_DAY, TOKEN_SCOPE);
	}

}

