package com.pinting.business.accounting.loan.model;

import java.util.Date;

public class LnRepayScheduleVO {

	private Integer loanId;
	
	private Date planDate;
	
	private Date loanDate;

	public Integer getLoanId() {
		return loanId;
	}

	public void setLoanId(Integer loanId) {
		this.loanId = loanId;
	}

	public Date getPlanDate() {
		return planDate;
	}

	public void setPlanDate(Date planDate) {
		this.planDate = planDate;
	}

	public Date getLoanDate() {
		return loanDate;
	}

	public void setLoanDate(Date loanDate) {
		this.loanDate = loanDate;
	}
}
