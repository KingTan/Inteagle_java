package com.inteagle.apis.struct.entity;

import java.util.Date;

import com.inteagle.common.base.entity.BaseEntity;

import struct.StructClass;
import struct.StructField;

/**
 * 
 * @ClassName: HelmetSensorDataStruct
 * @Description: TODO(电池情况结构体)
 * @author IVAn
 * @date 2019年8月2日下午3:27:17
 *
 */
@StructClass
public class HelmetSensorDataStruct extends BaseEntity {

	/**
	 * @Fields field:field:{todo}(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = 1L;

	// 设备ID
	@StructField(order = 0)
	private short id;

	// 电压(电压低于2600m/f时 电量不足)
	@StructField(order = 1)
	private int vol;

	// 温度
	@StructField(order = 2)
	private int temp;

	// 是否戴帽
	@StructField(order = 3)
	private int helmet_on;

	// 电池记录主键
	private String sensorId;

	public String getsensorId() {
		return sensorId;
	}

	public void setsensorId(String sensorId) {
		this.sensorId = sensorId;
	}

	public short getId() {
		return id;
	}

	public void setId(short id) {
		this.id = id;
	}

	public int getVol() {
		return vol;
	}

	public void setVol(int vol) {
		this.vol = vol;
	}

	public int getTemp() {
		return temp;
	}

	public void setTemp(int temp) {
		this.temp = temp;
	}

	public int getHelmet_on() {
		return helmet_on;
	}

	public void setHelmet_on(int helmet_on) {
		this.helmet_on = helmet_on;
	}

}
