package com.pinting.gateway.hessian.message.loan.model;

import java.io.Serializable;

/**
 * Created by Gemma on 2017/8/30.
 * 代偿通知 
 */
public class LateRepayment implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 6279556573013430556L;
	/**
	 * 用户编号
	 * */
	private String userId;
	/**
	 * 借款编号
	 * */
	private String loanId;
	/**
	 * 账单序号
	 * */
	private String repaySerial;
	/**
     * 账单编号
     */
    private String repayId;
   
    /**
     * ADVANCED 提前还款 
	 * NORMAL 正常还款
	 * OVERDUE 逾期还款（逾期且已还款）
     */
    private String repayType;

    /**
     * 代偿金额
     * 单位：分
     */
    private Long total;

    /**
     * 代偿本金
     * 单位：分
     */
    private Long principal;

    /**
     * 代偿利息
     * 单位：分
     */
    private Long interest;

    /**
     * 本金滞纳金
     * 单位：分
     */
    private Long principalOverdue;

    /**
     * 利息滞纳金
     * 单位：分
     */
    private Long interestOverdue;

    /**
     * 预留字段1
     * 单位：分
     */
    private Long reservedField1;

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

	public String getRepaySerial() {
		return repaySerial;
	}

	public void setRepaySerial(String repaySerial) {
		this.repaySerial = repaySerial;
	}

	public String getRepayId() {
		return repayId;
	}

	public void setRepayId(String repayId) {
		this.repayId = repayId;
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

	public Long getReservedField1() {
		return reservedField1;
	}

	public void setReservedField1(Long reservedField1) {
		this.reservedField1 = reservedField1;
	}
    
}
