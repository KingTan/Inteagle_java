package com.inteagle.utils.struct;

import com.inteagle.modal.base.BaseEntity;

import struct.StructClass;
import struct.StructField;

/**
 * 
* @ClassName: TimeSyncData
* @Description: TODO(时间同步结构体)
* @author IVAn
* @date 2019年9月11日上午10:06:22
*
 */
@StructClass
public class TimeSyncData extends BaseEntity {

	/**
	 * @Fields field:field:{todo}(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = 1428184715532600340L;

	@StructField(order = 0)
	private int t_h;

	@StructField(order = 1)
	private int t_l;

	public int getT_h() {
		return t_h;
	}

	public void setT_h(int t_h) {
		this.t_h = t_h;
	}

	public int getT_l() {
		return t_l;
	}

	public void setT_l(int t_l) {
		this.t_l = t_l;
	}

}
