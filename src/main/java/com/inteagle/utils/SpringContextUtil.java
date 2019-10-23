package com.inteagle.utils;

import org.springframework.context.ApplicationContextAware;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

/**
 * 
 * @ClassName: SpringContextUtil
 * @Description: TODO(Spring 容器管理工具类)
 * @author IVAn
 * @date 2019年7月3日下午4:18:48
 *
 */
public class SpringContextUtil implements ApplicationContextAware {

	// spring容器
	private static ApplicationContext context;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		context = applicationContext;
	}

	public static ApplicationContext getContext() {
		if (context == null) {
			throw new RuntimeException("获取spring容器失败");
		}
		return context;
	}

	public static <T> T getBean(Class<T> requiredType) {
		return getContext().getBean(requiredType);
	}

	public static <T> T getBean(String name, Class<T> requiredType) {
		return getContext().getBean(name, requiredType);
	}

}
