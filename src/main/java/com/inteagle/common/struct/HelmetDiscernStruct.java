package com.inteagle.common.struct;

import com.inteagle.common.base.entity.BaseEntity;

import struct.StructClass;
import struct.StructField;

/**
 * 
 * @ClassName: HelmetDiscern
 * @Description: TODO(安全帽识别结构体)
 * @author IVAn
 * @date 2019年8月1日下午2:39:17
 *
 */
@StructClass
public class HelmetDiscernStruct extends BaseEntity {

	/**
	 * @Fields field:field:{todo}(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = 1L;

	@StructField(order = 0)
	public int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
