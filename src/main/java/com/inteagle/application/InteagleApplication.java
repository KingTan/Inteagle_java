package com.inteagle.application;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @ClassName: InteagleApplication
 * @Description: TODO(项目启动类)
 * @author IVAn
 * @date 2019年6月25日
 * 
 */
@SpringBootApplication
@ComponentScan(basePackages = { "com.inteagle" })
@MapperScan("com.inteagle.dao.*")
@EnableScheduling
@Slf4j
public class InteagleApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(InteagleApplication.class, args);
		log.info("SpringBoot加载完成...");
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(InteagleApplication.class);
	}
}
