package com.inteagle.common.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class RedisTask {

	@Autowired
	private RedisService redisService;

	// 0 */1 * * * ?
	@Scheduled(cron = "0/30 * * * * ? ")
	public void execute() {
		// 每一分钟清空Redis里 人脸识别的数据
		redisService.set("personId", "");
//		log.info("清空redis里缓存的人脸识别数据...");
	}

}
