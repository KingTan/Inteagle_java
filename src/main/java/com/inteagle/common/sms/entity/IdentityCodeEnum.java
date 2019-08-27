package com.inteagle.common.sms.entity;

public enum IdentityCodeEnum {

	// 验证码短信模板编号
	Register_Code("T170317005080"), Login_Code("T170317005076"),
	
	//验证码类型
	Register("register"), Login("login"),

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
