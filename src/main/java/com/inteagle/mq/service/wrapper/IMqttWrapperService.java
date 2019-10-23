package com.inteagle.mq.service.wrapper;

public interface IMqttWrapperService {
	/**
	 * 发布消息
	 *
	 * @param topic
	 * @param content
	 * @return
	 */
	Boolean publish(String topic, String content,String type);

	/**
	 * 订阅消息
	 *
	 * @param topic
	 * @return
	 */
	Boolean subscribe(String topic);
}
