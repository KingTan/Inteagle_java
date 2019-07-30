package com.inteagle.common.mqtt.controller;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.inteagle.common.mqtt.service.IMqttPublish;
import com.inteagle.common.struct.ByteHexUtil;
import com.inteagle.common.struct.CRC8;
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
	public boolean send(@RequestParam("msg") String msg, @RequestParam("topic") String topic) throws MqttException {

//		System.out.println("发布消息");
//		System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName() + "==========:" + msg);

		// a5a5 0008 2006 000003e8000007d0 11 5a5a
		String sof = "a5a5";
		sof = ByteHexUtil.changeType(sof);

		String len = "0008";
		len = ByteHexUtil.changeType(len);

		String cmd = "2006";
		cmd = ByteHexUtil.changeType(cmd);

		String data = "000003e8000007d0";
		data = ByteHexUtil.changeType(data);
		
		String crc = "11";
		crc = ByteHexUtil.changeType(crc);

		String eof = "5a5a";
		eof = ByteHexUtil.changeType(eof);

		msg = sof + len + cmd + data + crc + eof;

//		String sof = "a5a5";
//		sof = ByteHexUtil.changeType(sof);
//
//		String len = "0008";
//		len = ByteHexUtil.changeType(len);
//
//		String cmd = "2006";
//		cmd = ByteHexUtil.changeType(cmd);
//
//		String data = "000003e8000007d0";
//		data = ByteHexUtil.changeType(data);
//
//		String crc = "";
//		crc = len + cmd + data;
//
//		byte[] crc_byte = ByteHexUtil.hex2Byte(crc);
//
//		byte crc_after = CRC8.calcCrc8(crc_byte);
//		System.out.println("crc_after------" + crc_after);
//
//		crc = ByteHexUtil.changeType(Byte.toString(crc_after));
//
//		// CRC 10进制
//		long crc_ten = Long.parseLong(crc, 16);
//		System.out.println("crc_ten------" + crc_ten);
//		
////		crc = Byte.toString(crc_after);
//		crc = Long.toString(crc_ten);
//		System.out.println("crc------" + crc);
//
//		String eof = "5a5a";
//		eof = ByteHexUtil.changeType(eof);
//
//		msg = sof + len + cmd + data + crc + eof;
//		System.out.println("msg-----" + msg);

		return iEmqService.publish(topic, msg);
	}
}
