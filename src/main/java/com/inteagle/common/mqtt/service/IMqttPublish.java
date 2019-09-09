package com.inteagle.common.mqtt.service;

/**
 * 
 * @ClassName: IMqttPublish
 * @Description: TODO(发布)
 * @author IVAn
 * @date 2019年6月29日下午2:34:54
 *
 */
public interface IMqttPublish {

	/**
	 * 发布消息
	 *
	 * @param topic
	 * @param content
	 * @return
	 */
	Boolean publish(String topic, String content,String type);

}
