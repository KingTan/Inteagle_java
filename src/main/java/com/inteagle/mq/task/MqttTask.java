package com.inteagle.mq.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.inteagle.config.MqttConfiguration;
import com.inteagle.mq.service.IMqttPublish;
import com.inteagle.utils.struct.SendDataUtil;

@Component
public class MqttTask {

	@Autowired
	private IMqttPublish iEmqService;

//	@Scheduled(cron = "0/1 * * * * ? ")
//	public void execute() {
//
//		for (int i = 0; i < 20; i++) {
//			iEmqService.publish("6lbr-down/61948/61948", SendDataUtil.sendTimeSyncData(), MqttConfiguration.HELMET);
//			try {
//				Thread.sleep(40);
//			} catch (Exception e) {
//			}
//		}
//
//	}

}
