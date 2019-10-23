package com.inteagle.utils.struct;

/**
 * 
* @ClassName: NumberFormatException
* @Description: TODO(hex转float异常处理类)
* @author IVAn
* @date 2019年7月3日上午9:57:32
*
 */
public class NumberFormatException extends IllegalArgumentException {

	private static final long serialVersionUID = 1L;

	public NumberFormatException(String s) {
		super(s);
	}

	static NumberFormatException forInputString(String s) {
		return new NumberFormatException("For input string: \"" + s + "\"");
	}

}
