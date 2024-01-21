package com.pinting.open.pojo.response.asset;

import com.pinting.open.base.response.Response;

public class TotalAssetsResponse extends Response {
	private Double assetAmount; //账户总资产
	private Double regularAmount; //投资本金
	private Double investEarnings; //投资收益
	private Double balance; //账户余额
	private Double jljBalance; //我的奖励
	private Double freezeBalance; //结算户账户冻结金额
	
	private Double depBalance; //存管账户余额
	private String depAccountStatus; //存管户可用余额(OLD 只有存管前账户   、DEP  只有存管户 、 DOUBLE  双账户并存)
	private Double depFreezeBalance; //存管结算户账户冻结金额

	public Double getDepFreezeBalance() {
		return depFreezeBalance;
	}

	public void setDepFreezeBalance(Double depFreezeBalance) {
		this.depFreezeBalance = depFreezeBalance;
	}

	public Double getAssetAmount() {
		return assetAmount;
	}
	public void setAssetAmount(Double assetAmount) {
		this.assetAmount = assetAmount;
	}
	public Double getRegularAmount() {
		return regularAmount;
	}
	public void setRegularAmount(Double regularAmount) {
		this.regularAmount = regularAmount;
	}
	public Double getInvestEarnings() {
		return investEarnings;
	}
	public void setInvestEarnings(Double investEarnings) {
		this.investEarnings = investEarnings;
	}
	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	public Double getJljBalance() {
		return jljBalance;
	}
	public void setJljBalance(Double jljBalance) {
		this.jljBalance = jljBalance;
	}
	public Double getFreezeBalance() {
		return freezeBalance;
	}
	public void setFreezeBalance(Double freezeBalance) {
		this.freezeBalance = freezeBalance;
	}
	public Double getDepBalance() {
		return depBalance;
	}
	public void setDepBalance(Double depBalance) {
		this.depBalance = depBalance;
	}
	public String getDepAccountStatus() {
		return depAccountStatus;
	}
	public void setDepAccountStatus(String depAccountStatus) {
		this.depAccountStatus = depAccountStatus;
	}
	
}
