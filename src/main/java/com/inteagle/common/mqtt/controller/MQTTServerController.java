package com.inteagle.common.mqtt.controller;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.inteagle.common.mqtt.config.MqttConfiguration;
import com.inteagle.common.mqtt.service.IMqttPublish;

@RestController
public class MQTTServerController {
	@Autowired
	private IMqttPublish iEmqService;

	@Autowired
	private MqttConfiguration mqttConfiguration;

	String TOPIC = "6lbr-server";

	@RequestMapping("/")
	public String sayHello() {
		return "Hello !";
	}

	@RequestMapping("/send/msg")
	public boolean send(@RequestParam String msg) throws MqttException {

		System.err.println("发布消息");
		System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName() + "==========:" + msg);
		return iEmqService.publish(TOPIC, msg);
	}
}
