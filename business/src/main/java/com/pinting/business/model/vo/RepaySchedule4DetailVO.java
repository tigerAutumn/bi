package com.pinting.business.model.vo;

import com.pinting.business.model.LnRepaySchedule;


public class RepaySchedule4DetailVO extends LnRepaySchedule {
	
	private Double principal;//本金

	private Double interest;//利息

	private Double principalOverdue;//本金逾期金额
	
	private Double interestOverdue;//利息逾期金额
	
	private Integer principalId;//本金detailId
	
	private Integer interestId;//利息detailId

	private Integer principalOverdueId;//本金逾期金额detailId
	
	private Integer interestOverdueId;//利息逾期金额detailId

	public Double getPrincipal() {
		return principal;
	}

	public void setPrincipal(Double principal) {
		this.principal = principal;
	}

	public Double getInterest() {
		return interest;
	}

	public void setInterest(Double interest) {
		this.interest = interest;
	}

	public Double getPrincipalOverdue() {
		return principalOverdue;
	}

	public void setPrincipalOverdue(Double principalOverdue) {
		this.principalOverdue = principalOverdue;
	}

	public Double getInterestOverdue() {
		return interestOverdue;
	}

	public void setInterestOverdue(Double interestOverdue) {
		this.interestOverdue = interestOverdue;
	}

	public Integer getPrincipalId() {
		return principalId;
	}

	public void setPrincipalId(Integer principalId) {
		this.principalId = principalId;
	}

	public Integer getInterestId() {
		return interestId;
	}

	public void setInterestId(Integer interestId) {
		this.interestId = interestId;
	}

	public Integer getPrincipalOverdueId() {
		return principalOverdueId;
	}

	public void setPrincipalOverdueId(Integer principalOverdueId) {
		this.principalOverdueId = principalOverdueId;
	}

	public Integer getInterestOverdueId() {
		return interestOverdueId;
	}

	public void setInterestOverdueId(Integer interestOverdueId) {
		this.interestOverdueId = interestOverdueId;
	}
	
	
}
