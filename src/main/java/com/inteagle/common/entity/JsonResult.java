package com.inteagle.common.entity;

import java.io.Serializable;

import com.inteagle.common.exception.BusinessException;

/**
 * 
 * @ClassName: JsonResult
 * @Description: TODO(自定义相应对象)
 * @author IVAn
 * @date 2019年6月27日
 *
 * @param <T>
 */
public class JsonResult<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 状态属性：200表示成功，其它表示失败
	 */
	private int state;

	/**
	 * 返回提示信息
	 */
	private String message;

	/**
	 * 服务器处理成功返回json数据
	 */
	private T data;

	/**
	 * 返回状态200成功
	 */
	public static final Integer SUCCESS = 200;

	/**
	 * 服务器请求成功消息提示
	 */
	public static final String SUCCESS_MESSAGE = "请求成功";

	/**
	 * 返回状态500成功
	 */
	public static final Integer ERROR = 500;

	/**
	 * 服务器请求异常消息提示
	 */
	public static final String ERROR_MESSAGE = "服务器繁忙";

	public JsonResult(Integer state, T data, String message) {
		this.state = state;
		this.data = data;
		this.message = message;
	}

	public JsonResult(T data, String message) {
		this(SUCCESS, data, message);
	}

	public JsonResult(T data) {
		this(data, SUCCESS_MESSAGE);
	}

	public JsonResult(String message) {
		this(null, message);
	}

	public JsonResult(int line, String successMessage, String errorMessage) {
		if (line <= 0) {
			BusinessException.throwException(BusinessException.BUSINESS_ERROR_CODE, errorMessage);
		}
		state = SUCCESS;
		message = successMessage;
		this.data = null;
	}

	/**
	 * 失败
	 */
	public JsonResult(BusinessException businessException) {
		this(businessException.getCode(), (T) null, businessException.getMessage());
	}

	/**
	 * 失败 Description: 构造函数
	 */
	public JsonResult(Throwable e) {
		this(ERROR, null, ERROR_MESSAGE);
//		this(ERROR, null, e.getMessage());
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

}
