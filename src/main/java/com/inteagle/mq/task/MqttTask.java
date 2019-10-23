package com.inteagle.mq.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.inteagle.mq.service.IMqttPublish;

@Component
public class MqttTask {

	@Autowired
	private IMqttPublish iEmqService;

//	@Scheduled(cron = "0/10 * * * * ? ")
//	public void execute() {
//
//		String sof = "a5a5";
//		sof = ByteHexUtil.changeType(sof);
//
//		String len = "000a";
//		len = ByteHexUtil.changeType(len);
//
//		String cmd = "2006";
//		cmd = ByteHexUtil.changeType(cmd);
//
//		Long content = Calendar.getInstance().getTimeInMillis() / 1000;
//
//		// 转成字节数组
//		String data = Long.toString(content);
//		System.out.println("data-----" + data);
//
//		data = ByteHexUtil.str2HexStr(data);
//
//		// 按照协议 截取到crc的16进制值
//		String crc = len + cmd + data;
//
//		// 16进制转成字节数组
//		byte[] crc_byte = ByteHexUtil.hex2Byte(crc);
//
//		// 传入字节数组 求出crc的校验值字节
//		byte crc_after = CRC8.calcCrc8(crc_byte);
//
//		// 将校验值字节放入数组中 转成16进制数据
//		byte[] crc_after_array = { crc_after };
//
//		crc = ByteHexUtil.byte2HexStr(crc_after_array);
//
//		String eof = "5a5a";
//
//		eof = ByteHexUtil.changeType(eof);
//
//		String msg = sof + len + cmd + data + crc + eof;
//
//		String topic = "6lbr-down/61948/28153";
//
//		try {
//			iEmqService.publish(topic, msg, MqttConfiguration.HELMET);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	}

}
