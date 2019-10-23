package com.inteagle.utils.struct;

import com.inteagle.modal.base.BaseEntity;

import struct.StructClass;
import struct.StructField;

/**
 * 
 * @ClassName: IdPositionStruct
 * @Description: TODO(ID位置结构体)
 * @author IVAn
 * @date 2019年8月1日下午3:18:24
 *
 */
@StructClass
public class IdPositionStruct extends BaseEntity {

	/**
	 * @Fields field:field:{todo}(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = 1L;

	@StructField(order = 0)
	private short id;

	@StructField(order = 1)
	private float position;

	public short getId() {
		return id;
	}

	public void setId(short id) {
		this.id = id;
	}

	public float getPosition() {
		return position;
	}

	public void setPosition(float position) {
		this.position = position;
	}

}
