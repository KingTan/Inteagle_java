package com.inteagle.config;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import com.inteagle.service.bimface.BimCharacterEncodingFilter;


/**
 * 
 * @ClassName: GlobalFilterConfig
 * @Description: TODO(配置过滤器)
 * @author IVAn
 * @date 2019年7月5日下午3:50:22
 *
 */
@SpringBootConfiguration
public class GlobalFilterConfig {

	@Autowired
	private BimCharacterEncodingFilter bimCharacterEncodingFilter;

	@Bean
	public FilterRegistrationBean<Filter> companyUrlFilterRegister() {
		FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>();
		// 注入过滤器
		registration.setFilter(bimCharacterEncodingFilter);
		// 拦截规则
		registration.addUrlPatterns("/*");
		// 过滤器名称
		registration.setName("bimCharacterEncodingFilter");
		// 过滤器顺序
		registration.setOrder(FilterRegistrationBean.LOWEST_PRECEDENCE);
		return registration;
	}

}
