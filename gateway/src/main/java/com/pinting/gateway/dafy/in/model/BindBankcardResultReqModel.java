package com.pinting.gateway.dafy.in.model;

public class BindBankcardResultReqModel extends BaseReqModel {
	
	private String customerIds;
	private String results;
	private String resultMsgs;
	public String getCustomerIds() {
		return customerIds;
	}
	public void setCustomerIds(String customerIds) {
		this.customerIds = customerIds;
	}
	public String getResults() {
		return results;
	}
	public void setResults(String results) {
		this.results = results;
	}
	public String getResultMsgs() {
		return resultMsgs;
	}
	public void setResultMsgs(String resultMsgs) {
		this.resultMsgs = resultMsgs;
	}

}
