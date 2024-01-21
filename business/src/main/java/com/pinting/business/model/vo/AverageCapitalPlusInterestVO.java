package com.pinting.business.model.vo;

public class AverageCapitalPlusInterestVO {

	private Integer repaySerial; //还款期次

	private Double planTotal; //计划总金额

	private Double planPrincipal; //计划本金

	private Double planInterest; //计划利息

	public Integer getRepaySerial() {
		return repaySerial;
	}

	public void setRepaySerial(Integer repaySerial) {
		this.repaySerial = repaySerial;
	}

	public Double getPlanTotal() {
		return planTotal;
	}

	public void setPlanTotal(Double planTotal) {
		this.planTotal = planTotal;
	}

	public Double getPlanPrincipal() {
		return planPrincipal;
	}

	public void setPlanPrincipal(Double planPrincipal) {
		this.planPrincipal = planPrincipal;
	}

	public Double getPlanInterest() {
		return planInterest;
	}

	public void setPlanInterest(Double planInterest) {
		this.planInterest = planInterest;
	}
}
