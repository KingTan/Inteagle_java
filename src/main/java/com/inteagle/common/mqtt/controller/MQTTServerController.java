package com.inteagle.common.mqtt.controller;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inteagle.common.mqtt.service.PublishMessage;
import com.inteagle.common.mqtt.service.ReceiveMessage;

@RestController
public class MQTTServerController {

	@RequestMapping("/")
	public String sayHello() {
		return "Hello !";
	}

	@RequestMapping("/send")
	public void send(String msg) throws MqttException {
		PublishMessage.start(msg);
	}

	@RequestMapping("/receive")
	public void receive() throws MqttException {
		ReceiveMessage.start();
	}

}
