package com.inteagle.common.struct;

import java.util.regex.Pattern;

/**
 * 
 * @ClassName: ByteHexUtil
 * @Description: TODO(字符串 hex码转换工具类)
 * @author IVAn
 * @date 2019年7月1日上午9:57:26
 *
 */
public class ByteHexUtil {

	public static void main(String[] args) throws Exception {
		String source = "study hard and make progress everyday";
		System.out.println("source : " + source);

		String hexStr = bytes2HexStr(source.getBytes("utf8")); // 编码
		System.out.println("encode result : " + hexStr);

		String rawSource = new String(hexStr2Bytes(hexStr), "utf8"); // 解码
		System.out.println("decode result : " + rawSource);

		byte[] b = hexStr2Bytes(rawSource);

		String str = "{";

		for (int i = 0; i < b.length; i++) {
			System.out.printf("b[%d]: %d\n", i, b[i]);
			str += b[i] + ",";
		}

		str += "}";

		System.out.println("str-----" + str);

	}

	// byte转为hex串
	public static String bytes2HexStr(byte[] byteArr) {
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
	public static byte[] hexStr2Bytes(String hexStr) {
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

	/**
	 * 2进制转16进制
	 * 
	 * @param bString 2进制字符串
	 * @return
	 */
	public static String binary2Hex(String bString) {
		if (bString == null || bString.equals("") || bString.length() % 8 != 0)
			return null;
		StringBuffer tmp = new StringBuffer();
		int iTmp = 0;
		for (int i = 0; i < bString.length(); i += 4) {
			iTmp = 0;
			for (int j = 0; j < 4; j++) {
				iTmp += Integer.parseInt(bString.substring(i + j, i + j + 1)) << (4 - j - 1);
			}
			tmp.append(Integer.toHexString(iTmp));
		}
		return tmp.toString();
	}

	/**
	 * 将二进制转换成16进制
	 * 
	 * @param buf
	 * @return
	 */
	public static String binary2Hex(byte buf[]) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}

	/**
	 * @description 2进制转10进制
	 * @author IVAn
	 * @date 2019年7月2日 下午4:58:52
	 * @param binaryNumber
	 * @return
	 */
	public static int BinaryToDecimal(int binaryNumber) {
		int decimal = 0;
		int p = 0;
		while (true) {
			if (binaryNumber == 0) {
				break;
			} else {
				int temp = binaryNumber % 10;
				decimal += temp * Math.pow(2, p);
				binaryNumber = binaryNumber / 10;
				p++;
			}
		}
		return decimal;
	}

	/**
	 * 16进制转2进制
	 * 
	 * @param hexString
	 * @return
	 */
	public static String hex2Binary(String hexString) {
		if (hexString == null || hexString.length() % 2 != 0)
			return null;
		String bString = "", tmp;
		for (int i = 0; i < hexString.length(); i++) {
			tmp = "0000" + Integer.toBinaryString(Integer.parseInt(hexString.substring(i, i + 1), 16));
			bString += tmp.substring(tmp.length() - 4);
		}
		return bString;
	}

	/**
	 * 将16进制转换为二进制
	 * 
	 * @param hexStr
	 * @return
	 */
	public static byte[] hex2Byte(String hexStr) {
		if (hexStr.length() < 1)
			return null;
		byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++) {
			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}

	/**
	 * * 十六进制字符串转十进制 * * @param hex * 十六进制字符串 * @return 十进制数值
	 * 
	 */
	public static int hexStringToAlgorism(String hex) {
		hex = hex.toUpperCase();
		int max = hex.length();
		int result = 0;
		for (int i = max; i > 0; i--) {
			char c = hex.charAt(i - 1);
			int algorism = 0;
			if (c >= '0' && c <= '9') {
				algorism = c - '0';
			} else {
				algorism = c - 55;
			}
			result += Math.pow(16, max - i) * algorism;
		}
		return result;
	}

	/**
	 * 十六进制转为十进制
	 */
	public static String getHexToTen(String hex) {
		return String.valueOf(Integer.parseInt(hex, 16));
	}

	/**
	 * 计算CRC16校验码 逐个求和
	 *
	 * @param bytes 字节数组
	 * @return {@link String} 校验码
	 * @since 1.0
	 */
	public static String getCRC_16(byte[] bytes) {
		int CRC = 0x0000ffff;
		int POLYNOMIAL = 0x0000a001;
		int i, j;
		for (i = 0; i < bytes.length; i++) {
			CRC ^= ((int) bytes[i] & 0x000000ff);
			for (j = 0; j < 8; j++) {
				if ((CRC & 0x00000001) != 0) {
					CRC >>= 1;
					CRC ^= POLYNOMIAL;
				} else {
					CRC >>= 1;
				}
			}
		}
		if (Integer.toHexString(CRC).toUpperCase().length() == 2) {
			return "00" + Integer.toHexString(CRC).toUpperCase();
		} else if (Integer.toHexString(CRC).toUpperCase().length() == 3) {
			return "0" + Integer.toHexString(CRC).toUpperCase();
		}
		return Integer.toHexString(CRC).toUpperCase();
	}

	/**
	 * 指令校验和,并取出后两位字节
	 */
	public static String getSum16(byte[] msg, int length) {
		long mSum = 0;
		byte[] mByte = new byte[length];
		for (byte byteMsg : msg) {
			long mNum = ((long) byteMsg >= 0) ? (long) byteMsg : ((long) byteMsg + 256);
			mSum += mNum;
		}
		for (int liv_Count = 0; liv_Count < length; liv_Count++) {
			mByte[length - liv_Count - 1] = (byte) (mSum >> (liv_Count * 8) & 0xff);
		}
		return byteToStr(msg, length) + byteToStr(mByte, mByte.length)
				.substring(byteToStr(mByte, mByte.length).length() - 4, byteToStr(mByte, mByte.length).length());
	}

	/**
	 * 高低位转换
	 */
	public static String getShort(String data) {
		switch (data.length()) {
		case 1:
			data = "000" + data;
			break;
		case 2:
			data = "00" + data;
			break;
		case 3:
			data = "0" + data;
			break;
		}
		String s1 = data.substring(0, 2);
		String s2 = data.substring(2, 4);
		return s2 + s1;
	}

	// 判断浮点数（double和float）
	public static boolean isDouble(String str) {
		if (null == str || "".equals(str)) {
			return false;
		}
		Pattern pattern = Pattern.compile("^[-\\+]?[.\\d]*$");
		return pattern.matcher(str).matches();
	}

	/**
	 * 计算校验值
	 */
	public static String getCRC_16CheckSum(String hexdata) {
		if (hexdata == null || hexdata.equals("")) {
			return "00";
		}
		hexdata = hexdata.replaceAll(" ", "");
		int total = 0;
		int len = hexdata.length();
		if (len % 2 != 0) {
			return "00";
		}
		int num = 0;
		while (num < len) {
			String s = hexdata.substring(num, num + 2);
			total += Integer.parseInt(s, 16);
			num = num + 2;
		}
		String data = hexInt(total);
		return data.substring(data.length() - 2, data.length());
	}

	public static String hexInt(int total) {
		int a = total / 256;
		int b = total % 256;
		if (a > 255) {
			return hexInt(a) + format(b);
		}
		return format(a) + format(b);
	}

	public static String format(int hex) {
		String hexa = Integer.toHexString(hex);
		int len = hexa.length();
		if (len < 2) {
			hexa = "0" + hexa;
		}
		return hexa;
	}

	/**
	 * 接收到的字节数组转换16进制字符串
	 */
	public static String byteToStr(byte[] b, int size) {
		String ret = "";
		for (int i = 0; i < size; i++) {
			String hex = Integer.toHexString(b[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			ret += hex.toUpperCase();
		}
		return ret;
	}

}