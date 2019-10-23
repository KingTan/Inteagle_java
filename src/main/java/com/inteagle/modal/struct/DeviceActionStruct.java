package com.inteagle.modal.struct;

import com.inteagle.modal.base.BaseEntity;

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
	
	//设备行为
	@StructField(order = 0)
	private byte action;
	
	//设备类型
	@StructField(order = 1)
	private byte device_type;
	
	//设备优先级
	@StructField(order = 2)
	private byte priority;
	
	//设备ID(带符号位、转换前)
	@StructField(order = 3)
	private short device_id;
	
	//无符号位(转换后)
	private int unSignedId;
	
	private String recordId;

	public int getUnSignedId() {
		return unSignedId;
	}

	public void setUnSignedId(int unSignedId) {
		this.unSignedId = unSignedId;
	}

	public short getDevice_id() {
		return device_id;
	}

	public void setDevice_id(short device_id) {
		this.device_id = device_id;
	}

	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

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
