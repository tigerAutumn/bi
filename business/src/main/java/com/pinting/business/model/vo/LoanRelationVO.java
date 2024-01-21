package com.pinting.business.model.vo;

import java.util.Date;

import com.pinting.business.model.LnLoanRelation;

public class LoanRelationVO extends LnLoanRelation {
	
	private int period; //借款期限
	
	private Date loanTime; //借款成功时间

	public int getPeriod() {
		return period;
	}

	public void setPeriod(int period) {
		this.period = period;
	}

	public Date getLoanTime() {
		return loanTime;
	}

	public void setLoanTime(Date loanTime) {
		this.loanTime = loanTime;
	}

}
