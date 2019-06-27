package com.inteagle.common.exception;

/**
 * 
* @ClassName: HttpException
* @Description: TODO(Http请求异常)
* @author IVAn
* @date 2019年6月27日
*
 */
public class HttpException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public HttpException(String message) {
		super(message);
	}

	public HttpException(Throwable cause) {
		super(cause);
	}

	/**
	 * 抛出异常
	 * 
	 * @param msg：错误信息
	 * @author IVAn
	 * @createDate 2018年7月12日 下午2:32:31
	 */
	public static void throwException(String msg) {
		throw new HttpException(msg);
	}

	/**
	 * 抛出异常
	 * 
	 * @param e：错误信息
	 * @author IVAn
	 * @createDate 2018年7月12日 下午2:32:31
	 */
	public static void throwException(Exception e) {
		throw new HttpException(e);
	}
}
