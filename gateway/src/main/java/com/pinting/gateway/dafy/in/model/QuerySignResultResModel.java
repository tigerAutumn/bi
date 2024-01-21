package com.pinting.gateway.dafy.in.model;

public class QuerySignResultResModel extends BaseResModel {
	/*签章结果*/
	private			String		signResult;
	/*借款编号*/
	private			String		loanId;
	/*协议下载地址*/
	private			String		agreementUrl;
	/*出借人信息*/
	//private			List<Lenders>		lenders;
	
	public String getSignResult() {
		return signResult;
	}
	public void setSignResult(String signResult) {
		this.signResult = signResult;
	}
	public String getAgreementUrl() {
		return agreementUrl;
	}
	public void setAgreementUrl(String agreementUrl) {
		this.agreementUrl = agreementUrl;
	}
	public String getLoanId() {
		return loanId;
	}
	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}
	
	
}
