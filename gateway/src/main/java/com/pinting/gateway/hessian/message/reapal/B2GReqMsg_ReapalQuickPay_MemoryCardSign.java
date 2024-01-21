package com.pinting.gateway.hessian.message.reapal;

import com.pinting.core.hessian.msg.ReqMsg;

public class B2GReqMsg_ReapalQuickPay_MemoryCardSign extends ReqMsg {

	private static final long serialVersionUID = -8304940860316686909L;

	private String cardNo; //借记卡的银行卡号
	
	private String owner; //用户姓名
	
	private String certNo; //用户填写的证件号码
	
	private String phone; //用户在银行预留手机号
	
	private String orderNo; //商户生成的唯一订单号
	
	private Double amount; //金额(单位:元)
	
	private String userId; //商户生成的用户ID
	
	private String userMacAddr; //用户MAC地址
	
	private String userIpAddr; //用户IP地址

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getCertNo() {
		return certNo;
	}

	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserMacAddr() {
		return userMacAddr;
	}

	public void setUserMacAddr(String userMacAddr) {
		this.userMacAddr = userMacAddr;
	}

	public String getUserIpAddr() {
		return userIpAddr;
	}

	public void setUserIpAddr(String userIpAddr) {
		this.userIpAddr = userIpAddr;
	}
	
}
