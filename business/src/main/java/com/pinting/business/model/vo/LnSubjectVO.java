package com.pinting.business.model.vo;

import java.util.Date;

import com.pinting.business.model.LnSubject;

public class LnSubjectVO extends LnSubject {
	
	private Double approveAmount; //批准金额
	
	private Date loanTime; //借款成功时间
	
	private String partnerCode;
	
	private String chargeRuleCode;

	private Integer term;


	public String getPartnerCode() {
		return partnerCode;
	}

	public void setPartnerCode(String partnerCode) {
		this.partnerCode = partnerCode;
	}

	public String getChargeRuleCode() {
		return chargeRuleCode;
	}

	public void setChargeRuleCode(String chargeRuleCode) {
		this.chargeRuleCode = chargeRuleCode;
	}

	public Integer getTerm() {
		return term;
	}

	public void setTerm(Integer term) {
		this.term = term;
	}

	public Double getApproveAmount() {
		return approveAmount;
	}

	public void setApproveAmount(Double approveAmount) {
		this.approveAmount = approveAmount;
	}

	public Date getLoanTime() {
		return loanTime;
	}

	public void setLoanTime(Date loanTime) {
		this.loanTime = loanTime;
	}
	    

}
