package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_Statistics_FinanceTotal extends ReqMsg {
	/**
	 * 序列化编号
	 */
	private static final long serialVersionUID = 1364790902284750444L;
	
	private double sysAccBalance; //系统账户余额
	private double accBalance; //用户余额
	private double proAccBalance; //产品户余额
	private double retAccBalance; //回款户余额
	private double bonusAccBalance; //用户奖励金户余额
	
	public double getSysAccBalance() {
		return sysAccBalance;
	}
	public void setSysAccBalance(double sysAccBalance) {
		this.sysAccBalance = sysAccBalance;
	}
	public double getAccBalance() {
		return accBalance;
	}
	public void setAccBalance(double accBalance) {
		this.accBalance = accBalance;
	}
	public double getProAccBalance() {
		return proAccBalance;
	}
	public void setProAccBalance(double proAccBalance) {
		this.proAccBalance = proAccBalance;
	}
	public double getRetAccBalance() {
		return retAccBalance;
	}
	public void setRetAccBalance(double retAccBalance) {
		this.retAccBalance = retAccBalance;
	}
	public double getBonusAccBalance() {
		return bonusAccBalance;
	}
	public void setBonusAccBalance(double bonusAccBalance) {
		this.bonusAccBalance = bonusAccBalance;
	}
	
}