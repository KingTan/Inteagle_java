package com.inteagle.common.mqtt.service;

import com.inteagle.common.mqtt.utils.queue.QueueHandleUtil;
import com.inteagle.common.struct.ByteHexUtil;
import com.inteagle.common.struct.CallBackInstruBase;
import com.inteagle.common.struct.ExecuteStruct;
import com.inteagle.common.struct.ExecuteStruct.AString;

import lombok.extern.slf4j.Slf4j;
import struct.ArrayLengthMarker;
import struct.JavaStruct;
import struct.StructClass;
import struct.StructException;
import struct.StructField;

import java.nio.ByteOrder;
import java.util.Arrays;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PushCallback implements MqttCallback {

	/**
	 * 消息订阅者配置类
	 */
	@Autowired
	private SubscribeConn subscribeConn;

	@Autowired
	private QueueHandleUtil queueHandleUtil;

	@Override
	public void connectionLost(Throwable cause) {
		log.info("连接断开，进行重连");
		subscribeConn.getConn();
	}

	@Override
	public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
		log.info("接收消息主题:{},接收消息Qos:{},接收消息内容:{}", topic, mqttMessage.getQos(), new String(mqttMessage.getPayload()));
		String content = new String(mqttMessage.getPayload());
		log.info("MQ消费者接收消息:" + content);

		String hexStr = ByteHexUtil.bytes2HexStr(content.getBytes("utf8")); // 编码
		System.out.println("encode result : " + hexStr);

		String rawSource = new String(ByteHexUtil.hexStr2Bytes(hexStr), "utf8"); // 解码
		System.out.println("decode result : " + rawSource);

		validate(hexStr);

		// 数据存到队列
//		Thread.sleep(10000);
//		queueHandleUtil.advanceDisruptor(content);
//        queueHandleUtil.advanceDisruptor2(content);
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
		log.info("deliveryComplete:{}", iMqttDeliveryToken.isComplete());
	}

	public void validate(String codeStr) {
		String sof_hex = codeStr.substring(0, 4);
		System.out.println("sof_hex----"+sof_hex);
		
		String eof_hex = codeStr.substring(codeStr.length() - 4);
		System.out.println("eof_hex----"+eof_hex);
		
		String len_hex = codeStr.substring(4, 8);
		System.out.println("长度len_hex------" + len_hex);
		long len_num = Long.parseLong(len_hex, 16);
		System.out.println("长度len_num------" + len_num);
		
		String data_crc_eof=codeStr.substring(12);
		
		//数据体
		String data=data_crc_eof.substring(0,(data_crc_eof.length()-6));
		
		//crc16进制
		String crc_16hex=data_crc_eof.substring((data_crc_eof.length()-6),2);
		//crc检验数据
		
		

	}

	public static byte[] hexStringToByte(String hex) {
		int len = (hex.length() / 2);
		byte[] result = new byte[len];
		char[] achar = hex.toCharArray();
		for (int i = 0; i < len; i++) {
			int pos = i * 2;
			result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
		}
		return result;
	}

	private static byte toByte(char c) {
		byte b = (byte) "0123456789abcdef".indexOf(c);
		return b;
	}

}
