package com.inteagle.mq.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inteagle.mq.service.IMqttPublish;
import com.inteagle.mq.service.wrapper.IMqttWrapperService;

@Service
public class MqttPublishImpl implements IMqttPublish {

	@Autowired
	private IMqttWrapperService iMqttWrapperService;

	@Override
	public Boolean publish(String topic, String content, String type) {
		// TODO Auto-generated method stub
		return iMqttWrapperService.publish(topic, content, type);
	}

}
