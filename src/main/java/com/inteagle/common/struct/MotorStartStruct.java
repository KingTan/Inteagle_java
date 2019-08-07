package com.inteagle.common.struct;

import com.inteagle.common.base.entity.BaseEntity;

import struct.StructClass;
import struct.StructField;

/**
 * 
 * @ClassName: MotorStartStruct
 * @Description: TODO(电机数据结构体)
 * @author IVAn
 * @date 2019年8月4日下午8:03:35
 *
 */
@StructClass
public class MotorStartStruct extends BaseEntity {

	/**
	 * @Fields field:field:{todo}(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = 1L;

	@StructField(order = 0)
	private short fre;

	@StructField(order = 1)
	private short duty;

	@StructField(order = 2)
	private short steps;

	@StructField(order = 3)
	private short steps_hold;

	@StructField(order = 4)
	private short hold_time;

	@StructField(order = 5)
	private short dir;

	public short getFre() {
		return fre;
	}

	public void setFre(short fre) {
		this.fre = fre;
	}

	public short getDuty() {
		return duty;
	}

	public void setDuty(short duty) {
		this.duty = duty;
	}

	public short getSteps() {
		return steps;
	}

	public void setSteps(short steps) {
		this.steps = steps;
	}

	public short getSteps_hold() {
		return steps_hold;
	}

	public void setSteps_hold(short steps_hold) {
		this.steps_hold = steps_hold;
	}

	public short getHold_time() {
		return hold_time;
	}

	public void setHold_time(short hold_time) {
		this.hold_time = hold_time;
	}

	public short getDir() {
		return dir;
	}

	public void setDir(short dir) {
		this.dir = dir;
	}

}
