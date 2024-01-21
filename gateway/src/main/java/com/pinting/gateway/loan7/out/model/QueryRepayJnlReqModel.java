package com.pinting.gateway.loan7.out.model;

public class QueryRepayJnlReqModel extends BaseReqModel {
	
	private String customerId;
	private String borrowNo;
	private String clientKey;
	
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getBorrowNo() {
		return borrowNo;
	}
	public void setBorrowNo(String borrowNo) {
		this.borrowNo = borrowNo;
	}
	public String getClientKey() {
		return clientKey;
	}
	public void setClientKey(String clientKey) {
		this.clientKey = clientKey;
	}

}
