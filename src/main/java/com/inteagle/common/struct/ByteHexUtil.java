package com.inteagle.common.struct;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

import com.inteagle.apis.struct.entity.HelmetSensorDataStruct;
import com.inteagle.common.util.DateUtil;

import struct.JavaStruct;

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

//		String sof = "a5a5";
//		sof = ByteHexUtil.changeType(sof);
//
//		// CMD 16进制
//		// Integer.toHexString(0712)
//		String cmd = "02be";
//		cmd = ByteHexUtil.changeType(cmd);
//		
//		HelmetSensorDataStruct helmetSensorDataStruct=new HelmetSensorDataStruct();
//		short id= 001;
//		helmetSensorDataStruct.setId(id);
//		helmetSensorDataStruct.setVol(10);
//		helmetSensorDataStruct.setTemp(20);
//		helmetSensorDataStruct.setHelmet_on(1);
//
//		// 转成字节数组
//		byte[] start_byte = JavaStruct.pack(helmetSensorDataStruct);
//		String data = ByteHexUtil.bytes2HexStr(start_byte);
//
//		System.out.println("data------------" + data);
//
//		Integer data_len = data.length();
//
//		System.out.println("data.length----" + data_len);
//
//		String len_hex_after = data_len.toHexString(data_len);
//
//		System.out.println("len_hex_after----" + len_hex_after);
//
//		// 10进制转成16进制
//		String len_hex = intToHex(data_len);
//		System.out.println("len_hex----" + len_hex);
//
//		String after = addZeroForNum(len_hex, 4);
//
//		System.out.println("after----" + after);
//
//		// 数据长度 10进制
//		long len_num = Long.parseLong("0024", 16) & 0x0fff;
//		System.out.println("长度len_num------" + len_num);
//
//		String len = "2008";
//		len = ByteHexUtil.changeType(len);
//
//		// 按照协议 截取到crc的16进制值
//		String crc = len + cmd + data;
//		// System.out.println("crc-------" + crc);
//
//		// 16进制转成字节数组
//		byte[] crc_byte = ByteHexUtil.hex2Byte(crc);
//
//		// 传入字节数组 求出crc的校验值字节
//		byte crc_after = CRC8.calcCrc8(crc_byte);
//
//		// 将校验值字节放入数组中 转成16进制数据
//		byte[] crc_after_array = { crc_after };
//		crc = ByteHexUtil.byte2HexStr(crc_after_array);
//
//		String eof = "5a5a";
//		eof = ByteHexUtil.changeType(eof);
//
//		String msg = sof + len + cmd + data + crc + eof;
//		System.out.println("msg-----" + msg);

		// CMD 10进制
//		long cmd_ten = Long.parseLong("02bf", 16) & 0x0fff;
//		System.out.println("cmd_ten-----" + cmd_ten);
//		
//		String cmd = Integer.toHexString(703);
//		System.out.println("cmd-----" + cmd);
//		String source = "study hard and make progress everyday";
//		System.out.println("source : " + source);

//		String hexStr = bytes2HexStr(source.getBytes("utf8")); // 编码
//		System.out.println("encode result : " + hexStr);

		// 精确到毫秒
		// 获取当前时间戳
//        System.out.println(System.currentTimeMillis());
//        System.out.println(Calendar.getInstance().getTimeInMillis());
//        System.out.println(new Date().getTime());

		String now_time = DateUtil.getDateTimeStr();

		System.out.println("now_time---------" + now_time);

		Long content = Calendar.getInstance().getTimeInMillis() / 1000;

		System.out.println(Long.toString(content));
//		
//        //a5a500122133009542db000042f700000000000000000040115a5a
//		byte[] rawSource = hexStr2Bytes("2006"); // 解码
//		System.out.println("decode result : " + rawSource);
//
//		String hexStr_2 = bytes2HexStr(rawSource); // 编码
//		System.out.println("encode result : " + hexStr_2);
//		
//		long hexStr_long=Long.parseLong(hexStr_2);
//		System.out.println("hexStr_long------"+hexStr_long);

