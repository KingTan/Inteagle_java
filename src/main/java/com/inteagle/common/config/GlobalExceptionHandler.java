package com.inteagle.common.config;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.inteagle.common.entity.JsonResult;
import com.inteagle.common.exception.BusinessException;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.MissingServletRequestParameterException;

/**
 * 
* @ClassName: GlobalExceptionHandler
* @Description: TODO(全局异常配置)
* @author IVAn
* @date 2019年7月23日上午10:53:29
*
 */
@ControllerAdvice
@ResponseBody
@SuppressWarnings({ "rawtypes" })
@Slf4j
public class GlobalExceptionHandler {

	@SuppressWarnings("unchecked")
	@ExceptionHandler(Exception.class)
	public JsonResult handleException(Exception e) {
		log.error(e.toString());
		return new JsonResult(JsonResult.ERROR, e.toString(), "系统异常，请稍后再试");
	}

	// 业务异常
	@ExceptionHandler(BusinessException.class)
	public JsonResult BusinessExceptionHandler(BusinessException e) {
		log.error(e.toString());
		return new JsonResult(e);
	}

	// spring参数校验异常
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public JsonResult MissingServletRequestParameterException(MissingServletRequestParameterException e) {
		log.error(e.toString());
		String msg = "parameter '" + e.getParameterName() + "' can not be null，type is '" + e.getParameterType() + "'";
		return new JsonResult<>(JsonResult.ERROR, null, msg);
	}

	// 其他异常处理对象
	@ExceptionHandler(Throwable.class)
	public JsonResult ExceptionHandler(BusinessException e) {
		log.error(e.toString());
		return new JsonResult(e);
	}

}
