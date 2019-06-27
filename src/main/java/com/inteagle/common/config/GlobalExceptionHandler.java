package com.inteagle.common.config;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.inteagle.common.entity.JsonResult;
import com.inteagle.common.exception.BusinessException;

@ControllerAdvice
@ResponseBody
@SuppressWarnings({ "rawtypes", "unchecked" })
public class GlobalExceptionHandler {

	@ExceptionHandler(Exception.class)
	public JsonResult handleException(Exception e) {
		return new JsonResult(BusinessException.DEFAULT_CODE, "系统异常，请稍后再试");
	}

	// 自定义异常
	@ExceptionHandler(BusinessException.class)
	public JsonResult handleException(BusinessException e) {
		return new JsonResult(e);
	}

}
