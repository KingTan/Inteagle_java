package com.inteagle.common.websocket.controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.inteagle.common.entity.JsonResult;
import com.inteagle.common.websocket.server.WebSocketServer;

@Controller
@RequestMapping("/checkcenter")
public class CheckCenterController {

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

}
