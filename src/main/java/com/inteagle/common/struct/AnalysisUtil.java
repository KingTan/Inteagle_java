package com.inteagle.common.struct;

import java.io.UnsupportedEncodingException;
import java.nio.ByteOrder;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.inteagle.apis.struct.entity.IdInfoStruct;
import com.inteagle.apis.struct.service.IdInfoStructService;
import com.inteagle.common.websocket.server.WebSocketServer;

import lombok.extern.slf4j.Slf4j;
import struct.JavaStruct;
import struct.StructException;

/**
 * 
 * @ClassName: AnalysisUtil
 * @Description: TODO(按照协议 解析工具类)
 * @author IVAn
 * @date 2019年7月2日下午4:12:41
 *
 */
@Slf4j
@Component
public class AnalysisUtil {

	@Autowired
	private IdInfoStructService idInfoStructService;

	private static AnalysisUtil analysisUtil;

	// 项目启动时 注入到Spring容器
	@PostConstruct
	public void init() {
		analysisUtil = this;
		analysisUtil.idInfoStructService = this.idInfoStructService;
	}

	// 命令类型
	public static final Map<Integer, String> CMD_TYPE = new HashMap<Integer, String>();

	// 公共命令
	public static final Map<Integer, String> CMD = new HashMap<Integer, String>();

	static {
		CMD_TYPE.put(0, "CMD_TYPE_SET");
		CMD_TYPE.put(1, "CMD_TYPE_GET");
		CMD_TYPE.put(2, "CMD_TYPE_PUT");
		CMD_TYPE.put(3, "CMD_TYPE_ACK");

		CMD.put(1, "CMD_COMMON_NONE");
		CMD.put(2, "CMD_COMMON_HEARTBEAT");
		CMD.put(3, "CMD_COMMON_GET_DEVICE_INFO");
		CMD.put(4, "CMD_COMMON_REBOOT");
		CMD.put(5, "CMD_COMMON_SLEEP");
		CMD.put(6, "CMD_COMMON_TIME_SYNC");
		CMD.put(7, "CMD_COMMON_LAST");

		CMD.put(50, "CMD_COMMON_RESERVED");
		CMD.put(51, "CMD_BR_GET_ROUTERS"); // 获取路由表，有子网的所有节点ID和IPV6地址
		CMD.put(52, "CMD_BR_SET_NEW_NODE");
		CMD.put(53, "CMD_BR_SET_DIO_PERIOD");

		CMD.put(100, "CMD_BR_RESERVED");
		CMD.put(101, "CMD_CAMERA_GET_VISIBLE");
		CMD.put(102, "CMD_CAMERA_GET_VISIBLE_ALL");
		CMD.put(103, "CMD_CAMERA_GET_FULL_IMG");
		CMD.put(104, "CMD_CAMERA_GET_IMU");// 惯性测量单元
		CMD.put(105, "CMD_CAMERA_RECONFIG");
		CMD.put(106, "CMD_CAMERA_SET_MODE");

		CMD.put(120, "CMD_CAMERA_RESERVED");
		CMD.put(121, "CMD_CAMERA_C_GET_AUX_NUM");// 获取辅助摄像机数量

		// 辅助摄像头命令 151-199
		CMD.put(150, "CMD_CAMERA_C_RESERVED");
		CMD.put(151, "CMD_CAMERA_A_GET_IMG");

		// 安全帽命令 201-249
		CMD.put(150, "CMD_CAMERA_A_RESERVED");
		CMD.put(150, "CMD_HELMET_GET_BLINK_FRE");
		CMD.put(150, "CMD_HELMET_GET_BLINK_PERIOD");
		CMD.put(150, "CMD_HELMET_RESERVED");

		// 基坑设备命令 251-299
		CMD.put(251, "CMD_FRAME_SET_THRESHOLD");
		CMD.put(300, "CMD_FRAME_RESERVED");
	}

