package com.inteagle.common.mqtt.init;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.inteagle.common.mqtt.service.IMqttSubscribe;

@Slf4j
@Component
public class MyApplicationRunner implements ApplicationRunner {
	@Autowired
	private IMqttSubscribe iMqttSubscribe;

	String TOPIC = "testtopic";

	@Override
	public void run(ApplicationArguments applicationArguments) throws Exception {
		log.info(" MQTT Subscribe Server 开始...");
		iMqttSubscribe.subscribe(TOPIC);
	}
}
