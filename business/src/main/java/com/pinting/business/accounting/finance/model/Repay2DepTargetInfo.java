package com.pinting.business.accounting.finance.model;

import com.pinting.business.accounting.loan.enums.PartnerEnum;

/**
 * 还款到标的记账入参
 * @project business
 * @author bianyatian
 * @2018-3-16 上午11:43:11
 */
public class Repay2DepTargetInfo {
	
	/** 还款服务费*/
	private Double loanServiceFee;
	
	/** 线下还款计划id*/
	private Integer depPlanId;
	
	/** 借款人合作方 */
	private  PartnerEnum partner;

	public PartnerEnum getPartner() {
		return partner;
	}

	public void setPartner(PartnerEnum partner) {
		this.partner = partner;
	}

	public Double getLoanServiceFee() {
		return loanServiceFee;
	}

	public void setLoanServiceFee(Double loanServiceFee) {
		this.loanServiceFee = loanServiceFee;
	}

	public Integer getDepPlanId() {
		return depPlanId;
	}

	public void setDepPlanId(Integer depPlanId) {
		this.depPlanId = depPlanId;
	}
	
	
}
