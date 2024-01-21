package com.pinting.business.accounting.loan.model;

//币港湾宝付体系-云贷，还款系统分账
public class FixedRepaySysSplitInfo extends BaseAccount {

	private Double thdRepayAmount; //还款资金子账户,改变金额
	
	private Double thdBGWRevenueAmount;//币港湾对合作方营收子账户,改变金额
	
	private Double thdRevenueAmount;//营收子账户,改变金额
	
	private Double thdMarginAmount;//保证金子账户,改变金额
	
	private Double fee; //手续费
	
	private Integer lnRepayScheduleId; //还款计划表id

	public Double getThdRepayAmount() {
		return thdRepayAmount;
	}

	public void setThdRepayAmount(Double thdRepayAmount) {
		this.thdRepayAmount = thdRepayAmount;
	}

	public Double getThdRevenueAmount() {
		return thdRevenueAmount;
	}

	public void setThdRevenueAmount(Double thdRevenueAmount) {
		this.thdRevenueAmount = thdRevenueAmount;
	}

	public Double getThdBGWRevenueAmount() {
		return thdBGWRevenueAmount;
	}

	public void setThdBGWRevenueAmount(Double thdBGWRevenueAmount) {
		this.thdBGWRevenueAmount = thdBGWRevenueAmount;
	}

	public Double getFee() {
		return fee;
	}

	public void setFee(Double fee) {
		this.fee = fee;
	}

	public Double getThdMarginAmount() {
		return thdMarginAmount;
	}

	public void setThdMarginAmount(Double thdMarginAmount) {
		this.thdMarginAmount = thdMarginAmount;
	}

	public Integer getLnRepayScheduleId() {
		return lnRepayScheduleId;
	}

	public void setLnRepayScheduleId(Integer lnRepayScheduleId) {
		this.lnRepayScheduleId = lnRepayScheduleId;
	}
	
	
}