//		byte[] b = hexStr2Bytes(rawSource);
//
//		String str = "{";
//		for (int i = 0; i < b.length; i++) {
////			System.out.printf("b[%d]: %d\n", i, b[i]);
//			str += b[i] + ",";
//		}
//		str += "}";
//		System.out.println("str-----" + str);

	}

	// 字符串长度不够 前后补0
	public static String addZeroForNum(String str, int strLength) {
		int strLen = str.length();
		StringBuffer sb = null;
		while (strLen < strLength) {
			sb = new StringBuffer();
			sb.append("0").append(str); // 左补0
			// sb.append(str).append("0"); //右补0
			str = sb.toString();
			strLen = str.length();
		}
		return str;
	}

	// 10进制转16进制
	public static String intToHex(int n) {
		// StringBuffer s = new StringBuffer();
		StringBuilder sb = new StringBuilder(8);
		String a;
		char[] b = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		while (n != 0) {
			sb = sb.append(b[n % 16]);
			n = n / 16;
		}
		a = sb.reverse().toString();
		return a;
	}

	public static String binaryToDecimal(int n) {
		String str = "";
		while (n != 0) {
			str = n % 2 + str;
			n = n / 2;
		}
		return str;
	}

	public static String changeType(String str) {
		byte[] rawSource = hexStr2Bytes(str); // 解码
		String hexStr_2 = bytes2HexStr(rawSource); // 编码
		return hexStr_2;
	}

	// 字符串转 ASCII 码
	public static String stringToAscii(String value) {
		StringBuffer sbu = new StringBuffer();
		char[] chars = value.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			if (i != chars.length - 1) {
				sbu.append((int) chars[i]);
			} else {
				sbu.append((int) chars[i]);
			}
		}
		return sbu.toString();
	}

	// byte转为hex串
	public static String bytes2HexStr(byte[] byteArr) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < byteArr.length; i++) {
			String hex = Integer.toHexString(byteArr[i] & 0xFF);
			if (hex.length() < 2) {
				sb.append(0);
			}
			sb.append(hex);
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
	 * Hex字符串转byte
	 * 
	 * @param inHex 待转换的Hex字符串
	 * @return 转换后的byte
	 */
	public static byte hexToByte(String inHex) {
		return (byte) Integer.parseInt(inHex, 16);
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

	// 判断浮点数（double和float）
	public static boolean isDouble(String str) {
		if (null == str || "".equals(str)) {
			return false;
		}
		Pattern pattern = Pattern.compile("^[-\\+]?[.\\d]*$");
		return pattern.matcher(str).matches();
	}

	/**
	 * 用于建立十六进制字符的输出的小写字符数组
	 */
	private static final char[] DIGITS_LOWER = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
			'e', 'f' };

	/**
	 * 用于建立十六进制字符的输出的大写字符数组
	 */
	private static final char[] DIGITS_UPPER = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D',
			'E', 'F' };

	/**
	 * 字符串转换成十六进制字符串
	 *
	 * @param String str 待转换的ASCII字符串
	 * @return String 每个Byte之间空格分隔，如: [61 6C 6B]
	 */
	public static String str2HexStr(String str) {

		char[] chars = "0123456789ABCDEF".toCharArray();
		StringBuilder sb = new StringBuilder("");
		byte[] bs = str.getBytes();
		int bit;

		for (int i = 0; i < bs.length; i++) {
			bit = (bs[i] & 0x0f0) >> 4;
			sb.append(chars[bit]);
			bit = bs[i] & 0x0f;
			sb.append(chars[bit]);
			sb.append(' ');
		}
		return sb.toString().trim();
	}

	/**
	 * 十六进制转换字符串
	 *
	 * @param String str Byte字符串(Byte之间无分隔符 如:[616C6B])
	 * @return String 对应的字符串
	 */
	public static String hexStr2Str(String hexStr) {
		String str = "0123456789ABCDEF";
		char[] hexs = hexStr.toCharArray();
		byte[] bytes = new byte[hexStr.length() / 2];
		int n;

		for (int i = 0; i < bytes.length; i++) {
			n = str.indexOf(hexs[2 * i]) * 16;
			n += str.indexOf(hexs[2 * i + 1]);
			bytes[i] = (byte) (n & 0xff);
		}
		return new String(bytes);
	}

	/**
	 * bytes转换成十六进制字符串
	 *
	 * @param byte[] b byte数组
	 * @return String 每个Byte值之间空格分隔
	 */
	public static String byte2HexStr(byte[] b) {
		String stmp = "";
		StringBuilder sb = new StringBuilder("");
		for (int n = 0; n < b.length; n++) {
			stmp = Integer.toHexString(b[n] & 0xFF);
			sb.append((stmp.length() == 1) ? "0" + stmp : stmp);
//			sb.append(" ");
		}
		return sb.toString().trim();
	}

	/**
	 * bytes字符串转换为Byte值
	 *
	 * @param String src Byte字符串，每个Byte之间没有分隔符
	 * @return byte[]
	 */
