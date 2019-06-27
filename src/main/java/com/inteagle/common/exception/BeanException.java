package com.inteagle.common.exception;

/**
 * 
* @ClassName: BeanException
* @Description: TODO(JavaBean异常)
* @author IVAn
* @date 2019年6月27日
*
 */
public class BeanException extends RuntimeException{

	/**
	* @Fields field:field:{todo}(序列化ID)
	*/
	private static final long serialVersionUID = 1L;
	
	public BeanException(String message) {
		super(message);
	}

	public BeanException(Throwable cause) {
		super(cause);
	}

	/**
	 * 抛出异常
	 * 
	 * @param msg：错误信息
	 * @author IVAn
	 * @createDate 2019年6月27日
	 */
	public static void throwException(String msg) {
		throw new BeanException(msg);
	}

	/**
	 * 抛出异常
	 * 
	 * @param e：错误信息
	 * @author IVAn
	 * @createDate 2019年6月27日
	 */
	public static void throwException(Exception e) {
		throw new BeanException(e);
	}

}
