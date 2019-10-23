package com.inteagle.service.bimface;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.stereotype.Component;

/**
 * 
 * @ClassName: CharacterEncodingFilter
 * @Description: TODO(设置通用的字符编码为UTF-8)
 * @author IVAn
 * @date 2019年7月5日下午3:49:05
 *
 */
@Component
public class BimCharacterEncodingFilter implements Filter {
	private final static String UTF8 = "utf-8";

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding(UTF8);
		response.setCharacterEncoding(UTF8);
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
	}

}
