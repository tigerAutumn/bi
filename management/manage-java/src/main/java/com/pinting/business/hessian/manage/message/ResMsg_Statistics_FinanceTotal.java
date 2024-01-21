package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_Statistics_FinanceTotal extends ResMsg {
	/**
	 * 序列化编号
	 */
	private static final long serialVersionUID = 1364790902284750444L;
	
	private double sysAccBalance; //系统账户余额
	private double accBalance; //用户余额
	private double proAccBalance; //产品户余额
	private double retAccBalance; //回款户余额
	private double bonusAccBalance; //用户奖励金户余额
	private double redPaktAccBalance;//红包户余额
	private double sysFreezeAccBalance;//用户利息冻结余额
	private double pro7AccBalance;//产品户余额（7贷）
    private double ret7AccBalance;//回款户余额（7贷）
    
    private double proAuthZanAccBalance;//站岗户余额（赞分期）
	private double proZanAccBalance;//产品户余额（赞分期）
	private double retZanAccBalance;//回款户余额（赞分期）
	private double marginZanAccBalance;//风险保证金账户余额（赞分期）
	private double revenueZanAccBalance;//营收账户余额（赞分期）
	private double badloansZanAccBalance;//坏账户余额（赞分期）
	private double revenueBgwAccBalance;//币港湾营收账户余额（赞分期）
	private double feeBgwAccBalance;//币港湾手续费账户余额（赞分期）
    
	public double getPro7AccBalance() {
        return pro7AccBalance;
    }
    public void setPro7AccBalance(double pro7AccBalance) {
        this.pro7AccBalance = pro7AccBalance;
    }
    public double getRet7AccBalance() {
        return ret7AccBalance;
    }
    public void setRet7AccBalance(double ret7AccBalance) {
        this.ret7AccBalance = ret7AccBalance;
    }
    public double getSysFreezeAccBalance() {
		return sysFreezeAccBalance;
	}
	public void setSysFreezeAccBalance(double sysFreezeAccBalance) {
		this.sysFreezeAccBalance = sysFreezeAccBalance;
	}
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
	public double getRedPaktAccBalance() {
		return redPaktAccBalance;
	}
	public void setRedPaktAccBalance(double redPaktAccBalance) {
		this.redPaktAccBalance = redPaktAccBalance;
	}
	public double getProAuthZanAccBalance() {
		return proAuthZanAccBalance;
	}
	public void setProAuthZanAccBalance(double proAuthZanAccBalance) {
		this.proAuthZanAccBalance = proAuthZanAccBalance;
	}
	public double getProZanAccBalance() {
		return proZanAccBalance;
	}
	public void setProZanAccBalance(double proZanAccBalance) {
		this.proZanAccBalance = proZanAccBalance;
	}
	public double getRetZanAccBalance() {
		return retZanAccBalance;
	}
	public void setRetZanAccBalance(double retZanAccBalance) {
		this.retZanAccBalance = retZanAccBalance;
	}
	public double getMarginZanAccBalance() {
		return marginZanAccBalance;
	}
	public void setMarginZanAccBalance(double marginZanAccBalance) {
		this.marginZanAccBalance = marginZanAccBalance;
	}
	public double getRevenueZanAccBalance() {
		return revenueZanAccBalance;
	}
	public void setRevenueZanAccBalance(double revenueZanAccBalance) {
		this.revenueZanAccBalance = revenueZanAccBalance;
	}
	public double getBadloansZanAccBalance() {
		return badloansZanAccBalance;
	}
	public void setBadloansZanAccBalance(double badloansZanAccBalance) {
		this.badloansZanAccBalance = badloansZanAccBalance;
	}
	public double getRevenueBgwAccBalance() {
		return revenueBgwAccBalance;
	}
	public void setRevenueBgwAccBalance(double revenueBgwAccBalance) {
		this.revenueBgwAccBalance = revenueBgwAccBalance;
	}
	public double getFeeBgwAccBalance() {
		return feeBgwAccBalance;
	}
	public void setFeeBgwAccBalance(double feeBgwAccBalance) {
		this.feeBgwAccBalance = feeBgwAccBalance;
	}
	
}