	// 解析 paylaod 数据
	// byte[] playload
	public static void validate(String hexStr) throws UnsupportedEncodingException {

		try {
			// SOF头
			String sof_hex = hexStr.substring(0, 4);
//			System.out.println("sof_hex----" + sof_hex);
			// EOF尾
			String eof_hex = hexStr.substring(hexStr.length() - 4);
//			System.out.println("eof_hex----" + eof_hex);

			// 验证头尾
			if (sof_hex.equals("a5a5") && eof_hex.equals("5a5a")) {

				// | SOF | len | cmd | data | crc | EOF |
				// Data:|0xa5 0xa5 | 0x00 0x00 | 0x20 0x02 | 0x02 0x03 | 0x7d | 0x5a 0x5a |
				// len: data
				// crc: len + cmd + data
				// CRC校验类型：CRC8/MAXIM X8+X5+X4+1

				log.info("数据完整,SOF EOF验证成功");
				// 数据长度 16进制
				String len_hex = hexStr.substring(4, 8);
//				System.out.println("长度len_hex------" + len_hex);
				// 数据长度 10进制
				long len_num = Long.parseLong(len_hex, 16);
//				System.out.println("长度len_num------" + len_num);

				// cmd: uint16_t Bit14~Bit15位为转发优先级，优先级4最高0最低。Bit12~Bit13为ACK位作为命令类型:set,get,ack
				String str_2 = ByteHexUtil.hex2Binary(hexStr.substring(8, 12));

				// 2进制 转 10进制
				// 转发优先级
				int forwarding_priority = ByteHexUtil.BinaryToDecimal(Integer.parseInt(str_2.substring(0, 2)));
				// 命令类型
				int cmd_type_bin = ByteHexUtil.BinaryToDecimal(Integer.parseInt(str_2.substring(2, 4)));
				// 命令
				int cmd_bin = ByteHexUtil.BinaryToDecimal(Integer.parseInt(str_2.substring(4, 12)));
				 
				System.out.println("cmd_bin----"+cmd_bin);
				// data+crc+eof
				String data_crc_eof = hexStr.substring(12);

//				System.out.println("data_crc_eof-----" + data_crc_eof);

				// CRC 16进制
				String crc_hex = data_crc_eof.substring((data_crc_eof.length() - 6), (data_crc_eof.length() - 4));

				// CRC 检验数据
				String crc_examine = hexStr.substring(4, (hexStr.length() - 6));

				// CRC 校验数据的字节数组
				byte[] b = ByteHexUtil.hexStr2Bytes(crc_examine);

				// CRC 校验
				byte crc = CRC8.calcCrc8(b);

//				System.out.println("crc_hex-----" + crc_hex);
//				System.out.println("(Integer.toHexString(0x00ff & crc)-----" + (Integer.toHexString(0x00ff & crc)));

				if (crc_hex.equals((Integer.toHexString(0x00ff & crc)).toString())) {
					log.info("crc校验成功....");

					// 数据体
					String data_str = data_crc_eof.substring(0, (data_crc_eof.length() - 6));

					System.out.println("data_str-----" + data_str);

					// JavaStruct解析数据、保存到数据库
					getDataJson(data_str);

				} else {
					log.error("CRC 校验失败.....");
				}

			}

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());

			// TODO Auto-generated catch block
			log.info("解析数据失败...");
		}

	}

	/**
	 * @description 解析数据体
	 * @author IVAn
	 * @date 2019年7月3日 上午10:52:49
	 * @param data
	 */
	public static void getDataJson(String data) {

		if (data.length() > 0) {
			if (data.length() == 44) {
			} else if (data.length() == 36) {

				// 方法2 JavaStruct解析
				try {
					IdInfoStruct struct = new IdInfoStruct();
					byte[] b = ByteHexUtil.hexStr2Bytes(data);

					// 解析成javaStruct
					JavaStruct.unpack(struct, b, ByteOrder.BIG_ENDIAN);

					System.out.println("id----" + struct.id);
					System.out.println("x-----" + struct.x);
					System.out.println("y-----" + struct.y);
					System.out.println("t-----" + struct.t);

					try {
						// 保存到数据库
//						  int result = analysisUtil.idInfoStructService.insert(struct);
//						  if (result > 0) { log.info("数据保存成功..."); }

						// 发送socket消息
						WebSocketServer.sendInfo(JSONObject.toJSONString(struct), "ivan");

					} catch (Exception e) {
						log.error(e.toString());
					}

				} catch (StructException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				/*
				 * //方法1 分段截取 按照协议转进制解析 // 16进制 转 10进制 int id =
				 * ByteHexUtil.hexStringToAlgorism(data.substring(0, 4));
				 * System.out.println("id-----" + id); // hex 转成 float Long x_long =
				 * parseLong(data.substring(4, 12), 16); float x_rate =
				 * Float.intBitsToFloat(x_long.intValue()); System.out.println("x_rate------" +
				 * x_rate); Long y_long = parseLong(data.substring(12, 20), 16); float y_rate =
				 * Float.intBitsToFloat(y_long.intValue()); System.out.println("y_rate-----" +
				 * y_rate); int t_rate = ByteHexUtil.hexStringToAlgorism(data.substring(20,
				 * 36)); System.out.println("t_rate-----" + t_rate);
				 */

			}

		}

	}

//	public static void main(String[] args) {
//		byte[] test = { 1, 2, 3 };
//
//		try {
//			validate(test);
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

	/**
	 * @description 16进制转成10进制
	 * @author IVAn
	 * @date 2019年7月9日 下午2:45:12
	 * @param s
	 * @param radix
	 * @return
	 * @throws NumberFormatException
	 */
	public static long parseLong(String s, int radix) throws NumberFormatException {
		if (s == null) {
			throw new NumberFormatException("null");
		}

		if (radix < Character.MIN_RADIX) {
			throw new NumberFormatException("radix " + radix + " less than Character.MIN_RADIX");
		}
		if (radix > Character.MAX_RADIX) {
			throw new NumberFormatException("radix " + radix + " greater than Character.MAX_RADIX");
		}

		long result = 0;
		boolean negative = false;
		int i = 0, len = s.length();
		long limit = -Long.MAX_VALUE;
		long multmin;
		int digit;

		if (len > 0) {
			char firstChar = s.charAt(0);
			if (firstChar < '0') { // Possible leading "+" or "-"
				if (firstChar == '-') {
					negative = true;
					limit = Long.MIN_VALUE;
				} else if (firstChar != '+')
					throw NumberFormatException.forInputString(s);

				if (len == 1) // Cannot have lone "+" or "-"
					throw NumberFormatException.forInputString(s);
				i++;
			}
			multmin = limit / radix;
			while (i < len) {
				// Accumulating negatively avoids surprises near MAX_VALUE
				digit = Character.digit(s.charAt(i++), radix);
				if (digit < 0) {
					throw NumberFormatException.forInputString(s);
				}
				if (result < multmin) {
					throw NumberFormatException.forInputString(s);
				}
				result *= radix;
				if (result < limit + digit) {
					throw NumberFormatException.forInputString(s);
				}
				result -= digit;
			}
		} else {
			throw NumberFormatException.forInputString(s);
		}
		return negative ? result : -result;
	}

}
