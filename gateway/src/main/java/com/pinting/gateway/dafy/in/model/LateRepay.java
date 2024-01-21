package com.pinting.gateway.dafy.in.model;
/**
 * 代偿通知
 * 用户账单信息
 * @author Dragon & cat
 * @date 2016-12-14
 */
public class LateRepay {
	
	private String userId; //用户编号
	
	private String loanId; //借款编号
	
	private String repayId; //账单编号
	
	private Integer repaySerial; //账单序号
	
	private String repayType; //代偿类型
	
	private Long total; //代偿金额
	
	private Long principal; //代偿本金
	
	private Long interest; //代偿利息
	
	private Long principalOverdue; //本金滞纳金，非必填
	
	private Long interestOverdue; //利息滞纳金，非必填
	
	private String reservedField1; //预留字段1，非必填

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

	public String getRepayId() {
		return repayId;
	}

	public void setRepayId(String repayId) {
		this.repayId = repayId;
	}

	public Integer getRepaySerial() {
		return repaySerial;
	}

	public void setRepaySerial(Integer repaySerial) {
		this.repaySerial = repaySerial;
	}

	public String getRepayType() {
		return repayType;
	}

	public void setRepayType(String repayType) {
		this.repayType = repayType;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public Long getPrincipal() {
		return principal;
	}

	public void setPrincipal(Long principal) {
		this.principal = principal;
	}

	public Long getInterest() {
		return interest;
	}

	public void setInterest(Long interest) {
		this.interest = interest;
	}

	public Long getPrincipalOverdue() {
		return principalOverdue;
	}

	public void setPrincipalOverdue(Long principalOverdue) {
		this.principalOverdue = principalOverdue;
	}

	public Long getInterestOverdue() {
		return interestOverdue;
	}

	public void setInterestOverdue(Long interestOverdue) {
		this.interestOverdue = interestOverdue;
	}

	public String getReservedField1() {
		return reservedField1;
	}

	public void setReservedField1(String reservedField1) {
		this.reservedField1 = reservedField1;
	}
	
	
}
