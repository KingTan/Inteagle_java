package com.inteagle.modal.struct;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.inteagle.modal.base.BaseEntity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import struct.StructClass;
import struct.StructField;

@StructClass
public class IdInfoStruct extends BaseEntity {

	/**
	 * @Fields field:field:{todo}(序列化ID)
	 */
	private static final long serialVersionUID = 1L;
	
	//带符号位(转换前)
	@StructField(order = 0)
	@Excel(name = "设备ID", width = 30, orderNum = "0", isImportField = "true_st")
	public short id;
	
	//无符号位(转换后)
	private int unSignedId;

	@StructField(order = 1)
	@Excel(name = "x_Data", width = 30, orderNum = "2", isImportField = "true_st")
	public float x;

	@StructField(order = 2)
	@Excel(name = "y_Data", width = 30, orderNum = "3", isImportField = "true_st")
	public float y;

	@StructField(order = 3)
	@Excel(name = "t_Data", width = 30, orderNum = "4", isImportField = "true_st")
	public long t;

	@StructField(order = 4)
	@Excel(name = "相机ID", width = 30, orderNum = "1", isImportField = "true_st")
	public short camera_id;
	
	private String istructId;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Excel(name = "创建时间", width = 40, orderNum = "5",exportFormat = "yyyy-MM-dd HH:mm:ss", isImportField = "true_st")
	private Date createTime;
	
	public int getUnSignedId() {
		return unSignedId;
	}

	public void setUnSignedId(int unSignedId) {
		this.unSignedId = unSignedId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getIstructId() {
		return istructId;
	}

	public void setIstructId(String istructId) {
		this.istructId = istructId;
	}

	public short getId() {
		return id;
	}

	public void setId(short id) {
		this.id = id;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public long getT() {
		return t;
	}

	public void setT(long t) {
		this.t = t;
	}

	public short getCamera_id() {
		return camera_id;
	}

	public void setCamera_id(short camera_id) {
		this.camera_id = camera_id;
	}

}
