package com.pinting.business.model.vo;

public class RedPaktBudgetStatVO {
	
	private double totalBudgetAmount;//总审核通过
	private double expiryAmount;//已逾期
	private double usedBudgetAmount;//已使用预算额度
	private double usedRedPaktAmount;//红包已使用
	private double unUsedRedPaktAmount;//红包未使用
	
	private double badloansZanAccBalance;//坏账户余额（赞分期）
	
	public double getTotalBudgetAmount() {
		return totalBudgetAmount;
	}
	public void setTotalBudgetAmount(double totalBudgetAmount) {
		this.totalBudgetAmount = totalBudgetAmount;
	}
	public double getExpiryAmount() {
		return expiryAmount;
	}
	public void setExpiryAmount(double expiryAmount) {
		this.expiryAmount = expiryAmount;
	}
	public double getUsedBudgetAmount() {
		return usedBudgetAmount;
	}
	public void setUsedBudgetAmount(double usedBudgetAmount) {
		this.usedBudgetAmount = usedBudgetAmount;
	}
	public double getUsedRedPaktAmount() {
		return usedRedPaktAmount;
	}
	public void setUsedRedPaktAmount(double usedRedPaktAmount) {
		this.usedRedPaktAmount = usedRedPaktAmount;
	}
	public double getUnUsedRedPaktAmount() {
		return unUsedRedPaktAmount;
	}
	public void setUnUsedRedPaktAmount(double unUsedRedPaktAmount) {
		this.unUsedRedPaktAmount = unUsedRedPaktAmount;
	}
	public double getBadloansZanAccBalance() {
		return badloansZanAccBalance;
	}
	public void setBadloansZanAccBalance(double badloansZanAccBalance) {
		this.badloansZanAccBalance = badloansZanAccBalance;
	}
	
}
