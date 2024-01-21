package com.pinting.gateway.hessian.message.loan7;

import java.util.ArrayList;
import java.util.HashMap;

import org.hibernate.validator.constraints.NotEmpty;

import com.pinting.core.hessian.msg.ReqMsg;
/**
 * 
 * @project gateway
 * @title G2BReqMsg_DepLoan7_PushBill.java
 * @author Dragon & cat
 * @date 2017-12-12
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description
 */
public class G2BReqMsg_DepLoan7_PushBill extends ReqMsg {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8233746739526256715L;
	/*用户编号*/
	@NotEmpty(message="userId为空")
	private		String		userId;
	/*借款编号*/
	@NotEmpty(message="loanId为空")
	private		String		loanId;
	/*借款协议编号*/
	private		String		agreementNo;
	/*借款协议下载地址*/
	private		String		agreementUrl;
	/*还款计划列表*/
	private		ArrayList<HashMap<String, Object>> 	repayments;
	
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
	public ArrayList<HashMap<String, Object>> getRepayments() {
		return repayments;
	}
	public void setRepayments(ArrayList<HashMap<String, Object>> repayments) {
		this.repayments = repayments;
	}
	
	
}
