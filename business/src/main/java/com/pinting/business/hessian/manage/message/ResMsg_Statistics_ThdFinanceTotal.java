package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * 财务总账查询(宝付)
 * @author SHENGP
 * @date  2017年4月26日 下午3:46:29
 */
public class ResMsg_Statistics_ThdFinanceTotal extends ResMsg {

	private static final long serialVersionUID = 3773150970995099165L;
	
	private Double sysAccBalance; //系统账户余额
	private Double accBalance; //用户余额
	private Double proAccBalance; //产品户余额（云贷）
	private Double retAccBalance; //回款户余额（云贷）
	private Double bonusAccBalance; //用户奖励金户余额
	private Double redPaktAccBalance;//红包户余额
	private Double sysFreezeAccBalance;//用户利息冻结余额
	private Double pro7AccBalance;//产品户余额（7贷）
	private Double ret7AccBalance;//回款户余额（7贷）
	
	private Double proAuthZanAccBalance;//站岗户余额（赞分期）
	private Double proZanAccBalance;//产品户余额（赞分期）
	private Double retZanAccBalance;//回款户余额（赞分期）
	private Double marginZanAccBalance;//风险保证金账户余额（赞分期）
	private Double revenueZanAccBalance;//营收账户余额（赞分期）
	private Double badloansZanAccBalance;//坏账户余额（赞分期）
	private Double revenueBgwAccBalance;//币港湾营收账户余额（赞分期）
	private Double feeBgwAccBalance;//币港湾手续费账户余额（赞分期）
	private Double revenueBgwYunAccBalance; // 币港湾营收账户余额（云贷）
	private Double revenueYunAccBalance;  // 营收账户余额（云贷）
	private Double repayAccBalance;  // 币港湾宝付体系-还款资金子账户
	private Double revenueBgw7AccBalance; // 币港湾营收账户余额（七贷）
	private Double revenue7AccBalance;  // 营收账户余额（七贷）
	
	/********************20171108管理台新增赞时贷需求***********************/
	
	private Double revenueBgwZsdAccBalance; //币港湾营收账户余额（赞时贷）
	private Double revenueZsdAccBalance;//营收账户余额（赞时贷）
	private Double marginZsdAccBalance;//风险保证金账户余额(赞时贷)
	
	private Double bgwAuthZsdAccBalance; //存管赞时贷站岗户
	private Double bgwRegZsdAccBalance;  //存管赞时贷产品户
	private Double bgwReturnZsdAccBalance; //存管赞时贷回款户
	private Double bgwAuthRedZsdAccBalance; //赞时贷产品站岗红包 
	
