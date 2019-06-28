package com.inteagle.common.mqtt.service;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * 
 * @ClassName: PushCallback
 * @Description: TODO(mqtt推送回调类)
 * @author IVAn
 * @date 2019年6月28日下午2:57:41
 *
 */
public class PushCallback implements MqttCallback {

	@Override
	public void connectionLost(Throwable throwable) {
		/**
		 * 连接丢失后，一般在这里面进行重连
		 */
		System.out.println("连接断开，可以做重连");
	}

	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		// subscribe后得到的消息会执行到这里面
		System.out.println("Client 接收消息主题 : " + topic);
		System.out.println("Client 接收消息Qos : " + message.getQos());
		System.out.println("Client 接收消息内容 : " + new String(message.getPayload()));
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
		System.out.println("deliveryComplete---------" + token.isComplete());
	}

}