//    public static byte[] hexStr2Bytes(String src) {
//        int m = 0, n = 0;
//        int l = src.length() / 2;
//        System.out.println(l);
//        byte[] ret = new byte[l];
//        for (int i = 0; i < l; i++) {
//            m = i * 2 + 1;
//            n = m + 1;
//            ret[i] = Byte.decode("0x" + src.substring(i * 2, m) + src.substring(m, n));
//        }
//        return ret;
//    }

	/**
	 * String的字符串转换成unicode的String
	 *
	 * @param String strText 全角字符串
	 * @return String 每个unicode之间无分隔符
	 * @throws Exception
	 */
	public static String strToUnicode(String strText) throws Exception {
		char c;
		StringBuilder str = new StringBuilder();
		int intAsc;
		String strHex;
		for (int i = 0; i < strText.length(); i++) {
			c = strText.charAt(i);
			intAsc = (int) c;
			strHex = Integer.toHexString(intAsc);
			if (intAsc > 128)
				str.append("\\u" + strHex);
			else
				// 低位在前面补00
				str.append("\\u00" + strHex);
		}
		return str.toString();
	}

	/**
	 * unicode的String转换成String的字符串
	 *
	 * @param String hex 16进制值字符串 （一个unicode为2byte）
	 * @return String 全角字符串
	 */
	public static String unicodeToString(String hex) {
		int t = hex.length() / 6;
		StringBuilder str = new StringBuilder();
		for (int i = 0; i < t; i++) {
			String s = hex.substring(i * 6, (i + 1) * 6);
			// 高位需要补上00再转
			String s1 = s.substring(2, 4) + "00";
			// 低位直接转
			String s2 = s.substring(4);
			// 将16进制的string转为int
			int n = Integer.valueOf(s1, 16) + Integer.valueOf(s2, 16);
			// 将int转换为字符
			char[] chars = Character.toChars(n);
			str.append(new String(chars));
		}
		return str.toString();
	}

	/**
	 * 将字节数组转换为十六进制字符数组
	 *
	 * @param data byte[]
	 * @return 十六进制char[]
	 */
	public static char[] encodeHex(byte[] data) {
		return encodeHex(data, true);
	}

	/**
	 * 将字节数组转换为十六进制字符数组
	 *
	 * @param data        byte[]
	 * @param toLowerCase <code>true</code> 传换成小写格式 ， <code>false</code> 传换成大写格式
	 * @return 十六进制char[]
	 */
	public static char[] encodeHex(byte[] data, boolean toLowerCase) {
		return encodeHex(data, toLowerCase ? DIGITS_LOWER : DIGITS_UPPER);
	}

	/**
	 * 将字节数组转换为十六进制字符数组
	 *
	 * @param data     byte[]
	 * @param toDigits 用于控制输出的char[]
	 * @return 十六进制char[]
	 */
	protected static char[] encodeHex(byte[] data, char[] toDigits) {
		int l = data.length;
		char[] out = new char[l << 1];
		// two characters form the hex value.
		for (int i = 0, j = 0; i < l; i++) {
			out[j++] = toDigits[(0xF0 & data[i]) >>> 4];
			out[j++] = toDigits[0x0F & data[i]];
		}
		return out;
	}

	/**
	 * 将字节数组转换为十六进制字符串
	 *
	 * @param data byte[]
	 * @return 十六进制String
	 */
	public static String encodeHexStr(byte[] data) {
		return encodeHexStr(data, true);
	}

	/**
	 * 将字节数组转换为十六进制字符串
	 *
	 * @param data        byte[]
	 * @param toLowerCase <code>true</code> 传换成小写格式 ， <code>false</code> 传换成大写格式
	 * @return 十六进制String
	 */
	public static String encodeHexStr(byte[] data, boolean toLowerCase) {
		return encodeHexStr(data, toLowerCase ? DIGITS_LOWER : DIGITS_UPPER);
	}

	/**
	 * 将字节数组转换为十六进制字符串
	 *
	 * @param data     byte[]
	 * @param toDigits 用于控制输出的char[]
	 * @return 十六进制String
	 */
	protected static String encodeHexStr(byte[] data, char[] toDigits) {
		return new String(encodeHex(data, toDigits));
	}

	/**
	 * 将十六进制字符数组转换为字节数组
	 *
	 * @param data 十六进制char[]
	 * @return byte[]
	 * @throws RuntimeException 如果源十六进制字符数组是一个奇怪的长度，将抛出运行时异常
	 */
	public static byte[] decodeHex(char[] data) {

		int len = data.length;

		if ((len & 0x01) != 0) {
			throw new RuntimeException("Odd number of characters.");
		}

		byte[] out = new byte[len >> 1];

		// two characters form the hex value.
		for (int i = 0, j = 0; j < len; i++) {
			int f = toDigit(data[j], j) << 4;
			j++;
			f = f | toDigit(data[j], j);
			j++;
			out[i] = (byte) (f & 0xFF);
		}

		return out;
	}

	/**
	 * 将十六进制字符转换成一个整数
	 *
	 * @param ch    十六进制char
	 * @param index 十六进制字符在字符数组中的位置
	 * @return 一个整数
	 * @throws RuntimeException 当ch不是一个合法的十六进制字符时，抛出运行时异常
	 */
	protected static int toDigit(char ch, int index) {
		int digit = Character.digit(ch, 16);
		if (digit == -1) {
			throw new RuntimeException("Illegal hexadecimal character " + ch + " at index " + index);
		}
		return digit;
	}

	public static String convertHexToString(String hex) {

		StringBuilder sb = new StringBuilder();
		StringBuilder temp = new StringBuilder();

		// 49204c6f7665204a617661 split into two characters 49, 20, 4c...
		for (int i = 0; i < hex.length() - 1; i += 2) {

			// grab the hex in pairs
			String output = hex.substring(i, (i + 2));
			// convert hex to decimal
			int decimal = Integer.parseInt(output, 16);
			// convert the decimal to character
			sb.append((char) decimal);

			temp.append(decimal);
		}

		return sb.toString();
	}

}