	public Double getSysAccBalance() {
		return sysAccBalance;
	}
	public void setSysAccBalance(Double sysAccBalance) {
		this.sysAccBalance = sysAccBalance;
	}
	public Double getAccBalance() {
		return accBalance;
	}
	public void setAccBalance(Double accBalance) {
		this.accBalance = accBalance;
	}
	public Double getProAccBalance() {
		return proAccBalance;
	}
	public void setProAccBalance(Double proAccBalance) {
		this.proAccBalance = proAccBalance;
	}
	public Double getRetAccBalance() {
		return retAccBalance;
	}
	public void setRetAccBalance(Double retAccBalance) {
		this.retAccBalance = retAccBalance;
	}
	public Double getBonusAccBalance() {
		return bonusAccBalance;
	}
	public void setBonusAccBalance(Double bonusAccBalance) {
		this.bonusAccBalance = bonusAccBalance;
	}
	public Double getRedPaktAccBalance() {
		return redPaktAccBalance;
	}
	public void setRedPaktAccBalance(Double redPaktAccBalance) {
		this.redPaktAccBalance = redPaktAccBalance;
	}
	public Double getSysFreezeAccBalance() {
		return sysFreezeAccBalance;
	}
	public void setSysFreezeAccBalance(Double sysFreezeAccBalance) {
		this.sysFreezeAccBalance = sysFreezeAccBalance;
	}
	public Double getPro7AccBalance() {
		return pro7AccBalance;
	}
	public void setPro7AccBalance(Double pro7AccBalance) {
		this.pro7AccBalance = pro7AccBalance;
	}
	public Double getRet7AccBalance() {
		return ret7AccBalance;
	}
	public void setRet7AccBalance(Double ret7AccBalance) {
		this.ret7AccBalance = ret7AccBalance;
	}
	public Double getProAuthZanAccBalance() {
		return proAuthZanAccBalance;
	}
	public void setProAuthZanAccBalance(Double proAuthZanAccBalance) {
		this.proAuthZanAccBalance = proAuthZanAccBalance;
	}
	public Double getProZanAccBalance() {
		return proZanAccBalance;
	}
	public void setProZanAccBalance(Double proZanAccBalance) {
		this.proZanAccBalance = proZanAccBalance;
	}
	public Double getRetZanAccBalance() {
		return retZanAccBalance;
	}
	public void setRetZanAccBalance(Double retZanAccBalance) {
		this.retZanAccBalance = retZanAccBalance;
	}
	public Double getMarginZanAccBalance() {
		return marginZanAccBalance;
	}
	public void setMarginZanAccBalance(Double marginZanAccBalance) {
		this.marginZanAccBalance = marginZanAccBalance;
	}
	public Double getRevenueZanAccBalance() {
		return revenueZanAccBalance;
	}
	public void setRevenueZanAccBalance(Double revenueZanAccBalance) {
		this.revenueZanAccBalance = revenueZanAccBalance;
	}
	public Double getBadloansZanAccBalance() {
		return badloansZanAccBalance;
	}
	public void setBadloansZanAccBalance(Double badloansZanAccBalance) {
		this.badloansZanAccBalance = badloansZanAccBalance;
	}
	public Double getRevenueBgwAccBalance() {
		return revenueBgwAccBalance;
	}
	public void setRevenueBgwAccBalance(Double revenueBgwAccBalance) {
		this.revenueBgwAccBalance = revenueBgwAccBalance;
	}
	public Double getFeeBgwAccBalance() {
		return feeBgwAccBalance;
	}
	public void setFeeBgwAccBalance(Double feeBgwAccBalance) {
		this.feeBgwAccBalance = feeBgwAccBalance;
	}
	public Double getRevenueBgwYunAccBalance() {
		return revenueBgwYunAccBalance;
	}
	public void setRevenueBgwYunAccBalance(Double revenueBgwYunAccBalance) {
		this.revenueBgwYunAccBalance = revenueBgwYunAccBalance;
	}
	public Double getRevenueYunAccBalance() {
		return revenueYunAccBalance;
	}
	public void setRevenueYunAccBalance(Double revenueYunAccBalance) {
		this.revenueYunAccBalance = revenueYunAccBalance;
	}
	public Double getRepayAccBalance() {
		return repayAccBalance;
	}
	public void setRepayAccBalance(Double repayAccBalance) {
		this.repayAccBalance = repayAccBalance;
	}
	public Double getRevenueBgw7AccBalance() {
		return revenueBgw7AccBalance;
	}
	public void setRevenueBgw7AccBalance(Double revenueBgw7AccBalance) {
		this.revenueBgw7AccBalance = revenueBgw7AccBalance;
	}
	public Double getRevenue7AccBalance() {
		return revenue7AccBalance;
	}
	public void setRevenue7AccBalance(Double revenue7AccBalance) {
		this.revenue7AccBalance = revenue7AccBalance;
	}
	public Double getRevenueBgwZsdAccBalance() {
		return revenueBgwZsdAccBalance;
	}
	public void setRevenueBgwZsdAccBalance(Double revenueBgwZsdAccBalance) {
		this.revenueBgwZsdAccBalance = revenueBgwZsdAccBalance;
	}
	public Double getRevenueZsdAccBalance() {
		return revenueZsdAccBalance;
	}
	public void setRevenueZsdAccBalance(Double revenueZsdAccBalance) {
		this.revenueZsdAccBalance = revenueZsdAccBalance;
	}
	public Double getMarginZsdAccBalance() {
		return marginZsdAccBalance;
	}
	public void setMarginZsdAccBalance(Double marginZsdAccBalance) {
		this.marginZsdAccBalance = marginZsdAccBalance;
	}
	public Double getBgwAuthZsdAccBalance() {
		return bgwAuthZsdAccBalance;
	}
	public void setBgwAuthZsdAccBalance(Double bgwAuthZsdAccBalance) {
		this.bgwAuthZsdAccBalance = bgwAuthZsdAccBalance;
	}
	public Double getBgwRegZsdAccBalance() {
		return bgwRegZsdAccBalance;
	}
	public void setBgwRegZsdAccBalance(Double bgwRegZsdAccBalance) {
		this.bgwRegZsdAccBalance = bgwRegZsdAccBalance;
	}
	public Double getBgwReturnZsdAccBalance() {
		return bgwReturnZsdAccBalance;
	}
	public void setBgwReturnZsdAccBalance(Double bgwReturnZsdAccBalance) {
		this.bgwReturnZsdAccBalance = bgwReturnZsdAccBalance;
	}
	public Double getBgwAuthRedZsdAccBalance() {
		return bgwAuthRedZsdAccBalance;
	}
	public void setBgwAuthRedZsdAccBalance(Double bgwAuthRedZsdAccBalance) {
		this.bgwAuthRedZsdAccBalance = bgwAuthRedZsdAccBalance;
	}
	
}
