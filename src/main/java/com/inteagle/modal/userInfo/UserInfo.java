package com.inteagle.modal.userInfo;

import com.inteagle.modal.base.BaseEntity;

/**
 * 
 * @ClassName: UserInfo
 * @Description: TODO(登录信息实体类)
 * @author IVAn
 * @date 2019年6月25日
 *
 */
public class UserInfo extends BaseEntity {

	/**
	 * @Fields field:field:{todo}(序列化ID)
	 */
	private static final long serialVersionUID = 1L;

	private String userId;

	private String roleId;

	private String openId;

	private String userName;

	private String gender;

	private String phone;

	private String idCardNum;

	private String corpName;

	private String password;

	private String email;

	private String headPortrait;

	private String userStatus;

	private String userIsonline;

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId == null ? null : userId.trim();
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId == null ? null : roleId.trim();
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName == null ? null : userName.trim();
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender == null ? null : gender.trim();
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone == null ? null : phone.trim();
	}

	public String getIdCardNum() {
		return idCardNum;
	}

	public void setIdCardNum(String idCardNum) {
		this.idCardNum = idCardNum == null ? null : idCardNum.trim();
	}

	public String getCorpName() {
		return corpName;
	}

	public void setCorpName(String corpName) {
		this.corpName = corpName == null ? null : corpName.trim();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password == null ? null : password.trim();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email == null ? null : email.trim();
	}

	public String getHeadPortrait() {
		return headPortrait;
	}

	public void setHeadPortrait(String headPortrait) {
		this.headPortrait = headPortrait == null ? null : headPortrait.trim();
	}

	public String getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus == null ? null : userStatus.trim();
	}

	public String getUserIsonline() {
		return userIsonline;
	}

	public void setUserIsonline(String userIsonline) {
		this.userIsonline = userIsonline == null ? null : userIsonline.trim();
	}

}