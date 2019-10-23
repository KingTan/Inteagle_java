package com.inteagle.utils.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * 
* @ClassName: ServiceInfoUtil
* @Description: TODO(获取本项目端口号)
* @author IVAn
* @date 2019年6月29日上午11:14:57
*
 */
@Slf4j
@Component
public class ServiceInfoUtil implements ApplicationListener<WebServerInitializedEvent> {

	private int serverPort;

	@Override
	public void onApplicationEvent(WebServerInitializedEvent event) {
		this.serverPort = event.getWebServer().getPort();
	}

	public int getPort() {
		return this.serverPort;
	}

	/*
	 * private static EmbeddedServletContainerInitializedEvent event;
	 * 
	 * @Override public void
	 * onApplicationEvent(EmbeddedServletContainerInitializedEvent event) {
	 * ServiceInfoUtil.event = event; }
	 * 
	 * public int getPort() { int port =
	 * event.getEmbeddedServletContainer().getPort(); return port; }
	 */

}
