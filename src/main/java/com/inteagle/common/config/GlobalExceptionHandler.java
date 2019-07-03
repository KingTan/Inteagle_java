package com.inteagle.common.config;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.inteagle.common.entity.JsonResult;
import com.inteagle.common.exception.BusinessException;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.MissingServletRequestParameterException;

@ControllerAdvice
@ResponseBody
@SuppressWarnings({ "rawtypes", "unchecked" })
@Slf4j
public class GlobalExceptionHandler {

	@ExceptionHandler(Exception.class)
	public JsonResult handleException(Exception e) {
		log.error(e.toString());
		return new JsonResult(BusinessException.DEFAULT_CODE, "系统异常，请稍后再试");
	}

	// 业务异常
	@ExceptionHandler(BusinessException.class)
	public JsonResult BusinessExceptionHandler(BusinessException e) {
		log.error(e.toString());
		return new JsonResult(e);
	}

	// spring参数校验异常
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public JsonResult MissingServletRequestParameterException(BusinessException e) {
		log.error(e.toString());
		return new JsonResult(e);
	}
	
	//其他异常处理对象
	@ExceptionHandler(Throwable.class)
	public JsonResult ExceptionHandler(BusinessException e) {
		log.error(e.toString());
		return new JsonResult(e);
	}
	
	

}
