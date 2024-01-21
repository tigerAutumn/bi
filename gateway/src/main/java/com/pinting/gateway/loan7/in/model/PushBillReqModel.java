package com.pinting.gateway.loan7.in.model;

import java.util.List;

public class PushBillReqModel extends BaseReqModel {
	/*客户端标识*/
	private		String 		clientKey;
	/*用户编号*/
	private		String		userId;
	/*借款编号*/
	private		String		loanId;
	/*借款协议编号*/
	private		String		agreementNo;
	/*借款协议下载地址*/
	private		String		agreementUrl;
	/*还款计划列表*/
	private		List<Repayment> 	repayments;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getLoanId() {
		return loanId;
	}

	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}

	public String getAgreementNo() {
		return agreementNo;
	}

	public void setAgreementNo(String agreementNo) {
		this.agreementNo = agreementNo;
	}

	public String getAgreementUrl() {
		return agreementUrl;
	}

	public void setAgreementUrl(String agreementUrl) {
		this.agreementUrl = agreementUrl;
	}

	public List<Repayment> getRepayments() {
		return repayments;
	}

	public void setRepayments(List<Repayment> repayments) {
		this.repayments = repayments;
	}

	public String getClientKey() {
		return clientKey;
	}

	public void setClientKey(String clientKey) {
		this.clientKey = clientKey;
	}
	
	
}
