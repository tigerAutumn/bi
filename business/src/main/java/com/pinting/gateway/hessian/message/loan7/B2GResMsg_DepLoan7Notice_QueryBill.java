package com.pinting.gateway.hessian.message.loan7;

import java.util.List;

import com.pinting.core.hessian.msg.ResMsg;
import com.pinting.gateway.hessian.message.loan7.model.Loan7QueryBillRepayment;

/**
 * 7贷存管--账单同步查询
 * @project gateway
 * @title B2GResMsg_DepLoan7Notice_QueryBill.java
 * @author Dragon & cat
 * @date 2017-12-12
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description
 */
public class B2GResMsg_DepLoan7Notice_QueryBill extends ResMsg {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3785637731357969857L;
	/*用户编号*/
	private 	String 	userId;
	/*借款编号*/
	private 	String 	loanId;
	/*借款协议编号*/
	private		String	agreementNo;
	/*借款协议下载地址*/
	private		String	agreementUrl;
	/*列表*/
	private		List<Loan7QueryBillRepayment> 	repayments;
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

	public List<Loan7QueryBillRepayment> getRepayments() {
		return repayments;
	}

	public void setRepayments(List<Loan7QueryBillRepayment> repayments) {
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
