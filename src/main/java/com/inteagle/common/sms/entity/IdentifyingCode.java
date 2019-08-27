package com.inteagle.common.sms.entity;

import java.io.Serializable;

/**
 * 
 * @ClassName: IdentifyingCode
 * @Description: TODO(验证码实体类)
 * @author IVAn
 * @date 2019年8月27日上午11:24:47
 *
 */
public class IdentifyingCode implements Serializable {

	/**
	 * @Fields field:field:{todo}(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = 1L;

	private String telephone;

	private String code;

	private String codeType;

	public String getCacheKey() {
		return this.telephone + "-" + this.codeType;
	}

	public String toString() {
		return "IdentifyingCode [telephone=" + this.telephone + ", code=" + this.code + ", codeType=" + this.codeType
				+ "]";
	}

	public IdentifyingCode() {
		super();
	}

	public IdentifyingCode(String telephone, String code, String codeType) {
		super();
		this.telephone = telephone;
		this.code = code;
		this.codeType = codeType;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCodeType() {
		return codeType;
	}

	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}

}
