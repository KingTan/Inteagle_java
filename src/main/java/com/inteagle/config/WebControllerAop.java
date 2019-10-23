package com.inteagle.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Enumeration;

import org.aspectj.lang.annotation.Aspect;

/**
 * 
 * @ClassName: WebControllerAop
 * @Description: TODO(使用AOP统一处理Web请求日志)
 * @author IVAn
 * @date 2019年7月8日上午10:50:16
 *
 */
@Aspect
@Component
@SuppressWarnings("unused")
@Slf4j
public class WebControllerAop {

	/**
	 * 指定切点 匹配 com.example.demo.controller包及其子包下的所有类的所有方法
	 */
	@Pointcut("execution(public * com.inteagle.*.controller.*.*(..))")
	public void webLog() {
	}

	/**
	 * 前置通知，方法调用前被调用
	 * 
	 * @param joinPoint
	 */
	@Before("webLog()")
	public void doBefore(JoinPoint joinPoint) {
		// 接收到请求，记录请求内容
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		// 记录下请求内容
		log.info("URL : " + request.getRequestURL().toString());
		log.info("HTTP_METHOD : " + request.getMethod());
		log.info("IP : " + request.getRemoteAddr());
		Enumeration<String> enu = request.getParameterNames();
		while (enu.hasMoreElements()) {
			String name = (String) enu.nextElement();
			log.info("name:{},value:{}", name, request.getParameter(name));
		}
	}

	/**
	 * 处理完请求返回内容
	 * 
	 * @param ret
	 * @throws Throwable
	 */
	@AfterReturning(returning = "ret", pointcut = "webLog()")
	public void doAfterReturning(Object ret) throws Throwable {
		// 处理完请求，返回内容
		log.info("方法的返回值 : " + ret);
	}

	/**
	 * 后置异常通知
	 * 
	 * @param jp
	 */
	@AfterThrowing("webLog()")
	public void throwss(JoinPoint jp) {
		log.info("方法异常时执行.....");
	}

	/**
	 * 后置最终通知,final增强，不管是抛出异常或者正常退出都会执行
	 * 
	 * @param jp
	 */
	@After("webLog()")
	public void after(JoinPoint jp) {

	}

	/**
	 * 环绕通知,环绕增强，相当于MethodInterceptor
	 * 
	 * @param pjp
	 * @return
	 */
	@Around("webLog()")
	public Object arround(ProceedingJoinPoint pjp) {
		try {
			Object o = pjp.proceed();
			return o;
		} catch (Throwable e) {
			e.printStackTrace();
			return null;
		}
	}

}
