package com.pinting.business.accounting.loan.model;

public class RepayInfo extends BaseAccount {
	
	private Integer loanActId;
	
	private Double principal; //还款本金
	
	private Double financePrincipal; //应还至理财人的本金和
	
	private Double interest; 
	
	private Double overdueAmount;

	private Double fee;

	private Double bailAmount; //保证金

	private Double serviceFee; 
	
	private Integer chargeRuleId;

	private Integer regActId;

	private Integer authActId;

	private Integer diffActId;

	private Double diffAmount;
	
	private Double thdRepayAmount; //还款户金额
	
	private Double revenueZanAmount; //赞分期营收
	
	private Double bgwRevenueAmount; //币港湾营收
	
	private Integer lnFinancePlanId; //LnFinanceRepaySchedule表id 
	
	private Integer lnRepayScheduleId; //还款计划表id

	public Double getDiffAmount() {
		return diffAmount;
	}

	public void setDiffAmount(Double diffAmount) {
		this.diffAmount = diffAmount;
	}

	public Integer getRegActId() {
		return regActId;
	}

	public void setRegActId(Integer regActId) {
		this.regActId = regActId;
	}

	public Integer getDiffActId() {
		return diffActId;
	}

	public void setDiffActId(Integer diffActId) {
		this.diffActId = diffActId;
	}

	public Integer getAuthActId() {
		return authActId;
	}

	public void setAuthActId(Integer authActId) {
		this.authActId = authActId;
	}

	public Double getBailAmount() {
		return bailAmount;
	}

	public void setBailAmount(Double bailAmount) {
		this.bailAmount = bailAmount;
	}

	public Double getServiceFee() {
		return serviceFee;
	}

	public void setServiceFee(Double serviceFee) {
		this.serviceFee = serviceFee;
	}

	public Double getFee() {
		return fee;
	}

	public void setFee(Double fee) {
		this.fee = fee;
	}

	public Double getOverdueAmount() {
		return overdueAmount;
	}

	public void setOverdueAmount(Double overdueAmount) {
		this.overdueAmount = overdueAmount;
	}

	public Integer getChargeRuleId() {
		return chargeRuleId;
	}

	public void setChargeRuleId(Integer chargeRuleId) {
		this.chargeRuleId = chargeRuleId;
	}

	public Integer getLoanActId() {
		return loanActId;
	}

	public void setLoanActId(Integer loanActId) {
		this.loanActId = loanActId;
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
		return "RepayInfo{" +
				"loanActId=" + loanActId +
				", principal=" + principal +
				", interest=" + interest +
				", overdueAmount=" + overdueAmount +
				", fee=" + fee +
				", bailAmount=" + bailAmount +
				", serviceFee=" + serviceFee +
				", chargeRuleId=" + chargeRuleId +
				", regActId=" + regActId +
				", authActId=" + authActId +
				", diffActId=" + diffActId +
				", diffAmount=" + diffAmount +
				'}';
	}

	public Double getThdRepayAmount() {
		return thdRepayAmount;
	}

	public void setThdRepayAmount(Double thdRepayAmount) {
		this.thdRepayAmount = thdRepayAmount;
	}

	public Double getRevenueZanAmount() {
		return revenueZanAmount;
	}

	public void setRevenueZanAmount(Double revenueZanAmount) {
		this.revenueZanAmount = revenueZanAmount;
	}

	public Double getBgwRevenueAmount() {
		return bgwRevenueAmount;
	}

	public void setBgwRevenueAmount(Double bgwRevenueAmount) {
		this.bgwRevenueAmount = bgwRevenueAmount;
	}

	public Double getFinancePrincipal() {
		return financePrincipal;
	}

	public void setFinancePrincipal(Double financePrincipal) {
		this.financePrincipal = financePrincipal;
	}

	public Integer getLnFinancePlanId() {
		return lnFinancePlanId;
	}

	public void setLnFinancePlanId(Integer lnFinancePlanId) {
		this.lnFinancePlanId = lnFinancePlanId;
	}

	public Integer getLnRepayScheduleId() {
		return lnRepayScheduleId;
	}

	public void setLnRepayScheduleId(Integer lnRepayScheduleId) {
		this.lnRepayScheduleId = lnRepayScheduleId;
	}
}
