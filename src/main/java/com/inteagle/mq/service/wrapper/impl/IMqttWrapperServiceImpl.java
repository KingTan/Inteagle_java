package com.inteagle.mq.service.wrapper.impl;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import com.inteagle.config.MqttConfiguration;
import com.inteagle.mq.service.SubscribeConn;
import com.inteagle.mq.service.wrapper.IMqttWrapperService;
import com.inteagle.utils.struct.ByteHexUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@EnableConfigurationProperties({ MqttConfiguration.class })
public class IMqttWrapperServiceImpl implements IMqttWrapperService {

	@Autowired
	private MqttConfiguration mqttConfiguration;

	@Autowired
	private SubscribeConn subscribeConn;

	@Override
	public Boolean publish(String topic, String content, String type) {

		log.info("MQ===public=== 入参:topic:{};content:{}", topic, content);

		byte[] rawSource = ByteHexUtil.hexStr2Bytes(content); // 解码
		System.out.println("发送decode result : " + rawSource);
		
		String hexStr_2 = ByteHexUtil.bytes2HexStr(rawSource); // 编码
		System.out.println("发送encode result : " + hexStr_2);
		
		//消息体对象
		MqttMessage message;
		if (type.equals(MqttConfiguration.HELMET)) {
			message = new MqttMessage(rawSource);
		} else {
			message = new MqttMessage(content.getBytes());
		}

		message.setQos(mqttConfiguration.getQos());
		/**
		 * Retained为true时MQ会保留最后一条发送的数据，当断开再次订阅即会接收到这最后一次的数据
		 */
		message.setRetained(true);
		try {
			MqttClient mqttClient = subscribeConn.getMqttClient();

			// 判定是否需要重新连接
			// 删除一个判断条件
			// || !mqttClient.getClientId().equals(mqttConfiguration.getSubscribeClientId())

			if (mqttClient == null || !mqttClient.isConnected()) {
//				System.err.println("重新连接------");
				mqttClient = subscribeConn.getConn();
			}
			mqttClient.publish(topic, message);
			log.info("emq已发topic: {} - message: {}", topic, message);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public Boolean subscribe(String topic) {
		log.info("MQ===subscribe=== 入参:topic:{}", topic);

		MqttClient mqttClient = subscribeConn.getMqttClient();
		// 判定是否需要重新连接
		if (mqttClient == null || !mqttClient.isConnected()
				|| !mqttClient.getClientId().equals(mqttConfiguration.getSubscribeClientId())) {
			System.err.println("重新连接------");
			mqttClient = subscribeConn.getConn();
		}
		try {
			// 订阅消息
			int[] qos = { mqttConfiguration.getQos() };
			mqttClient.subscribe(new String[] { topic }, qos);
		} catch (MqttException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
