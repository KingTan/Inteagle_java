package com.inteagle.common.websocket.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.inteagle.common.entity.JsonResult;
import com.inteagle.common.websocket.message.MessageData;
import com.inteagle.common.websocket.server.WebSocketServer;

@Controller
@RequestMapping("/checkcenter")
public class CheckCenterController {

	@ResponseBody
	@RequestMapping("/sendMove")
	public JsonResult<Object> moveBall(int a_x, int a_y, int a_t, int b_x, int b_y, int b_t) {

			JSONObject object_a = new JSONObject();
			object_a.put("id", "a");
			object_a.put("x", a_x);
			object_a.put("y", a_y);
			object_a.put("t", a_t);
			object_a.put("camera_id", "001");

			JSONObject object_b = new JSONObject();
			object_b.put("id", "b");
			object_b.put("x", b_x);
			object_b.put("y", b_y);
			object_b.put("t", b_t);
			object_b.put("camera_id", "002");

			List<Object> jsonobject_list = new ArrayList<Object>();
			jsonobject_list.add(object_a);
			jsonobject_list.add(object_b);

			JSONObject send_object = new JSONObject();
			send_object.put("senderType", MessageData.PRIVATE_CHAT);
			send_object.put("messageData", jsonobject_list);

			try {
				WebSocketServer.sendInfo(send_object.toJSONString(), "ivan");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		return new JsonResult<Object>(JsonResult.SUCCESS);
	}

	// 推送数据接口
	@ResponseBody
	@RequestMapping("/socket/push/")
	public JsonResult<String> pushToWeb(String cid, String message) {
		try {
			WebSocketServer.sendInfo(message, cid);
		} catch (IOException e) {
			e.printStackTrace();
			return new JsonResult<String>(JsonResult.ERROR, e.toString(), JsonResult.ERROR_MESSAGE);
		}
		return new JsonResult<String>(JsonResult.SUCCESS, "发送成功", JsonResult.SUCCESS_MESSAGE);
	}

	// 推送安全帽人脸报警数据
	@ResponseBody
	@RequestMapping("/helmet")
	public JsonResult<String> pushHelmetWarningToWeb() {
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("data", "-1");
			jsonObject.put("dataType", "helmet_analysis");
			jsonObject.put("helmet_id", "255");
			// 发送socket消息
			WebSocketServer.sendInfo(JSONObject.toJSONString(jsonObject), "ivan");
		} catch (IOException e) {
			e.printStackTrace();
			return new JsonResult<String>(JsonResult.ERROR, e.toString(), JsonResult.ERROR_MESSAGE);
		}
		return new JsonResult<String>(JsonResult.SUCCESS, "发送成功", JsonResult.SUCCESS_MESSAGE);
	}

}
