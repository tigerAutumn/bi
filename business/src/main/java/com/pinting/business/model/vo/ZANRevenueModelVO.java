package com.pinting.business.model.vo;

/**
 * 营收详细明细
 * @author bianyatian
 * @2016-10-24 上午10:35:20
 */
public class ZANRevenueModelVO {
	private Double principal; //应还本金
	private Double interest; //应还币港湾利息
	private Double superviseFee; //应还监管费
	private Double infoServiceFee; //应还信息服务费
	private Double accountManageFee; //应还账户管理费
	private Double Premium; //应还保费
	private Double lateFee; //应提滞纳金
	private Double totalRepay; //应还总费用
	private Double financierPrincipalInterest; //应还理财人本息
	private Double ZANDeposit; //应提保证金
	private Double BGWServiceFee; //应提币港湾服务
	private Double serviceFee; //应提手续费
	private Double ZANRevenue; //赞分期营收
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
	public Double getSuperviseFee() {
		return superviseFee;
	}
	public void setSuperviseFee(Double superviseFee) {
		this.superviseFee = superviseFee;
	}
	public Double getInfoServiceFee() {
		return infoServiceFee;
	}
	public void setInfoServiceFee(Double infoServiceFee) {
		this.infoServiceFee = infoServiceFee;
	}
	public Double getAccountManageFee() {
		return accountManageFee;
	}
	public void setAccountManageFee(Double accountManageFee) {
		this.accountManageFee = accountManageFee;
	}
	public Double getLateFee() {
		return lateFee;
	}
	public void setLateFee(Double lateFee) {
		this.lateFee = lateFee;
	}
	public Double getTotalRepay() {
		return totalRepay;
	}
	public void setTotalRepay(Double totalRepay) {
		this.totalRepay = totalRepay;
	}
	public Double getZANDeposit() {
		return ZANDeposit;
	}
	public void setZANDeposit(Double zANDeposit) {
		ZANDeposit = zANDeposit;
	}
	public Double getBGWServiceFee() {
		return BGWServiceFee;
	}
	public void setBGWServiceFee(Double bGWServiceFee) {
		BGWServiceFee = bGWServiceFee;
	}
	public Double getServiceFee() {
		return serviceFee;
	}
	public void setServiceFee(Double serviceFee) {
		this.serviceFee = serviceFee;
	}
	public Double getZANRevenue() {
		return ZANRevenue;
	}
	public void setZANRevenue(Double zANRevenue) {
		ZANRevenue = zANRevenue;
	}
	public Double getPremium() {
		return Premium;
	}
	public void setPremium(Double premium) {
		Premium = premium;
	}
	public Double getFinancierPrincipalInterest() {
		return financierPrincipalInterest;
	}
	public void setFinancierPrincipalInterest(Double financierPrincipalInterest) {
		this.financierPrincipalInterest = financierPrincipalInterest;
	}
	
	
}
