package com.inteagle.apis.struct.entity;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.inteagle.common.base.entity.BaseEntity;

import cn.afterturn.easypoi.excel.annotation.Excel;
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
	@Excel(name = "设备ID", width = 30, orderNum = "0", isImportField = "true_st")
	private short id;

	// 电压(电压低于2600m/f时 电量不足)
	@StructField(order = 1)
	@Excel(name = "电压", width = 30, orderNum = "1", isImportField = "true_st")
	private int vol;

	// 温度
	@StructField(order = 2)
	@Excel(name = "温度", width = 30, orderNum = "2", isImportField = "true_st")
	private int temp;

	// 是否戴帽
	@StructField(order = 3)
	@Excel(name = "是否带帽", width = 30, orderNum = "3", isImportField = "true_st")
	private int helmet_on;

	// 电池记录主键
	private String sensorId;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Excel(name = "创建时间", width = 30, orderNum = "4",exportFormat = "yyyy-MM-dd HH:mm:ss", isImportField = "true_st")
	private Date inDate;
	
	public Date getInDate() {
		return inDate;
	}

	public void setInDate(Date inDate) {
		this.inDate = inDate;
	}

	public String getSensorId() {
		return sensorId;
	}

	public void setSensorId(String sensorId) {
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
