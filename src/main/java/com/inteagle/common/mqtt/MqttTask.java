package com.inteagle.common.mqtt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.inteagle.common.mqtt.service.IMqttPublish;
import com.inteagle.common.struct.ByteHexUtil;
import com.inteagle.common.websocket.server.WebSocketServer;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MqttTask {

	@Autowired
	private IMqttPublish iEmqService;

//	@Scheduled(cron = "0/5 * * * * ? ")
//	public void execute() {
//		String topic = "6lbr-down/61948/27384";
//
//		String sof = "a5a5";
//		sof = ByteHexUtil.changeType(sof);
//
//		String len = "0008";
//		len = ByteHexUtil.changeType(len);
//
//		String cmd = "2006";
//		cmd = ByteHexUtil.changeType(cmd);
//
//		String data = "000003e8000007d0";
//		data = ByteHexUtil.changeType(data);
//
//		String crc = "11";
//		crc = ByteHexUtil.changeType(crc);
//
//		String eof = "5a5a";
//		eof = ByteHexUtil.changeType(eof);
//
//		String content = sof + len + cmd + data + crc + eof;
//
//		try {
//			iEmqService.publish(topic, content);
//			log.info("发布消息");
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//
//	}

//	@Scheduled(cron = "*/1 * * * * ?")
//	public void execute() {
//		String topic = "6lbr-server";
//		String content="a5a500122133009542db000042f700000000000000000040115a5a";
//
//		for (int i = 0; i < 10; i++) {
//			JSONObject object = new JSONObject();
//			object.put("x", (5+i*5));
//			object.put("y", (10 + i * 5));
//			object.put("t", (0));
//			
//			try {
//				//socket发送位置消息
//				WebSocketServer.sendInfo(object.toJSONString(), "ivan");
////				iEmqService.publish(topic, content);
//				log.info("发布消息");
//			} catch (Exception e) {
//				// TODO: handle exception
//			}
//		}
//
//	}

}
