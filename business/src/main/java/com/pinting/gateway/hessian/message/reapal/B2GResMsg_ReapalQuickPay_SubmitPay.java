package com.pinting.gateway.hessian.message.reapal;

import com.pinting.core.hessian.msg.ResMsg;

public class B2GResMsg_ReapalQuickPay_SubmitPay extends ResMsg {

	private static final long serialVersionUID = -4724805051971224971L;

	private String orderNo;
	
	private String tradeNo;
	
	private String bankCardType;
	
	private String bankName;
	
	private String bankCode;
	
	private String phone;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getBankCardType() {
		return bankCardType;
	}

	public void setBankCardType(String bankCardType) {
		this.bankCardType = bankCardType;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}
