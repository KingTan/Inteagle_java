package com.inteagle.common.exception;

/**
 * 
* @ClassName: BusinessException
* @Description: TODO(业务异常)
* @author IVAn
* @date 2019年6月27日
*
 */
public class BusinessException extends RuntimeException{

	/**
	* @Fields field:field:{todo}(序列化ID)
	*/
	private static final long serialVersionUID = 1L;
	
	public static final int DEFAULT_CODE = 500;

	// 业务异常状态码
	public static final int BUSINESS_ERROR_CODE = 1001;

	// 分页异常状态码
	public static final int PAGE_ERROR_CODE = 1002;

	// 参数异常状态码
	public static final int PARAM_ERROR_CODE = 1003;

	// 附件异常状态码
	public static final int ACCESSORY_ERROR_CODE = 1004;

	// 数据库异常状态码
	public static final int DB_ERROR_CODE = 1005;

	// 身份校验失败状态码
	public static final int IDENTITY_ERROR_CODE = 1006;

	// 系统错误状态码
	public static final int SYSTEM_ERROR_CODE = 1007;

	// 数据为空状态码
	public static final int EMPTY_DATA_CODE = 1008;

	// 未登录或者token失效
	public static final int TOKEN_CODE = 2000;

	private int code;

	public BusinessException(String message) {
		super(message);
		setCode(DEFAULT_CODE);
	}

	public BusinessException(int code, String message) {
		super(message);
		setCode(code);
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	/**
	 * 抛出异常
	 * 
	 * @param msg：错误信息
	 * @author IVAn
	 * @createDate 2018年7月12日 下午2:32:31
	 */
	public static void throwException(String msg) {
		throwException(DEFAULT_CODE, msg);
	}

	/**
	 * 抛出异常
	 * 
	 * @param code：错误码
	 * @param msg：错误信息
	 * @author IVAn
	 * @createDate 2018年7月12日 下午2:32:31
	 */
	public static void throwException(int code, String msg) {
		throw new BusinessException(code, msg);
	}
}
