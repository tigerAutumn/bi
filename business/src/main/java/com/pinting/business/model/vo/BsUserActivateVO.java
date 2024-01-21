package com.pinting.business.model.vo;
/**
 * 获取激活页面信息
 * @project business
 * @title BsUserActivateVO.java
 * @author Dragon & cat
 * @date 2017-4-12
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description
 */
public class BsUserActivateVO {
	/*手机号*/
	private		String 	 	mobile;
	/*姓名*/
	private		String		userName;
	/*身份证号*/
	private		String		idCard;
	/*银行卡号*/
	private		String		bankCard;
	/*银行ID*/
	private		String		bankId;
	/*银行名称*/
	private		String		bankName;
	/*银行小图标，一般情况下使用小图标*/
	private		String		smallLogo;
	/*大图标*/
	private		String		largeLogo;
	/*个人存管账户*/
	private		String		depAccount;
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
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
	public String getBankCard() {
		return bankCard;
	}
	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
	}

	public String getBankId() {
		return bankId;
	}
	public void setBankId(String bankId) {
		this.bankId = bankId;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getSmallLogo() {
		return smallLogo;
	}
	public void setSmallLogo(String smallLogo) {
		this.smallLogo = smallLogo;
	}
	public String getLargeLogo() {
		return largeLogo;
	}
	public void setLargeLogo(String largeLogo) {
		this.largeLogo = largeLogo;
	}
	public String getDepAccount() {
		return depAccount;
	}
	public void setDepAccount(String depAccount) {
		this.depAccount = depAccount;
	}
	
	
}
