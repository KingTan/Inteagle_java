package com.inteagle.common.redis;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.inteagle.common.mqtt.MqttTask;

import lombok.extern.slf4j.Slf4j;


@Component
@Slf4j
public class RedisTask {

	@Scheduled(cron = "0 */1 * * * ?")
	public void execute() {
		
		//每一分钟清空Redis里 人脸识别的数据
		
		
		
	}

}
