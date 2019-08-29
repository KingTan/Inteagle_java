package com.inteagle.common.idCardAudit.entity;

import java.io.Serializable;
/**
 * 
 * @ClassName: IdCardAuditEntity
 * @Description: TODO(身份证实名认证实体类)
 * @author IVAn
 * @date 2019年8月29日上午10:14:24
 *
 */
public class IdCardAuditEntity implements Serializable {

	/**
	 * @Fields field:field:{todo}(序列化)
	 */
	private static final long serialVersionUID = 1L;

	// 身份证号码
	private String idCard;

	// 姓名
	private String name;

	// 手机号码
	private String phone;

	// 错误次数
	private int errorCounts;

	public IdCardAuditEntity() {
		super();
	}

	public IdCardAuditEntity(String idCard, String name, String phone, int errorCounts) {
		super();
		this.idCard = idCard;
		this.name = name;
		this.phone = phone;
		this.errorCounts = errorCounts;
	}

	public String getCacheKey() {
		return this.phone + "-idCardAudit";
	}

	public String getIdCard() {
		return idCard;
	}

	public String getName() {
		return name;
	}

	public String getPhone() {
		return phone;
	}

	public int getErrorCounts() {
		return errorCounts;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setErrorCounts(int errorCounts) {
		this.errorCounts = errorCounts;
	}
}
