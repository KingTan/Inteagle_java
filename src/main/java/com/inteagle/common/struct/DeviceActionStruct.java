package com.inteagle.common.struct;

import com.inteagle.common.base.entity.BaseEntity;

import struct.StructClass;
import struct.StructField;

/**
 * 
 * @ClassName: DeviceActionStruct
 * @Description: TODO(设备行为结构体)
 * @author IVAn
 * @date 2019年8月1日下午5:03:45
 *
 */
@StructClass
public class DeviceActionStruct extends BaseEntity {

	/**
	 * @Fields field:field:{todo}(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = 1L;

	@StructField(order = 0)
	private byte action;

	@StructField(order = 1)
	private byte device_type;

	@StructField(order = 2)
	private byte priority;

	public byte getAction() {
		return action;
	}

	public void setAction(byte action) {
		this.action = action;
	}

	public byte getDevice_type() {
		return device_type;
	}

	public void setDevice_type(byte device_type) {
		this.device_type = device_type;
	}

	public byte getPriority() {
		return priority;
	}

	public void setPriority(byte priority) {
		this.priority = priority;
	}

}
