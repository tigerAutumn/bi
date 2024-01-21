package com.pinting.business.model.vo;

/**
 * 对账查询所需数据
 * @author bianyatian
 * @2017-5-11 下午5:20:38
 */
public class BsSysAccountJnlCheckVO {

	private Double userBalance;
	
	private Double authZan;
	
	private Double authYun;
	
	private Double sumBalance;

	public Double getUserBalance() {
		return userBalance;
	}

	public void setUserBalance(Double userBalance) {
		this.userBalance = userBalance;
	}

	public Double getAuthZan() {
		return authZan;
	}

	public void setAuthZan(Double authZan) {
		this.authZan = authZan;
	}

	public Double getAuthYun() {
		return authYun;
	}

	public void setAuthYun(Double authYun) {
		this.authYun = authYun;
	}

	public Double getSumBalance() {
		return sumBalance;
	}

	public void setSumBalance(Double sumBalance) {
		this.sumBalance = sumBalance;
	}
	
}
