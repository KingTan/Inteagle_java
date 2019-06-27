package com.inteagle.common.util;

import com.inteagle.common.exception.BusinessException;

/**
 * 用户工具类
 * 
 * @author IVAn
 * @CreateDate 2019年6月27日
 */
public class UserUtil {

	private UserUtil() {
		throw new RuntimeException("new UserUtil instance error");
	}

	/**
	 * 用于保存当前线程userId的ThreadLocal
	 */
	private static final ThreadLocal<String> userLocal = new ThreadLocal<String>() {

		@Override
		protected String initialValue() {
			return null;
		}

	};

	/**
	 * 设置userId
	 * 
	 * @param userId
	 * @author IVAn
	 * @createDate 2018年5月30日 上午11:17:18
	 */
	public static void setUserId(String userId) {
		userLocal.set(userId);
	}

	/**
	 * 获取userId，如果为空会抛出异常
	 * 
	 * @return String
	 * @author IVAn
	 * @createDate 2018年5月30日 上午11:17:11
	 */
	public static String getUserId() {
		String userId = userLocal.get();
		if (userId == null) {
			throw new BusinessException(BusinessException.IDENTITY_ERROR_CODE, "获取当前用户失败");
		}
		return userId;
	}

	/**
	 * 获取userId，如果没有则返回null
	 * 
	 * @return String
	 * @author IVAn
	 * @time 2018年10月30日 下午4:26:10
	 */
	public static String getOptionalUserId() {
		return userLocal.get();
	}
}
