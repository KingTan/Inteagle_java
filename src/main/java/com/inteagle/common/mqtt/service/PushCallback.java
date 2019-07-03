package com.inteagle.common.mqtt.service;

import com.inteagle.common.mqtt.utils.queue.QueueHandleUtil;
import com.inteagle.common.struct.AnalysisUtil;
import com.inteagle.common.struct.ByteHexUtil;

import lombok.extern.slf4j.Slf4j;
import struct.*;

import java.io.UnsupportedEncodingException;

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

		// 解析数据
//		AnalysisUtil.validate(content);

		// 数据存到队列
//		Thread.sleep(5000);
//		queueHandleUtil.advanceDisruptor(content);
//        queueHandleUtil.advanceDisruptor2(content);
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
		log.info("deliveryComplete:{}", iMqttDeliveryToken.isComplete());
	}

}
