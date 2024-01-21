package com.pinting.business.model.vo;

import com.pinting.business.model.BsBankCard;

public class BsBankCardVO extends BsBankCard {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3087044123447364638L;
	private String bankName;//银行名称
	private String subBranchName; 
	private String cityName;
	private String provinceName;
	private String mobile;//用户手机号
	private String idCard;//身份证号码
	private String obligateMobile;//银行预留手机
	private String receiptNo;//宝付回执单号

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getSubBranchName() {
		return subBranchName;
	}

	public void setSubBranchName(String subBranchName) {
		this.subBranchName = subBranchName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getObligateMobile() {
		return obligateMobile;
	}

	public void setObligateMobile(String obligateMobile) {
		this.obligateMobile = obligateMobile;
	}

	public String getReceiptNo() {
		return receiptNo;
	}

	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}

}
