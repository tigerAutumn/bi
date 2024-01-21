package com.pinting.gateway.hessian.message.loan7;

import com.pinting.core.hessian.msg.ReqMsg;
/**
 * 7贷存管--账单同步查询
 * @project gateway
 * @title B2GReqMsg_DepLoan7Notice_QueryBill.java
 * @author Dragon & cat
 * @date 2017-12-12
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description
 */
public class B2GReqMsg_DepLoan7Notice_QueryBill extends ReqMsg {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5678847807309562732L;
	/*用户编号*/
	private String userId;
	/*借款编号*/
	private	String loanId;
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
	
	
}
