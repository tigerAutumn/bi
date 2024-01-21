package com.dafy.model.vo;

public class LoanInfoVO {
	/*合作方订单号*/
	private String	partnerOrderNo;
	/*订单号*/
	private String	orderNo;
	/*申请借款金额*/
	private Double	applyAmount;
	/*银行名称*/
	private String	bankName;
	/*银行CODE*/
	private String	pay19BankCode;
	/*银行卡号*/
	private String	bankCardNo;
	/*姓名*/
	private String	userName;
	/*状态*/
	private String	status;
	/*恒丰客户号*/
	private String	hfUserId;
	

	public String getPartnerOrderNo() {
		return partnerOrderNo;
	}

	public void setPartnerOrderNo(String partnerOrderNo) {
		this.partnerOrderNo = partnerOrderNo;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Double getApplyAmount() {
		return applyAmount;
	}

	public void setApplyAmount(Double applyAmount) {
		this.applyAmount = applyAmount;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getPay19BankCode() {
		return pay19BankCode;
	}

	public void setPay19BankCode(String pay19BankCode) {
		this.pay19BankCode = pay19BankCode;
	}

	public String getBankCardNo() {
		return bankCardNo;
	}

	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getHfUserId() {
		return hfUserId;
	}

	public void setHfUserId(String hfUserId) {
		this.hfUserId = hfUserId;
	}
	
}
