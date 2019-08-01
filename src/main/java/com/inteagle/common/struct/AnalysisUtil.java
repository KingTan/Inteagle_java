package com.inteagle.common.struct;

import java.io.IOException;
import java.nio.ByteOrder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import com.inteagle.apis.struct.entity.IdInfoStruct;
import com.inteagle.apis.struct.service.IdInfoStructService;
import com.inteagle.common.enumList.DeviceActionEnum;
import com.inteagle.common.enumList.DeviceTypeEnum;
import com.inteagle.common.mqtt.service.IMqttPublish;
import com.inteagle.common.mqtt.service.wrapper.impl.IMqttWrapperServiceImpl;
import com.inteagle.common.redis.ListCacheUtil;
import com.inteagle.common.redis.RedisCacheUtil;
import com.inteagle.common.websocket.server.WebSocketServer;

import lombok.extern.slf4j.Slf4j;
import struct.JavaStruct;
import struct.StructException;

/**
 * @author IVAn
 * @ClassName: AnalysisUtil
 * @Description: TODO(按照协议 解析工具类)
 * @date 2019年7月2日下午4:12:41
 */
@Slf4j
@Component
public class AnalysisUtil {

	private static AnalysisUtil analysisUtil;

	@Autowired
	private IdInfoStructService idInfoStructService;

	@Autowired
	private IMqttWrapperServiceImpl iMqttWrapperServiceImpl;

	// 项目启动时 注入到Spring容器
	@PostConstruct
	public void init() {
		analysisUtil = this;
		analysisUtil.idInfoStructService = this.idInfoStructService;
		analysisUtil.iMqttWrapperServiceImpl = this.iMqttWrapperServiceImpl;
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
		CMD.put(7, "CMD_COMMON_DEVICE_ACTION");

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

		// 安全帽信息
		CMD.put(307, "CMD_CAMERA_ID_INFO");

		// 安全帽 人脸识别
		CMD.put(308, "CMD_CAMERA_PERSON_ID");

		// 基坑设备命令 251-299
		CMD.put(251, "CMD_FRAME_SET_THRESHOLD");
		CMD.put(300, "CMD_FRAME_RESERVED");

	}

	// 解析 paylaod 数据
	// byte[] playload
	public static void validate(String hexStr, String topic) {
		System.out.println("hexStr-----" + hexStr);

		// 发送socket消息
		sendSocketData(hexStr, "hexStr");

		// 发送socket消息
		try {
			WebSocketServer.sendInfo("123123", "ivan");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

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
				// System.out.println("长度len_hex------" + len_hex);

				// 数据长度 10进制
				// long len_num = Long.parseLong(len_hex, 16);
				// System.out.println("长度len_num------" + len_num);

				// CMD 16进制
				String cmd_hex = hexStr.substring(8, 12);
				System.out.println("cmd_hex-----" + cmd_hex);

				// CMD 10进制
				long cmd_ten = Long.parseLong(cmd_hex, 16) & 0x0fff;
				System.out.println("cmd_ten-----" + cmd_ten);

				// 发送socket消息
				sendSocketData(cmd_ten, "cmd_ten");

				// data+crc+eof
				String data_crc_eof = hexStr.substring(12);

				// CRC 16进制
				String crc_hex = data_crc_eof.substring((data_crc_eof.length() - 6), (data_crc_eof.length() - 4));

				System.out.println("crc_hex-------" + crc_hex);

				// CRC 10进制
				long crc_ten = Long.parseLong(crc_hex, 16);

				System.out.println("crc_ten-------" + crc_ten);

				// CRC 检验数据
				String crc_examine = hexStr.substring(4, (hexStr.length() - 6));

				// CRC 校验数据的字节数组
				byte[] b = ByteHexUtil.hexStr2Bytes(crc_examine);

				// CRC 校验
				byte crc = CRC8.calcCrc8(b);

				System.out.println("crc--校验值-----" + crc);

				// crc_ten == crc
				// crc_hex.equals((Integer.toHexString(0x00ff & crc)).toString())
				// 判断CRC 10进制 和校验值
				if (crc_hex.equals((Integer.toHexString(0x00ff & crc)).toString())) {
					log.info("crc校验成功....");

					// 数据体
					String data_str = data_crc_eof.substring(0, (data_crc_eof.length() - 6));

					System.out.println("data_str-----" + data_str);

					// JavaStruct解析数据、保存到数据库
					getDataJson(data_str, cmd_ten, topic);

				} else {
					log.error("CRC 校验失败.....");
				}

			} else {
				log.error("sof eof验证失败...");
			}

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());

