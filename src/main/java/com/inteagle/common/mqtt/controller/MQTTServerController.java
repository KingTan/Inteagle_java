package com.inteagle.common.mqtt.controller;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.inteagle.common.mqtt.service.IMqttPublish;

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

		System.err.println("发布消息");
		System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName() + "==========:" + msg);
		return iEmqService.publish(topic, msg);
	}
}
