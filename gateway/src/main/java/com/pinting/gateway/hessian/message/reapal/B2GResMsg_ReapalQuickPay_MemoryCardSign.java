package com.pinting.gateway.hessian.message.reapal;

import com.pinting.core.hessian.msg.ResMsg;

public class B2GResMsg_ReapalQuickPay_MemoryCardSign extends ResMsg {

	private static final long serialVersionUID = -8304940860316686909L;

	private String bindId;
	
	private String orderNo;
	
	private String bankName;
	
	private String bankCode;
	
	private String certificate;
	
	public String getCertificate() {
		return certificate;
	}

	public void setCertificate(String certificate) {
		this.certificate = certificate;
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

	public String getBindId() {
		return bindId;
	}

	public void setBindId(String bindId) {
		this.bindId = bindId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

}
