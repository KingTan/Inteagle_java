package com.inteagle.common.websocket.message;

import java.io.Serializable;
import com.alibaba.fastjson.annotation.JSONField;
import com.inteagle.common.base.entity.BaseEntity;

/**
 * 
* @ClassName: MessageData
* @Description: TODO(消息体对象)
* @author IVAn
* @date 2019年7月9日下午2:25:28
*
* @param <T>
 */
public class MessageData<T> extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	// 消息id
	public static final String MESSAGEID = "messageId";

	// 发送人用户id
	public static final String SENDERUSERID = "senderUserId";

	// 接收人用户id（发送类型为私聊时，不为空）
	public static final String RECEIVEDUSERID = "receivedUserId";

	// 发送类型（1私聊，2群聊,3系统消息，4心跳包）
	public static final String SENDERTYPE = "senderType";

	// 1私聊
	public static final int PRIVATE_CHAT = 1;

	// 2群聊
	public static final int GROUP_CHAT = 2;

	// 3系统消息
	public static final int SYSTEM_MSG = 3;

	// 4心跳包
	public static final int HEART_TEAT = 4;

	// 时间
	public static final String SENDTIME = "sendTime";

	// 消息类型（1文字，2图片，3语音，4视频，5透传消息）
	public static final String MESSAGETYPE = "messageType";

	// 消息状态（1未读，2已读）
	public static final String MESSAGESTATUS = "messageStatus";

	// 消息来源
	public static final String MESSAGESOURCE = "messageSource";

	// 1未读
	public static final int UNREADING = 1;

	// 2已读
	public static final int READING = 2;

	// 消息对象
	public static final String MESSAGE = "message";

	public MessageData() {
	}

	/**
	 * 判断是否为私聊
	 * 
	 * @return boolean
	 * @author peng.xy
	 * @createDate 2018年7月23日 下午3:29:51
	 */
	@JSONField(serialize = false)
	public static boolean isPrivateChat(Integer senderType) {
		return senderType != null && (senderType.intValue() == PRIVATE_CHAT);
	}

	/**
	 * 判断是否为群聊
	 * 
	 * @return boolean
	 * @author peng.xy
	 * @createDate 2018年7月23日 下午3:29:51
	 */
	@JSONField(serialize = false)
	public static boolean isGroupChat(Integer senderType) {
		return senderType != null && (senderType.intValue() == GROUP_CHAT);
	}

	/**
	 * 判断是否心跳包
	 * 
	 * @return boolean
	 * @author peng.xy
	 * @createDate 2018年7月23日 下午3:29:51
	 */
	@JSONField(serialize = false)
	public static boolean isHeartbeat(Integer senderType) {
		return senderType != null && (senderType.intValue() == HEART_TEAT);
	}

	/**
	 * 判断是否为已读
	 * 
	 * @return boolean
	 * @author peng.xy
	 * @createDate 2018年7月23日 下午3:29:51
	 */
	@JSONField(serialize = false)
	public static boolean isReading(Integer messageStatus) {
		return messageStatus != null && (messageStatus.intValue() == READING);
	}
}
