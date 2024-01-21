package com.pinting.gateway.pay19.out.model.resp;

public class RefundResp extends NewCounterBaseResp {

	private static final long serialVersionUID = -3460841035812861874L;

	private String mxResq;
	
	private String oriPayOrderId;
	
	private String amount;
	
	private String reserved;
	
	private String verifyString;
	
	private String fee;

	public String getMxResq() {
		return mxResq;
	}

	public void setMxResq(String mxResq) {
		this.mxResq = mxResq;
	}

	public String getOriPayOrderId() {
		return oriPayOrderId;
	}

	public void setOriPayOrderId(String oriPayOrderId) {
		this.oriPayOrderId = oriPayOrderId;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getReserved() {
		return reserved;
	}

	public void setReserved(String reserved) {
		this.reserved = reserved;
	}

	public String getVerifyString() {
		return verifyString;
	}

	public void setVerifyString(String verifyString) {
		this.verifyString = verifyString;
	}

	public String getFee() {
		return fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
	}

}
