package com.pinting.gateway.loan7.out.model;
/**
 * 自主放款-账单同步
 * @author Dragon & cat
 * @date 2016-11-24
 */
public class QueryBillReqModel extends BaseReqModel {
	/*用户编号*/
	private String userId;
	/*借款编号*/
	private	String loanId;
	/*申请流水号*/
	private	String	requestSeq;
	
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
	public String getRequestSeq() {
		return requestSeq;
	}
	public void setRequestSeq(String requestSeq) {
		this.requestSeq = requestSeq;
	}
	@Override
	public String toString() {
		return "QueryBillReqModel [userId=" + userId + ", loanId=" + loanId
				+ ", requestSeq=" + requestSeq + "]";
	}
	
}
