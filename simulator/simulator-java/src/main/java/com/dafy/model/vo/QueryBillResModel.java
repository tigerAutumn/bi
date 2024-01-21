package com.dafy.model.vo;

import java.util.Date;
import java.util.List;

/**
 * 自主放款-账单同步
 * @author Dragon & cat
 * @date 2016-11-24
 */
public class QueryBillResModel {
	private String respCode;
	private String respMsg;
	private Date responseTime;
	private String hash;
	
	/*用户编号*/
	private 	String 	userId;
	/*借款编号*/
	private 	String 	loanId;
	/*借款协议编号*/
	private		String	agreementNo;
	/*借款协议下载地址*/
	private		String	agreementUrl;
	/*列表*/
	private		List<Repayments> 	repayments;
	
	
	
	public String getRespCode() {
		return respCode;
	}
	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}
	public String getRespMsg() {
		return respMsg;
	}
	public void setRespMsg(String respMsg) {
		this.respMsg = respMsg;
	}
	public Date getResponseTime() {
		return responseTime;
	}
	public void setResponseTime(Date responseTime) {
		this.responseTime = responseTime;
	}
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
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
	public List<Repayments> getRepayments() {
		return repayments;
	}
	public void setRepayments(List<Repayments> repayments) {
		this.repayments = repayments;
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
	
}
