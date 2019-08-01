package com.inteagle.common.enumList;

public enum DeviceActionEnum {

	ONLINE(0), offLine(1);

	private int value;

	private DeviceActionEnum(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

}
