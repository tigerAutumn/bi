package com.pinting.gateway.dafy.in.model;

public class AgreementNoticeReqModel extends BaseReqModel{
	/*客户端标识*/
	private			String 		clientKey;
	/*借款编号*/
	private	 String	 loanId;

	public String getLoanId() {
		return loanId;
	}

	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}

	public String getClientKey() {
		return clientKey;
	}

	public void setClientKey(String clientKey) {
		this.clientKey = clientKey;
	}
	
}
