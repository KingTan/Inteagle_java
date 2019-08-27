package com.inteagle.common.weather.task;


import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @ClassName: WeahterTask
 * @Description: TODO(天气定时器,每一小时发送天气socket数据到前端,但是需要前端发送当前的位置的经纬度)
 * @author IVAn
 * @date 2019年8月26日上午11:35:10
 *
 */
@Component
@Slf4j
public class WeahterTask {

	@Scheduled(cron = "0 0 0/1 * * ? ")
	public void execute() {
		
	}

}
