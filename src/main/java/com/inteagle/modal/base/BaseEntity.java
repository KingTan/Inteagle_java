package com.inteagle.modal.base;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.inteagle.utils.UserUtil;

/**
 * 
 * @ClassName: BaseEntity
 * @Description: TODO(实体基类)
 * @author IVAn
 * @date 2019年6月25日
 *
 */
public class BaseEntity implements Serializable {

	/**
	 * 生成UUID
	 */
	public static String getUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	/**
	 * @Fields field:field:{todo}序列化ID
	 */
	private static final long serialVersionUID = 1L;

	// 创建时间
	//@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	protected Date inDate;

	// 创建人
	@JsonInclude(JsonInclude.Include.NON_NULL)
	protected String inUserName;

	// 修改时间
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	protected Date editDate;

	// 修改人
	@JsonInclude(JsonInclude.Include.NON_NULL)
	protected String editUserName;

	public void preUpadte() {
		this.editDate = new Date();
		this.editUserName = UserUtil.getUserId();
	}

	public void preUpadte(Date editDate, String editUserName) {
		this.editDate = editDate;
		this.editUserName = editUserName;
	}

	public void preInsert() {
		this.inDate = new Date();
		this.inUserName = UserUtil.getUserId();
		this.preUpadte(this.inDate, this.inUserName);
	}

	public Date getInDate() {
		return inDate;
	}

	public void setInDate(Date inDate) {
		this.inDate = inDate;
	}

	public Date getEditDate() {
		return editDate;
	}

	public void setEditDate(Date editDate) {
		this.editDate = editDate;
	}

	public String getInUserName() {
		return inUserName;
	}

	public void setInUserName(String inUserName) {
		this.inUserName = inUserName;
	}

	public String getEditUserName() {
		return editUserName;
	}

	public void setEditUserName(String editUserName) {
		this.editUserName = editUserName;
	}

}