			// TODO Auto-generated catch block
			log.info("解析数据失败...");
		}

	}

	/**
	 * @param data
	 * @description 解析数据体
	 * @author IVAn
	 * @date 2019年7月3日 上午10:52:49
	 */
	public static void getDataJson(String data, long cmd, String topic) {

		// 命令值
		int cmd_num = (int) cmd;
		System.out.println("cmd-num--------" + cmd_num);

		// 找到对应的命令
		String cmd_value = CMD.get(cmd_num);

		System.out.println("cmd_value---------------------" + cmd_value);

		// 根据不同命令 做不同处理
		switch (cmd_value) {
		// 时间同步
		case "CMD_COMMON_TIME_SYNC":
			try {
				// 16进制的data转成字节数组
				byte[] b = ByteHexUtil.hexStr2Bytes(data);
				TimeSyncData timeSyncData = new TimeSyncData();
				// 解析成javaStruct
				JavaStruct.unpack(timeSyncData, b, ByteOrder.BIG_ENDIAN);
				System.out.println("getT_h-------" + timeSyncData.getT_h());
				System.out.println("getT_l-------" + timeSyncData.getT_l());

				// 发送socket消息
				sendSocketData(timeSyncData, "timeSyncData");

			} catch (StructException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		// 解析安全帽数据
		case "CMD_CAMERA_ID_INFO":
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
				System.out.println("camera_id-----" + struct.camera_id);

				try {

					// 取出 Redis中的人脸识别数据
					List<Object> list = ListCacheUtil.listFindAll("camera_person_id_list");
					if (list != null && list.size() != 0) {
						for (int i = 0; i < list.size(); i++) {
							System.out.println("list.get(i)------" + list.get(i));
						}
					}

					// 保存到数据库
//						  int result = analysisUtil.idInfoStructService.insert(struct);
//						  if (result > 0) { log.info("数据保存成功..."); }

					// 发送socket消息
					WebSocketServer.sendInfo(JSONObject.toJSONString(struct), "ivan");

					// 发送socket消息
					sendSocketData(struct, "IdInfoStruct");

				} catch (Exception e) {
					log.error(e.toString());
				}

			} catch (StructException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		// 人脸识别数据
		case "CMD_CAMERA_PERSON_ID":
			// 方法2 JavaStruct解析
			try {
				HelmetDiscernStruct struct = new HelmetDiscernStruct();
				byte[] b = ByteHexUtil.hexStr2Bytes(data);

				// 解析成javaStruct
				JavaStruct.unpack(struct, b, ByteOrder.BIG_ENDIAN);
				System.out.println("id----------" + struct.getId());

				// 将指定的值插入列表尾部并返回长度
				ListCacheUtil.rightPush("camera_person_id_list", struct.getId());

				// 发送socket消息
				sendSocketData(struct, "HelmetDiscernStruct");

			} catch (StructException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		// 设备行为数据
		case "CMD_COMMON_DEVICE_ACTION":
			// 方法2 JavaStruct解析
			try {
				DeviceActionStruct struct = new DeviceActionStruct();
				byte[] b = ByteHexUtil.hexStr2Bytes(data);

				// 解析成javaStruct
				JavaStruct.unpack(struct, b, ByteOrder.BIG_ENDIAN);
				System.out.println("action----------" + struct.getAction());
				System.out.println("device_type----------" + struct.getDevice_type());
				System.out.println("priority----------" + struct.getPriority());

				// 发送socket消息
				sendSocketData(struct, "DeviceActionStruct");

				byte action = struct.getAction();
				byte device_type = struct.getDevice_type();

				if (action == DeviceActionEnum.ONLINE.getValue()) {
					System.out.println("-----设备上线-----");
					if (device_type == DeviceTypeEnum.DEVICE_TYPE_CAMERA_MASTER.getValue()) {
						System.out.println("上线设备为---DEVICE_TYPE_CAMERA_MASTER----");

						topic = topic.replace("up", "down");
						System.out.println("topic-------------" + topic);

						// 发送时间同步的消息到对应主题的设备
						analysisUtil.iMqttWrapperServiceImpl.publish(topic, SendDataUtil.sendTimeSyncData());
					}
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		default:
			break;
		}

	}

//  之前是根据 data长度来做对应的处理
//	System.out.println("data--length------" + data.length());
//	if (data.length() > 0) {
//		if (data.length() == 44) {
//		} else if (data.length() == 40) {
//
//			// 方法2 JavaStruct解析
//			try {
//				IdInfoStruct struct = new IdInfoStruct();
//				byte[] b = ByteHexUtil.hexStr2Bytes(data);
//
//				// 解析成javaStruct
//				JavaStruct.unpack(struct, b, ByteOrder.BIG_ENDIAN);
//
//				System.out.println("id----" + struct.id);
//				System.out.println("x-----" + struct.x);
//				System.out.println("y-----" + struct.y);
//				System.out.println("t-----" + struct.t);
//				System.out.println("camera_id-----" + struct.camera_id);
//				try {
//					// 保存到数据库
////					  int result = analysisUtil.idInfoStructService.insert(struct);
////					  if (result > 0) { log.info("数据保存成功..."); }
//
//					// 发送socket消息
//					WebSocketServer.sendInfo(JSONObject.toJSONString(struct), "ivan");
//
//				} catch (Exception e) {
//					log.error(e.toString());
//				}
//
//			} catch (StructException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//			/*
//			 * //方法1 分段截取 按照协议转进制解析 // 16进制 转 10进制 int id =
//			 * ByteHexUtil.hexStringToAlgorism(data.substring(0, 4));
//			 * System.out.println("id-----" + id); // hex 转成 float Long x_long =
//			 * parseLong(data.substring(4, 12), 16); float x_rate =
//			 * Float.intBitsToFloat(x_long.intValue()); System.out.println("x_rate------" +
//			 * x_rate); Long y_long = parseLong(data.substring(12, 20), 16); float y_rate =
//			 * Float.intBitsToFloat(y_long.intValue()); System.out.println("y_rate-----" +
//			 * y_rate); int t_rate = ByteHexUtil.hexStringToAlgorism(data.substring(20,
//			 * 36)); System.out.println("t_rate-----" + t_rate);
//			 */
//		} else if (data.length() == 16) {
//			try {
//				TimeSyncData timeSyncData = new TimeSyncData();
//
//				byte[] b = ByteHexUtil.hexStr2Bytes(data);
//
//				// 解析成javaStruct
//				JavaStruct.unpack(timeSyncData, b, ByteOrder.BIG_ENDIAN);
//
//				System.out.println("getT_h-------" + timeSyncData.getT_h());
//				System.out.println("getT_l-------" + timeSyncData.getT_l());
//
//			} catch (StructException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//		}
//
//	}

	// 发送socket数据
	public static void sendSocketData(Object dataStr, String dataType) {
		try {
			System.out.println("发送socket消息...");
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("data", dataStr);
			jsonObject.put("dataType", dataType);
			jsonObject.put("messageType", "mqtt");
			WebSocketServer.sendInfo(JSONObject.toJSONString(jsonObject), "showWindow");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			log.error("socket发送mqtt数据失败...");
		}
	}

	public static void main(String[] args) {
		// a5a5000820060000000100000001065a5a
		// a5a500122133009542db000042f700000000000000000040115a5a

		try {
			validate("a5a500032007000000275a5a", "6lbr-up");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Long 转成 byte数组
	 *
	 * @param
	 * @return
	 */
	public static byte[] long2Bytes(long num) {
		byte[] byteNum = new byte[8];
		for (int ix = 0; ix < 8; ++ix) {
			int offset = 64 - (ix + 1) * 8;
			byteNum[ix] = (byte) ((num >> offset) & 0xff);
		}
		return byteNum;
	}

	/**
	 * byte数组转成long
	 *
	 * @param byteNum
	 * @return
	 */
	public static long bytes2Long(byte[] byteNum) {
		long num = 0;
		for (int ix = 0; ix < 8; ++ix) {
			num <<= 8;
			num |= (byteNum[ix] & 0xff);
		}
		return num;
	}

	/**
	 * @param s
	 * @param radix
	 * @return
	 * @throws NumberFormatException
	 * @description 16进制转成10进制
	 * @author IVAn
	 * @date 2019年7月9日 下午2:45:12
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
