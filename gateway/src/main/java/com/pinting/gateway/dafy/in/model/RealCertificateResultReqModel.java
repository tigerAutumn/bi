package com.pinting.gateway.dafy.in.model;

public class RealCertificateResultReqModel extends BaseReqModel {
	
	private String customerIds;
	private String result;
	public String getCustomerIds() {
		return customerIds;
	}
	public void setCustomerIds(String customerIds) {
		this.customerIds = customerIds;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}

}
