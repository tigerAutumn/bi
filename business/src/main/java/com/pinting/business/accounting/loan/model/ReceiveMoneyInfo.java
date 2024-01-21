package com.pinting.business.accounting.loan.model;

public class ReceiveMoneyInfo extends BaseAccount {
	
	private Integer investorRegActId;
	
	private Double principal;
	
	private Double interest;

	private Double serviceFee;
	
	private Integer lnFinancePlanId; //LnFinanceRepaySchedule表id 

	public Double getServiceFee() {
		return serviceFee;
	}

	public void setServiceFee(Double serviceFee) {
		this.serviceFee = serviceFee;
	}

	public Integer getInvestorRegActId() {
		return investorRegActId;
	}

	public void setInvestorRegActId(Integer investorRegActId) {
		this.investorRegActId = investorRegActId;
	}

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

	@Override
	public String toString() {
		return "ReceiveMoneyInfo{" +
				"investorRegActId=" + investorRegActId +
				", principal=" + principal +
				", interest=" + interest +
				'}';
	}

	public Integer getLnFinancePlanId() {
		return lnFinancePlanId;
	}

	public void setLnFinancePlanId(Integer lnFinancePlanId) {
		this.lnFinancePlanId = lnFinancePlanId;
	}
}
