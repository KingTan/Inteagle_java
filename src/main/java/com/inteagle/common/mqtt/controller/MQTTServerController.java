package com.inteagle.common.mqtt.controller;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.inteagle.common.mqtt.service.IMqttPublish;
import com.inteagle.common.struct.ByteHexUtil;
import com.inteagle.common.struct.CRC8;
import com.inteagle.common.struct.SendDataUtil;
import com.inteagle.common.struct.TimeSyncData;

import struct.JavaStruct;
import struct.StructException;

@RestController
public class MQTTServerController {
	@Autowired
	private IMqttPublish iEmqService;

	@RequestMapping("/")
	public String sayHello() {
		return "Hello !";
	}

	@RequestMapping("/send/msg")
	public boolean send(@RequestParam("msg") String msg, @RequestParam("topic") String topic)
			throws MqttException, StructException {

//		System.out.println("发布消息");
//		System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName() + "==========:" + msg);

		String sof = "a5a5";
		sof = ByteHexUtil.changeType(sof);

		String len = "0008";
		len = ByteHexUtil.changeType(len);

		String cmd = "2006";
		cmd = ByteHexUtil.changeType(cmd);

		// 初始时间结构体对象
		TimeSyncData timeSyncData = new TimeSyncData();

		long curretnTimeStamp = Calendar.getInstance().getTimeInMillis();

		System.out.println("curretnTimeStamp-------" + curretnTimeStamp);

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
		// System.out.println("data------"+data);

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

		msg = sof + len + cmd + data + crc + eof;
		System.out.println("msg-----" + msg);

		return iEmqService.publish(topic, msg);
	}

}
