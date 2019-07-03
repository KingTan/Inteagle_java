package com.inteagle.apis.struct.entity;

import com.inteagle.common.base.entity.BaseEntity;

import struct.StructClass;
import struct.StructField;

@StructClass
public class IdInfoStruct extends BaseEntity {

	/**
	 * @Fields field:field:{todo}(序列化ID)
	 */
	private static final long serialVersionUID = 1L;

	@StructField(order = 0)
	public short id;

	@StructField(order = 1)
	public float x;

	@StructField(order = 2)
	public float y;

	@StructField(order = 3)
	public long t;

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

}
