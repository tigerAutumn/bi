package com.pinting.gateway.hessian.message.dafy;

import com.pinting.core.hessian.msg.ResMsg;

public class B2GResMsg_Payment_BasicInformation extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4068794223798165866L;
	private int status;//业务逻辑
	private int thirdStatus;//三方状态
	private String customerId; //达飞系统的客户ID
	private String name;//客户姓名
	private String mobile;//手机号
	private String cardNo;//身份证
	private String bankName;//开户银行名称
	private String openAccountProvince;//开户省份
	private String subbranchName;//支行名称
	private String bankCardNo;//银行卡号
	private String openAccountCity;//所在城市
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getThirdStatus() {
		return thirdStatus;
	}
	public void setThirdStatus(int thirdStatus) {
		this.thirdStatus = thirdStatus;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getOpenAccountProvince() {
		return openAccountProvince;
	}
	public void setOpenAccountProvince(String openAccountProvince) {
		this.openAccountProvince = openAccountProvince;
	}
	public String getSubbranchName() {
		return subbranchName;
	}
	public void setSubbranchName(String subbranchName) {
		this.subbranchName = subbranchName;
	}
	public String getBankCardNo() {
		return bankCardNo;
	}
	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}
	public String getOpenAccountCity() {
		return openAccountCity;
	}
	public void setOpenAccountCity(String openAccountCity) {
		this.openAccountCity = openAccountCity;
	}
	
}
