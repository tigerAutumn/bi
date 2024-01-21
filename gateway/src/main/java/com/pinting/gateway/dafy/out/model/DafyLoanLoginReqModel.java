package com.pinting.gateway.dafy.out.model;

public class DafyLoanLoginReqModel extends BaseReqModel {
	/*申请流水号*/
	private		String		requestSeq;
	private String clientSecret;

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public String getRequestSeq() {
		return requestSeq;
	}

	public void setRequestSeq(String requestSeq) {
		this.requestSeq = requestSeq;
	}
	
}
