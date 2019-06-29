package com.inteagle.common.mqtt.service;

/**
 * 
* @ClassName: IMqttSubscribe
* @Description: TODO(订阅)
* @author IVAn
* @date 2019年6月29日下午2:34:42
*
 */
public interface IMqttSubscribe {
	Boolean subscribe(String topic);
}
