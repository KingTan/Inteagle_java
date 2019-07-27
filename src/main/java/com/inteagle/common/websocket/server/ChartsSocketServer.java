package com.inteagle.common.websocket.server;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.server.standard.SpringConfigurator;

import com.alibaba.fastjson.JSONObject;
import com.inteagle.common.websocket.message.MessageData;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @ClassName: ChartsSocketServer
 * @Description: TODO(基坑charts图标发送数据 socket)
 * @author IVAn
 * @date 2019年7月13日下午2:23:54
 *
 */
@ServerEndpoint(value="/chartsSocket/{id}/{rate}")
@Component
@Slf4j
public class ChartsSocketServer {

	// 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
	private static int onlineCount = 0;

	// concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
	private static CopyOnWriteArraySet<ChartsSocketServer> chartsSocketWebSocketSet = new CopyOnWriteArraySet<ChartsSocketServer>();

	// 与某个客户端的连接会话，需要通过它来给客户端发送数据
	private Session session;

	// 接收ID
	private String id = "";

	/**
	 * 连接建立成功调用的方法
	 */
	@OnOpen
	public void onOpen(Session session, @PathParam("id") String id) {
		this.session = session;
		chartsSocketWebSocketSet.add(this); // 加入set中
		addOnlineCount(); // 在线数加1
		log.info("建立新连接,连接ID--:" + id + ",当前在线人数为" + getOnlineCount());
		this.id = id;
		try {
			JSONObject object = new JSONObject();
			object.put("senderType", 0);
			object.put("message", "socket连接成功");
			sendMessage(object.toJSONString());
		} catch (IOException e) {
			log.error("websocket IO异常");
		}
	}

	/**
	 * 连接关闭调用的方法
	 */
	@OnClose
	public void onClose() {
		try {
			chartsSocketWebSocketSet.remove(this); // 从set中删除
			subOnlineCount(); // 在线数减1
			log.info("有一连接关闭！当前在线人数为" + getOnlineCount());
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
		}

	}

	/**
	 * 收到客户端消息后调用的方法
	 *
	 * @param message 客户端发送过来的消息
	 */
	@OnMessage
	public void onMessage(String message, Session session) {
		// 获取MessageData对象
		JSONObject messageJson = JSONObject.parseObject(message);
		// 消息发送类型
		Integer senderType = messageJson.getInteger(MessageData.SENDERTYPE);

		if (MessageData.isHeartbeat(senderType)) {
			// 心跳消息
			this.heartbeatReply(message);
		} else {
			log.info("收到来自窗口" + id + "的信息:" + message);
		}

		// 群发消息
//			for (WebSocketServer item : webSocketSet) {
//				try {
//					item.sendMessage(message);
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
	}

	/**
	 * 
	 * @param session
	 * @param error
	 */
	@OnError
	public void onError(Session session, Throwable error) {
		log.error("发生错误");
		error.printStackTrace();
	}

	/**
	 * @description 发送心跳
	 * @author IVAn
	 * @date 2019年7月9日 下午2:26:38
	 * @param message
	 */
	private void heartbeatReply(String message) {
		try {
			this.sendMessage(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 实现服务器主动推送
	 */
	public void sendMessage(String message) throws IOException {
		this.session.getBasicRemote().sendText(message);
	}

	/**
	 * 群发自定义消息
	 */
	public static void sendInfo(String message, @PathParam("id") String id) throws IOException {
		log.info("推送消息到窗口" + id + "，推送内容:" + message);
		for (ChartsSocketServer item : chartsSocketWebSocketSet) {
			try {
				// 这里可以设定只推送给这个id的，为null则全部推送
				if (id == null) {
					item.sendMessage(message);
				} else if (item.id.equals(id)) {
					item.sendMessage(message);
				}
			} catch (IOException e) {
				continue;
			}
		}
	}

	public static synchronized int getOnlineCount() {
		return onlineCount;
	}

	public static synchronized void addOnlineCount() {
		ChartsSocketServer.onlineCount++;
	}

	public static synchronized void subOnlineCount() {
		ChartsSocketServer.onlineCount--;
	}

}
