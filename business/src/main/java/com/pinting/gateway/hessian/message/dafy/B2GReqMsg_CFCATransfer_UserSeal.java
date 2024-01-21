package com.pinting.gateway.hessian.message.dafy;

import com.pinting.core.hessian.msg.ReqMsg;

public class B2GReqMsg_CFCATransfer_UserSeal extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 647803156345702967L;

	private Integer userId;

	private String userName;

	private String idCard;

	private String pfxPath;

	private String pfxPassword;

	private String sealPerson;

	private String sealOrg;

	private String sealName;

	private String sealCode;

	private String sealPassword;

	private String sealType;

	private String imagePath;

	private String imageCode;

	private String orgCode = "F001";

	public String getSealType() {
		return sealType;
	}

	public void setSealType(String sealType) {
		this.sealType = sealType;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getImageCode() {
		return imageCode;
	}

	public void setImageCode(String imageCode) {
		this.imageCode = imageCode;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getPfxPath() {
		return pfxPath;
	}

	public void setPfxPath(String pfxPath) {
		this.pfxPath = pfxPath;
	}

	public String getPfxPassword() {
		return pfxPassword;
	}

	public void setPfxPassword(String pfxPassword) {
		this.pfxPassword = pfxPassword;
	}

	public String getSealPerson() {
		return sealPerson;
	}

	public void setSealPerson(String sealPerson) {
		this.sealPerson = sealPerson;
	}

	public String getSealOrg() {
		return sealOrg;
	}

	public void setSealOrg(String sealOrg) {
		this.sealOrg = sealOrg;
	}

	public String getSealName() {
		return sealName;
	}

	public void setSealName(String sealName) {
		this.sealName = sealName;
	}

	public String getSealCode() {
		return sealCode;
	}

	public void setSealCode(String sealCode) {
		this.sealCode = sealCode;
	}

	public String getSealPassword() {
		return sealPassword;
	}

	public void setSealPassword(String sealPassword) {
		this.sealPassword = sealPassword;
	}

}
