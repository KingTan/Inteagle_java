package com.inteagle.common.mqtt.config;

import javax.annotation.PostConstruct;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.stereotype.Component;

import com.inteagle.common.mqtt.service.ReceiveMessage;

/**
 * 
 * @ClassName: MqttTask
 * @Description: TODO(配置在项目启动时 连接mqtt服务器 监听订阅主题的消息)
 * @author IVAn
 * @date 2019年6月29日上午9:54:13
 *
 */
@Component
public class MqttTask {

	@PostConstruct
	public void execute() {
		try {
			ReceiveMessage.start();
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
