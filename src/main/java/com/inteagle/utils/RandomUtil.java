package com.inteagle.utils;


import java.util.Random;

/**
 * 随机工具类
 * 
* @author IVAn
 * @CreateDate 2019年6月27日
 */
public class RandomUtil {

	private RandomUtil() {
		throw new RuntimeException("new RandomUtil instance error");
	}

	/**
	 * 获取随机验证码
	 * 
	 * @param codeArrary：随机验证码数组
	 * @param length：随机验证码长度
	 * @return String：验证码
	 * @author IVAn
	 * @createDate 2018年5月23日 下午5:02:23
	 */
	public static String getRandomCode(char[] codeArrary, int length) {
		StringBuilder code = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			code.append(codeArrary[random.nextInt(codeArrary.length)]);
		}
		return code.toString();
	}

	/**
	 * 生成指定长度的验证码
	 * 
	 * @param length:验证码长度
	 * @return String:随机验证码
	 * @author IVAn
	 * @CreateDate 2018年5月4日 11:36:31
	 */
	public static String getRandomCode(int length) {
		char[] codeArrary = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
				'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C',
				'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
				'Y', 'Z' };
		return getRandomCode(codeArrary, length);
	}

	/**
	 * 生成指定长度的纯字母验证码
	 * 
	 * @param length:验证码长度
	 * @return String:随机验证码
	 * @author IVAn
	 * @CreateDate 2018年5月4日 11:36:31
	 */
	public static String getRandomWordCode(int length) {
		char[] codeArrary = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
				's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
				'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
		return getRandomCode(codeArrary, length);
	}

	/**
	 * 随机生成6位数字随机验证码
	 * 
	 * @param length:验证码长度
	 * @return String:手机验证码
	 * @author IVAn
	 * @CreateDate 2018年5月4日 11:38:01
	 */
	public static String getRandomNumberCode(int length) {
		char[] codeArrary = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
		return getRandomCode(codeArrary, length);
	}

}

