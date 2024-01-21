package com.pinting.gateway.hessian.message.reapal;

import com.pinting.core.hessian.msg.ReqMsg;

public class B2GReqMsg_ReapalQuickPay_BindCardSign extends ReqMsg {

	private static final long serialVersionUID = -8304940860316686909L;

	private String bindId; //融宝绑卡ID
	
	private String orderNo; //商户生成的唯一订单号
	
	private Double amount; //金额(单位:元)
	
	private String userId; //商户生成的用户ID
	
	private String userMacAddr; //用户MAC地址
	
	private String userIpAddr; //用户IP地址

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

	public String getBindId() {
		return bindId;
	}

	public void setBindId(String bindId) {
		this.bindId = bindId;
	}
	
}
