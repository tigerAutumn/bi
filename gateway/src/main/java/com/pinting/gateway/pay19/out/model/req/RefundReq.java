package com.pinting.gateway.pay19.out.model.req;

public class RefundReq extends NewCounterBaseReq {

	private static final long serialVersionUID = 6599753601319554510L;

	private String mxResq;
	
	private String oriPayOrderId;
	
	private String amount;
	
	private String notifyUrl;
	
	private String oriPayDate;
	
	private String tradeDesc;
	
	private String remark;
	
	private String reserved;
	
	private String tradeType;
	
	private String currencyType;

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

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public String getOriPayDate() {
		return oriPayDate;
	}

	public void setOriPayDate(String oriPayDate) {
		this.oriPayDate = oriPayDate;
	}

	public String getTradeDesc() {
		return tradeDesc;
	}

	public void setTradeDesc(String tradeDesc) {
		this.tradeDesc = tradeDesc;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getReserved() {
		return reserved;
	}

	public void setReserved(String reserved) {
		this.reserved = reserved;
	}

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public String getCurrencyType() {
		return currencyType;
	}

	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
	}
}
