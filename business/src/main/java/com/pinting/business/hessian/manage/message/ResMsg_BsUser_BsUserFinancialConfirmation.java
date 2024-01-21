package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * 财务确认处理查看详情中查询用户基本信息   出参
 * Created by shh on 2016/11/3 21:26.
 */
public class ResMsg_BsUser_BsUserFinancialConfirmation extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7966366433835222368L;
	
	/** 姓名  */
	private String userName;
	
	/**  身份证 */
	private String idCard;
	
	/**  注册手机 */
	private String mobile;
	
	/** 预留手机  */
	private String reservedMobile;
	
	/**  银行卡号 */
	private String cardNo;
	
	private String age;
	
	private String gender;
	
	/**  身份证归属地 */
	private String idCardAttribution;
	
	/**  银行卡归属地 */
	private String cardNoAttribution;
	
	/**  注册手机归属地 */
	private String registeredMobileAttribution;
	
	/**  预留手机归属地 */
	private String reservedMobileAttribution;
	
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

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getReservedMobile() {
		return reservedMobile;
	}

	public void setReservedMobile(String reservedMobile) {
		this.reservedMobile = reservedMobile;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getIdCardAttribution() {
		return idCardAttribution;
	}

	public void setIdCardAttribution(String idCardAttribution) {
		this.idCardAttribution = idCardAttribution;
	}

	public String getCardNoAttribution() {
		return cardNoAttribution;
	}

	public void setCardNoAttribution(String cardNoAttribution) {
		this.cardNoAttribution = cardNoAttribution;
	}

	public String getRegisteredMobileAttribution() {
		return registeredMobileAttribution;
	}

	public void setRegisteredMobileAttribution(String registeredMobileAttribution) {
		this.registeredMobileAttribution = registeredMobileAttribution;
	}

	public String getReservedMobileAttribution() {
		return reservedMobileAttribution;
	}

	public void setReservedMobileAttribution(String reservedMobileAttribution) {
		this.reservedMobileAttribution = reservedMobileAttribution;
	}
	
}
