package com.inteagle.controller.mq;

import java.util.Calendar;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.inteagle.config.MqttConfiguration;
import com.inteagle.modal.base.JsonResult;
import com.inteagle.modal.struct.HelmetSensorDataStruct;
import com.inteagle.mq.service.IMqttPublish;
import com.inteagle.utils.struct.ByteHexUtil;
import com.inteagle.utils.struct.CRC8;
import com.inteagle.utils.struct.MotorStartStruct;
import com.inteagle.utils.struct.SendDataUtil;
import com.inteagle.utils.struct.TimeSyncData;

import lombok.extern.slf4j.Slf4j;
import struct.JavaStruct;
import struct.StructException;

@RestController
@Slf4j
public class MQTTServerController {
	@Autowired
	private IMqttPublish iEmqService;

	@RequestMapping("/")
	public String sayHello() {
		return "Hello !";
	}

	@RequestMapping("/send/helmet")
	public boolean sendHelmetData() {
		String msg = SendDataUtil.sendHelmetPositionData();
		String topic = "test";
		return iEmqService.publish(topic, msg, MqttConfiguration.HELMET);
	}

	/**
	 * @description 发送电量mqtt数据
	 * @author IVAn
	 * @date 2019年8月9日 上午10:27:06
	 * @return
	 */
	@RequestMapping("/send/HelmetSensorData")
	public boolean sendHelmetSensorData(String topic) {

		System.out.println("发送电量mqtt数据 ...");

		String sof = "a5a5";
		sof = ByteHexUtil.changeType(sof);

		// CMD 16进制
		// Integer.toHexString(0712)
		String cmd = "025c";
		cmd = ByteHexUtil.changeType(cmd);

		String msg = "";

		try {
			HelmetSensorDataStruct helmetSensorDataStruct = new HelmetSensorDataStruct();
			short id = 100;
			helmetSensorDataStruct.setId(id);
			helmetSensorDataStruct.setVol(11);
			helmetSensorDataStruct.setTemp(22);
			helmetSensorDataStruct.setHelmet_on(0);

			// 转成字节数组
			byte[] start_byte = JavaStruct.pack(helmetSensorDataStruct);
			String data = ByteHexUtil.bytes2HexStr(start_byte);

			// 10进制转成16进制
			String len_hex = ByteHexUtil.intToHex(data.length());
			System.out.println("len_hex----" + len_hex);

			String len = ByteHexUtil.addZeroForNum(len_hex, 4);
			System.out.println("len----" + len);

			len = ByteHexUtil.changeType(len);
			// 按照协议 截取到crc的16进制值
			String crc = len + cmd + data;
			// System.out.println("crc-------" + crc);

			// 16进制转成字节数组
			byte[] crc_byte = ByteHexUtil.hex2Byte(crc);

			// 传入字节数组 求出crc的校验值字节
			byte crc_after = CRC8.calcCrc8(crc_byte);

			// 将校验值字节放入数组中 转成16进制数据
			byte[] crc_after_array = { crc_after };
			crc = ByteHexUtil.byte2HexStr(crc_after_array);

			String eof = "5a5a";
			eof = ByteHexUtil.changeType(eof);

			msg = sof + len + cmd + data + crc + eof;
			System.out.println("msg-----" + msg);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return iEmqService.publish(topic, msg, MqttConfiguration.HELMET);

	}

	/**
	 * @description 发送mqtt电机启动数据
	 * @author IVAn
	 * @date 2019年8月4日 下午7:49:59
	 * @return
	 */
	@RequestMapping("/send/start")
	public boolean sendStartSign(String topic) {

		System.err.println("发送电机启动消息...");

		String sof = "a5a5";
		sof = ByteHexUtil.changeType(sof);

		// CMD 16进制
		// Integer.toHexString(0712)
		String cmd = "02be";
		cmd = ByteHexUtil.changeType(cmd);

		String msg = "";

		try {
			MotorStartStruct motorStartStruct = new MotorStartStruct();
			short fre = 1;
			motorStartStruct.setFre(fre);
			short duty = 1;
			motorStartStruct.setDuty(duty);
			short steps = 3;
			motorStartStruct.setSteps(steps);
			short step_hold = 3;
			motorStartStruct.setSteps_hold(step_hold);
			short hold_time = 3;
			motorStartStruct.setHold_time(hold_time);
			short dir = 1;
			motorStartStruct.setDir(dir);

			// 转成字节数组
			byte[] start_byte = JavaStruct.pack(motorStartStruct);
			String data = ByteHexUtil.bytes2HexStr(start_byte);

			// 10进制转成16进制
			String len_hex = ByteHexUtil.intToHex(data.length());
			System.out.println("len_hex----" + len_hex);

			String len = ByteHexUtil.addZeroForNum(len_hex, 4);
			System.out.println("len----" + len);

			len = ByteHexUtil.changeType(len);
			// 按照协议 截取到crc的16进制值
			String crc = len + cmd + data;
			// System.out.println("crc-------" + crc);

			// 16进制转成字节数组
			byte[] crc_byte = ByteHexUtil.hex2Byte(crc);

			// 传入字节数组 求出crc的校验值字节
			byte crc_after = CRC8.calcCrc8(crc_byte);

			// 将校验值字节放入数组中 转成16进制数据
			byte[] crc_after_array = { crc_after };
			crc = ByteHexUtil.byte2HexStr(crc_after_array);

			String eof = "5a5a";
			eof = ByteHexUtil.changeType(eof);

			msg = sof + len + cmd + data + crc + eof;
			System.out.println("msg-----" + msg);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return iEmqService.publish(topic, msg, MqttConfiguration.HELMET);
	}

	/**
	 * @description 发送mqtt电机停止数据
	 * @author IVAn
	 * @date 2019年8月4日 下午7:49:59
	 * @return
	 */
	@RequestMapping("/send/stop")
	public boolean sendStopSign(String topic) {
		System.err.println("发送电机停止消息...");

		String sof = "a5a5";
		sof = ByteHexUtil.changeType(sof);

		// CMD 16进制
		String cmd = "02bf";

		String msg = "";

		try {
			String len = "0000";
			len = ByteHexUtil.changeType(len);

			String data = "";
			// 按照协议 截取到crc的16进制值
			String crc = len + cmd + data;
			// System.out.println("crc-------" + crc);
			// 16进制转成字节数组
			byte[] crc_byte = ByteHexUtil.hex2Byte(crc);

			// 传入字节数组 求出crc的校验值字节
			byte crc_after = CRC8.calcCrc8(crc_byte);

			// 将校验值字节放入数组中 转成16进制数据
			byte[] crc_after_array = { crc_after };
			crc = ByteHexUtil.byte2HexStr(crc_after_array);

			String eof = "5a5a";
			eof = ByteHexUtil.changeType(eof);

			msg = sof + len + cmd + data + crc + eof;
			System.out.println("msg-----" + msg);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return iEmqService.publish(topic, msg, MqttConfiguration.HELMET);
	}

	// 电机时间同步消息
	@RequestMapping("/send/timeSync")
	public JsonResult<Object> sendTimeSync() {
		String sof = "a5a5";
		String len = "000a";
		String cmd = "02C0";

//		Long content = Calendar.getInstance().getTimeInMillis() / 1000;
		
		Long content = 1571818193l;
		byte[] test = ByteHexUtil.longToDword(content);

		String test_hex = ByteHexUtil.bytes2HexStr(test);

		// 转成字节数组
//		String data = Long.toString(content);
//		data = ByteHexUtil.str2HexStr(data);

		String data = test_hex;

		log.info("data----" + data);

		// 按照协议 截取到crc的16进制值
		String crc = len + cmd + data;

		// 16进制转成字节数组
		byte[] crc_byte = ByteHexUtil.hex2Byte(crc);

		// 传入字节数组 求出crc的校验值字节
		byte crc_after = CRC8.calcCrc8(crc_byte);

		// 将校验值字节放入数组中 转成16进制数据
		byte[] crc_after_array = { crc_after };

		crc = ByteHexUtil.byte2HexStr(crc_after_array);

		String eof = "5a5a";

		eof = ByteHexUtil.changeType(eof);

		String msg = sof + len + cmd + data + crc + eof;

		log.info("msg-----" + msg);

		String topic = "6lbr-down/61948/26773";

		try {
			boolean is_send = iEmqService.publish(topic, msg, MqttConfiguration.HELMET);
			if (is_send) {
				return new JsonResult<>("发送成功");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new JsonResult<>(JsonResult.ERROR, "发送失败");
	}

	// 发送时间同步的消息(安全帽)
	@RequestMapping("/send/msg")
	public boolean send(@RequestParam("topic") String topic) throws MqttException, StructException {

		String sof = "a5a5";
		sof = ByteHexUtil.changeType(sof);

		String len = "0008";
		len = ByteHexUtil.changeType(len);

		String cmd = "2006";
		cmd = ByteHexUtil.changeType(cmd);

		// CMD 10进制
		long cmd_ten = Long.parseLong(cmd, 16) & 0x0fff;

		// 初始时间结构体对象
		TimeSyncData timeSyncData = new TimeSyncData();

		long curretnTimeStamp = Calendar.getInstance().getTimeInMillis();

		int t_l = (int) curretnTimeStamp;
		int t_h = (int) (curretnTimeStamp >> 32);

		System.out.println("t_h-------" + t_h);
		System.out.println("t_l-------" + t_l);

		Long change_val = SendDataUtil.getLongFromInt(t_l, t_h);
		System.out.println("change_val-------" + change_val);

//		int t_h = Integer.parseInt(curretnTimeStamp.toString().substring(0, 7));
//		int t_l = Integer.parseInt(curretnTimeStamp.toString().substring(7, curretnTimeStamp.toString().length()));

		timeSyncData.setT_h(t_h);
		timeSyncData.setT_l(t_l);

		// 转成字节数组
		byte[] time_byte = JavaStruct.pack(timeSyncData);
		String data = ByteHexUtil.bytes2HexStr(time_byte);
		System.out.println("data------" + data);

		// 按照协议 截取到crc的16进制值
		String crc = len + cmd + data;
		// System.out.println("crc-------" + crc);

		// 16进制转成字节数组
		byte[] crc_byte = ByteHexUtil.hex2Byte(crc);

		// 传入字节数组 求出crc的校验值字节
		byte crc_after = CRC8.calcCrc8(crc_byte);
		// System.out.println("crc_after-校验值-----" + crc_after);

		// 将校验值字节放入数组中 转成16进制数据
		byte[] crc_after_array = { crc_after };
		crc = ByteHexUtil.byte2HexStr(crc_after_array);
		// System.out.println("crc_last------" + crc);

		String eof = "5a5a";
		eof = ByteHexUtil.changeType(eof);

		String msg = sof + len + cmd + data + crc + eof;
		System.out.println("msg-----" + msg);

		return iEmqService.publish(topic, msg, MqttConfiguration.HELMET);
	}

}
