package com.inteagle.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 
 * @ClassName: InteagleApplication
 * @Description: TODO(项目启动类)
 * @author IVAn
 * @date 2019年6月25日
 * 
 */
@SpringBootApplication
@ComponentScan(basePackages = { "com.inteagle"})
@MapperScan("com.inteagle.dao.*")
@EnableScheduling
public class InteagleApplication extends SpringBootServletInitializer {

	protected static final Logger logger = LoggerFactory.getLogger(InteagleApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(InteagleApplication.class, args);
		logger.info("SpringBoot加载完成...");
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(InteagleApplication.class);
	}
}
