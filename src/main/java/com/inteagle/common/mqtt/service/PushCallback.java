package com.inteagle.common.mqtt.service;

import com.inteagle.common.mqtt.utils.queue.QueueHandleUtil;
import com.inteagle.common.struct.AnalysisUtil;
import com.inteagle.common.struct.ByteHexUtil;
import com.inteagle.common.util.ParamUtil;

import lombok.extern.slf4j.Slf4j;
import struct.*;

import java.io.UnsupportedEncodingException;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PushCallback implements MqttCallback {

	/**
	 * 消息订阅者配置类
	 */
	@Autowired
	private SubscribeConn subscribeConn;

	@Autowired
	private QueueHandleUtil queueHandleUtil;

	@Override
	public void connectionLost(Throwable cause) {
		log.info("连接断开，进行重连");
		subscribeConn.getConn();
	}

	@Override
	public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
		log.info("接收消息主题:{},接收消息Qos:{},接收消息内容:{}", topic, mqttMessage.getQos(), new String(mqttMessage.getPayload()));
		String content = new String(mqttMessage.getPayload());
		log.info("MQ消费者接收消息:" + content);

		// 分别不同主题、解析数据
		if (topic.indexOf("inclinometer") != -1) {

			// 解析数据(圣一 -基坑数据)
			System.out.println("content------" + content);
//			analyseData(content);

		} else if (topic.indexOf("6lbr-server") != -1 || topic.indexOf("6lbr-up") != -1) {

			// 解析数据(小慧-安全帽数据)
//			AnalysisUtil.validate(mqttMessage.getPayload());
			
			String hexStr_2 = ByteHexUtil.bytes2HexStr(mqttMessage.getPayload()); // 编码
			System.out.println("hexStr_2-----"+hexStr_2);
			
			AnalysisUtil.validate(hexStr_2);
		}

		// 数据存到队列
//		Thread.sleep(5000);
//		queueHandleUtil.advanceDisruptor(content);
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
		log.info("deliveryComplete:{}", iMqttDeliveryToken.isComplete());
	}

	/**
	 * @description 解析圣一发送的基坑相关数据字符串
	 * @author IVAn
	 * @date 2019年7月25日 下午5:10:51
	 * @param dataStr
	 */
	public static void analyseData(String dataStr) {

		if (ParamUtil.notNullForParam(dataStr)) {
			// 设备编号
			String id = dataStr.substring(0, 8);
			// 接受数据时间
			String min = dataStr.substring(8, 10);
			String hour = dataStr.substring(10, 12);
			String day = dataStr.substring(12, 14);
			String month = dataStr.substring(14, 16);
			String year = dataStr.substring(16, 20);
			// 接收数据组数
			String num_str = dataStr.substring(20, 23);
			int num = Integer.parseInt(num_str);

			log.info("id---------{}", id);
			log.info("min---------{}", min);
			log.info("hour---------{}", hour);
			log.info("day---------{}", day);
			log.info("month---------{}", month);
			log.info("year---------{}", year);
			log.info("num---------{}", num_str);
			System.out.println("changNum-------" + Integer.parseInt(num_str));

			for (int i = 0; i < num; i++) {
				System.out
						.println("p" + (i + 1) + "_x(7)-----" + dataStr.substring(23 + i * 7 * 3, 23 + i * 7 * 3 + 7));
				System.out.println(
						"p" + (i + 1) + "_y(7)-----" + dataStr.substring(23 + i * 7 * 3 + 7, 23 + i * 7 * 3 + 14));
				System.out.println(
						"p" + (i + 1) + "_z(7)-----" + dataStr.substring(23 + i * 7 * 3 + 14, 23 + i * 7 * 3 + 21));
				System.out.println("--------------------");
			}
		}
	}

	public static void main(String[] args) {
		String data_str = "00000001020304050006003001.000002.000003.000004.000005.000006.000007.000008.000009.000";
		analyseData(data_str);
	}

}
