package com.inteagle.common.mqtt.init;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.inteagle.common.mqtt.config.MqttConfiguration;
import com.inteagle.common.mqtt.service.IMqttSubscribe;

@Slf4j
@Component
public class MyApplicationRunner implements ApplicationRunner {

	@Autowired
	private IMqttSubscribe iMqttSubscribe;

	@Autowired
	private MqttConfiguration mqttConfiguration;

	@Override
	public void run(ApplicationArguments applicationArguments) throws Exception {
		log.info(" MQTT Subscribe Server 开始...");
		// 订阅主题
		iMqttSubscribe.subscribe(mqttConfiguration.getTopic());
		// 订阅主题
		iMqttSubscribe.subscribe("6lbr-up/#");
		// 订阅主题
//		iMqttSubscribe.subscribe("6lbr-down/#");
	}
}
