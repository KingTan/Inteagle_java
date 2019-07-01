package com.inteagle.common.util;

public class ByteHexMainUtil {

	public static void main(String[] args) throws Exception {
		String source = "a05bff1157121c";
		System.out.println("source : " + source);

		String hexStr = bytes2HexStr(source.getBytes("utf8")); // 编码
		System.out.println("encode result : " + hexStr);

		String rawSource = new String(hexStr2Bytes(hexStr), "utf8"); // 解码
		System.out.println("decode result : " + rawSource);

	}

	// byte转为hex串
	static String bytes2HexStr(byte[] byteArr) {
		if (null == byteArr || byteArr.length < 1)
			return "";
		StringBuilder sb = new StringBuilder();
		for (byte t : byteArr) {
			if ((t & 0xF0) == 0)
				sb.append("0");
			sb.append(Integer.toHexString(t & 0xFF)); // t & 0xFF 操作是为去除Integer高位多余的符号位（java数据是用补码表示）
		}
		return sb.toString();
	}

	// hex串转为byte
	static byte[] hexStr2Bytes(String hexStr) {
		if (null == hexStr || hexStr.length() < 1)
			return null;

		int byteLen = hexStr.length() / 2;
		byte[] result = new byte[byteLen];
		char[] hexChar = hexStr.toCharArray();
		for (int i = 0; i < byteLen; i++) {
			result[i] = (byte) (Character.digit(hexChar[i * 2], 16) << 4 | Character.digit(hexChar[i * 2 + 1], 16));
		}

		return result;
	}

}
