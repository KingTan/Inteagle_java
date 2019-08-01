package com.inteagle.common.enumList;

public enum DeviceTypeEnum {

	DEVICE_TYPE_6LBR(0), DEVICE_TYPE_HELMET(1), DEVICE_TYPE_CAMERA_MASTER(2), DEVICE_TYPE_CAMERA_SLAVE(3),
	DEVICE_TYPE_MOTOR(4), DEVICE_TYPE_IMU(5);

	private int value;

	private DeviceTypeEnum(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
}
