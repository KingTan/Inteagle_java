package com.inteagle.common.sms.entity;

public enum IdentityCodeEnum {

	Register_Code("T170317005080"), 
	Login_Code("T170317005076"),
	Success_Code("提交成功!");

	private IdentityCodeEnum(String value) {
		this.value = value;
	}

	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
