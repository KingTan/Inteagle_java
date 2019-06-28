package com.inteagle.common.mqtt.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

/**
 * 
* @ClassName: MqttConfiguration
* @Description: TODO(Mqtt配置类)
* @author IVAn
* @date 2019年6月28日下午2:46:21
*
 */
@Component
@Getter
@Setter
@SuppressWarnings("unused")
@ConfigurationProperties(prefix = "com.mqtt")
public class MqttConfiguration {
	
	private String host;

	private String clientid;

	private String topic;

	private String username;

	private String password;

	private int timeout;

	private int keepalive;

}
