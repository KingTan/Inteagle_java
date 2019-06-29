package com.inteagle.common.mqtt.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component // 不加这个注解的话, 使用@Autowired 就不能注入进去了
@PropertySource("classpath:application.yml")
@ConfigurationProperties(prefix = "mqtt") // 配置文件中的前缀
public class MqttConfiguration {

	private String host;

	private String username;

	private String password;

	private String topic;

	private Integer qos;

	private String[] hosts;

	private Integer connectionTimeout;

	private Integer keepAliveInterval;

	private String publishClientId;

	private String subscribeClientId;

	private boolean retained;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getQos() {
		return qos;
	}

	public void setQos(Integer qos) {
		this.qos = qos;
	}

	public String[] getHosts() {
		return hosts;
	}

	public void setHosts(String[] hosts) {
		this.hosts = hosts;
	}

	public Integer getConnectionTimeout() {
		return connectionTimeout;
	}

	public void setConnectionTimeout(Integer connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}

	public Integer getKeepAliveInterval() {
		return keepAliveInterval;
	}

	public void setKeepAliveInterval(Integer keepAliveInterval) {
		this.keepAliveInterval = keepAliveInterval;
	}

	public String getPublishClientId() {
		return publishClientId;
	}

	public void setPublishClientId(String publishClientId) {
		this.publishClientId = publishClientId;
	}

	public String getSubscribeClientId() {
		return subscribeClientId;
	}

	public void setSubscribeClientId(String subscribeClientId) {
		this.subscribeClientId = subscribeClientId;
	}

	public boolean isRetained() {
		return retained;
	}

	public void setRetained(boolean retained) {
		this.retained = retained;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

}
