package com.inteagle.common.websocket.task;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

	@Scheduled(cron = "*/5 * * * * ?")
	public void execute() {
		ArrayList<Data_single> data_array_1 = new ArrayList<Data_single>();

		for (int i = 0; i < 16; i++) {
			Data_single data_single_1 = new Data_single();
			// 50以内的随机数
			data_single_1.setX((int) (Math.random() * 40) + 10);
			data_single_1.setY((int) (Math.random() * 40) + 10);
			data_single_1.setZ(0);
			data_array_1.add(data_single_1);
		}

		Calendar cal = Calendar.getInstance();
		Date date = cal.getTime();
		
		JSONObject jsonObject_data = new JSONObject();
		jsonObject_data.put(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(date), data_array_1);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("messageType", "foundation");
		jsonObject.put("data", jsonObject_data);

		try {
			ChartsSocketServer.sendInfo(jsonObject.toJSONString(), "001");
//			System.out.println("发送socket消息....");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	class Data_single {

		private int x;

		private int y;

		private int z;

		public int getX() {
			return x;
		}

		public void setX(int x) {
			this.x = x;
		}

		public int getY() {
			return y;
		}

		public void setY(int y) {
			this.y = y;
		}

		public int getZ() {
			return z;
		}

		public void setZ(int z) {
			this.z = z;
		}
	}

}
