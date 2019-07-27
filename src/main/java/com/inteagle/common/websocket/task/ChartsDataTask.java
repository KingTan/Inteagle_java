package com.inteagle.common.websocket.task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.inteagle.common.util.DateUtil;
import com.inteagle.common.util.UserUtil;
import com.inteagle.common.websocket.server.ChartsSocketServer;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @ClassName: ChartsSocketServer
 * @Description: TODO(定时socket发送数据到前端基坑图)
 * @author IVAn
 * @date 2019年7月13日下午2:23:54
 *
 */
@Component
@Slf4j
public class ChartsDataTask {

//	@Scheduled(cron = "*/5 * * * * ?")
//	public void execute() {
//		DataSource dataSource = new DataSource();
//		JSONObject obejct = new JSONObject();
//		int num = (int) (Math.random() * 300);
//		dataSource.setRecordTime(DateUtil.getDateTimeStr());
//		dataSource.setNumber(100 + num);
//
//		obejct.put("data", dataSource);
//		obejct.put("senderType", 1);
//
//		try {
//			ChartsSocketServer.sendInfo(obejct.toJSONString(), null);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			log.error("发送socket消息失败");
//		}
//
//	}

	class DataSource {

		String recordTime;

		Integer number;

		public String getRecordTime() {
			return recordTime;
		}

		public void setRecordTime(String recordTime) {
			this.recordTime = recordTime;
		}

		public Integer getNumber() {
			return number;
		}

		public void setNumber(Integer number) {
			this.number = number;
		}

	}

}
