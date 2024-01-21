package com.pinting.gateway.loan7.in.model;

public class QuerySignResultReqModel extends BaseReqModel {
	/*客户端标识*/
	private			String 		clientKey;
	/*借款协议号*/
	private			String		agreementNo;

	public String getAgreementNo() {
		return agreementNo;
	}

	public void setAgreementNo(String agreementNo) {
		this.agreementNo = agreementNo;
	}

	public String getClientKey() {
		return clientKey;
	}

	public void setClientKey(String clientKey) {
		this.clientKey = clientKey;
	}
	
}
