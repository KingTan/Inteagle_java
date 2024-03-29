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
import com.inteagle.common.websocket.server.ChartsSocketServer;
import com.inteagle.utils.DateUtil;
import com.inteagle.utils.UserUtil;

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

		// 深层水平位移数据
		JSONObject jsonObject_data = new JSONObject();
		jsonObject_data.put(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(date), data_array_1);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("messageType", "foundation");
		jsonObject.put("data", jsonObject_data);

		// 顶部水平位移数据
		ArrayList<Data_single> data_array_top = new ArrayList<Data_single>();

		Data_single data_single_top = new Data_single();
		// 50以内的随机数
		data_single_top.setX((int) (Math.random() * 40) + 10);
		data_single_top.setY((int) (Math.random() * 40) + 10);
		data_single_top.setZ(0);
		data_array_top.add(data_single_top);

		JSONObject top_jsonObject_data = new JSONObject();
		top_jsonObject_data.put(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(date), data_array_top);

		JSONObject top_jsonObject = new JSONObject();
		top_jsonObject.put("messageType", "foundation_top_horizontal");
		top_jsonObject.put("data", top_jsonObject_data);

		try {
			// 发送基坑深层水平位移数据
			ChartsSocketServer.sendInfo(jsonObject.toJSONString(), null);
			// 发送顶部水平位移数据
			ChartsSocketServer.sendInfo(top_jsonObject.toJSONString(), null);
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
