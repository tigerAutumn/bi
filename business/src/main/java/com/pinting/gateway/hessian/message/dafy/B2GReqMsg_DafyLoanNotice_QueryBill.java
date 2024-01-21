package com.pinting.gateway.hessian.message.dafy;

import com.pinting.core.hessian.msg.ReqMsg;
/**
 * 自主放款--账单同步查询
 * @author Dragon & cat
 * @date 2016-12-15
 */
public class B2GReqMsg_DafyLoanNotice_QueryBill extends ReqMsg {
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
