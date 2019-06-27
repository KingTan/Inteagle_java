package com.inteagle.common.exception;
/**
 * 
* @ClassName: XmlException
* @Description: TODO(Xml解析异常)
* @author IVAn
* @date 2019年6月27日
*
 */
public class XmlException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public XmlException(String message) {
		super(message);
	}

	public XmlException(Throwable cause) {
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
		throw new XmlException(msg);
	}

	/**
	 * 抛出异常
	 * 
	 * @param msg：错误信息
	 * @author IVAn
	 * @createDate 2018年7月12日 下午2:32:31
	 */
	public static void throwException(Exception e) {
		throw new XmlException(e);
	}
	
}
