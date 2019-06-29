package com.inteagle.common.mqtt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inteagle.common.mqtt.service.IMqttSubscribe;
import com.inteagle.common.mqtt.service.wrapper.IMqttWrapperService;

@Service
public class MqttSubscribeImpl implements IMqttSubscribe {
	@Autowired
	private IMqttWrapperService iMqttWrapperService;

	@Override
	public Boolean subscribe(String topic) {
		return iMqttWrapperService.subscribe(topic);
	}
}
