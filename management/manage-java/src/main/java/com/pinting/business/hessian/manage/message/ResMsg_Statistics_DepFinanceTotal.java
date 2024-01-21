package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * 恒丰账户财务总账查询
 * @author SHENGP
 * @date  2017年4月25日 下午5:07:12
 */
public class ResMsg_Statistics_DepFinanceTotal extends ResMsg {

	private static final long serialVersionUID = -7037306281468548683L;

	private Double depSysAccBalance; //系统账户余额 DEP_JSH
	private Double depRedPaktAccBalance; //系统红包可用余额 DEP_REDPACKET
	private Double depUserAccBalance; // 用户余额 BGW_USER
	private Double depProRegAccBalance; //云贷产品户余额 BGW_REG_YUN
	private Double depRetAccBalance;   //云贷回款户余额 BGW_RETURN_YUN
	private Double depAuthYunAccBalance;  //云贷站岗户余额 BGW_AUTH_YUN
	private Double depRedAccBalance; //云贷产品站岗红包  RED
	private Double depHeadFeeAccBalance; //云贷砍头息户余额 DEP_HEADFEE_YUN
	private Double depOtherFeeAccBalance; //提现手续费余额 DEP_OTHER_FEE
	private Double depRevenueZanAccBalance; // 币港湾营收户（赞分期产品）DEP_BGW_REVENUE_ZAN
	private Double deprevenueYunAccBalance; // 币港湾营收户（云贷产品）DEP_BGW_REVENUE_YUN
	private Double depRegZanAccBalance; // 赞分期产品户余额 BGW_REG_ZAN
	private Double depRetZanAccBalance; // 赞分期回款户余额 BGW_RETURN_ZAN
	private Double depAuthZanAccBalance; // 赞分期站岗户余额 BGW_AUTH_ZAN
	private Double depAdvanceAccBalance; // 垫付金子账户 DEP_ADVANCE
	private Double depProReg7AccBalance; //七贷产品户余额 BGW_REG_7
	private Double depRet7AccBalance;   //七贷回款户余额 BGW_RETURN_7
	private Double depAuth7AccBalance;  //七贷站岗户余额 BGW_AUTH_7
	private Double depRed7AccBalance; //七贷产品站岗红包  RED_7  
	private Double deprevenue7AccBalance; // 币港湾营收户（七贷产品）DEP_BGW_REVENUE_7
	
	/********************20171108管理台新增赞时贷需求***********************/
	private Double depRevenueZsdAccBalance;	       // 币港湾对赞时贷营收子账户DEP_BGW_REVENUE_ZSD
	private Double depHeadFeeZsdAccBalance;		   // 币港湾对赞时贷砍头息子账户DEP_HEADFEE_ZSD

	/********************20180424管理台财务总账查询（恒丰）–新增借款人余额***********************/
	private Double sumLoanBalance; // 借款人余额

	/********************20180607管理台财务总账查询（恒丰）–新增自由站岗户业务数据***********************/
	private Double depFreeAccBalance; // 自由产品户余额
	private Double depAuthFreeAccBalance; // 自由站岗户余额
	private Double depRedFreeAccBalance; // 自有产品站岗红包

	public Double getDepSysAccBalance() {
		return depSysAccBalance;
	}
	public void setDepSysAccBalance(Double depSysAccBalance) {
		this.depSysAccBalance = depSysAccBalance;
	}
	public Double getDepRedPaktAccBalance() {
		return depRedPaktAccBalance;
	}
	public void setDepRedPaktAccBalance(Double depRedPaktAccBalance) {
		this.depRedPaktAccBalance = depRedPaktAccBalance;
	}
	public Double getDepUserAccBalance() {
		return depUserAccBalance;
	}
	public void setDepUserAccBalance(Double depUserAccBalance) {
		this.depUserAccBalance = depUserAccBalance;
	}
	public Double getDepProRegAccBalance() {
		return depProRegAccBalance;
	}
	public void setDepProRegAccBalance(Double depProRegAccBalance) {
		this.depProRegAccBalance = depProRegAccBalance;
	}
	public Double getDepRetAccBalance() {
		return depRetAccBalance;
	}
	public void setDepRetAccBalance(Double depRetAccBalance) {
		this.depRetAccBalance = depRetAccBalance;
	}
	public Double getDepAuthYunAccBalance() {
		return depAuthYunAccBalance;
	}
	public void setDepAuthYunAccBalance(Double depAuthYunAccBalance) {
		this.depAuthYunAccBalance = depAuthYunAccBalance;
	}
	public Double getDepRedAccBalance() {
		return depRedAccBalance;
	}
	public void setDepRedAccBalance(Double depRedAccBalance) {
		this.depRedAccBalance = depRedAccBalance;
	}
	public Double getDepHeadFeeAccBalance() {
		return depHeadFeeAccBalance;
	}
	public void setDepHeadFeeAccBalance(Double depHeadFeeAccBalance) {
		this.depHeadFeeAccBalance = depHeadFeeAccBalance;
	}
	public Double getDepOtherFeeAccBalance() {
		return depOtherFeeAccBalance;
	}
	public void setDepOtherFeeAccBalance(Double depOtherFeeAccBalance) {
		this.depOtherFeeAccBalance = depOtherFeeAccBalance;
	}
	public Double getDepRevenueZanAccBalance() {
		return depRevenueZanAccBalance;
	}
	public void setDepRevenueZanAccBalance(Double depRevenueZanAccBalance) {
		this.depRevenueZanAccBalance = depRevenueZanAccBalance;
	}
	public Double getDeprevenueYunAccBalance() {
		return deprevenueYunAccBalance;
	}
	public void setDeprevenueYunAccBalance(Double deprevenueYunAccBalance) {
		this.deprevenueYunAccBalance = deprevenueYunAccBalance;
	}
	public Double getDepRegZanAccBalance() {
		return depRegZanAccBalance;
	}
	public void setDepRegZanAccBalance(Double depRegZanAccBalance) {
		this.depRegZanAccBalance = depRegZanAccBalance;
	}
	public Double getDepRetZanAccBalance() {
		return depRetZanAccBalance;
	}
	public void setDepRetZanAccBalance(Double depRetZanAccBalance) {
		this.depRetZanAccBalance = depRetZanAccBalance;
	}
	public Double getDepAuthZanAccBalance() {
		return depAuthZanAccBalance;
	}
	public void setDepAuthZanAccBalance(Double depAuthZanAccBalance) {
		this.depAuthZanAccBalance = depAuthZanAccBalance;
	}
	public Double getDepAdvanceAccBalance() {
		return depAdvanceAccBalance;
	}
	public void setDepAdvanceAccBalance(Double depAdvanceAccBalance) {
		this.depAdvanceAccBalance = depAdvanceAccBalance;
	}
	public Double getDepProReg7AccBalance() {
		return depProReg7AccBalance;
	}
	public void setDepProReg7AccBalance(Double depProReg7AccBalance) {
		this.depProReg7AccBalance = depProReg7AccBalance;
	}
	public Double getDepRet7AccBalance() {
		return depRet7AccBalance;
	}
	public void setDepRet7AccBalance(Double depRet7AccBalance) {
		this.depRet7AccBalance = depRet7AccBalance;
	}
	public Double getDepAuth7AccBalance() {
		return depAuth7AccBalance;
	}
	public void setDepAuth7AccBalance(Double depAuth7AccBalance) {
		this.depAuth7AccBalance = depAuth7AccBalance;
	}
	public Double getDepRed7AccBalance() {
		return depRed7AccBalance;
	}
	public void setDepRed7AccBalance(Double depRed7AccBalance) {
		this.depRed7AccBalance = depRed7AccBalance;
	}
	public Double getDeprevenue7AccBalance() {
		return deprevenue7AccBalance;
	}
	public void setDeprevenue7AccBalance(Double deprevenue7AccBalance) {
		this.deprevenue7AccBalance = deprevenue7AccBalance;
	}
	public Double getDepRevenueZsdAccBalance() {
		return depRevenueZsdAccBalance;
	}
	public void setDepRevenueZsdAccBalance(Double depRevenueZsdAccBalance) {
		this.depRevenueZsdAccBalance = depRevenueZsdAccBalance;
	}
	public Double getDepHeadFeeZsdAccBalance() {
		return depHeadFeeZsdAccBalance;
	}
	public void setDepHeadFeeZsdAccBalance(Double depHeadFeeZsdAccBalance) {
		this.depHeadFeeZsdAccBalance = depHeadFeeZsdAccBalance;
	}

	public Double getSumLoanBalance() {
		return sumLoanBalance;
	}

	public void setSumLoanBalance(Double sumLoanBalance) {
		this.sumLoanBalance = sumLoanBalance;
	}

	public Double getDepFreeAccBalance() {
		return depFreeAccBalance;
	}

	public void setDepFreeAccBalance(Double depFreeAccBalance) {
		this.depFreeAccBalance = depFreeAccBalance;
	}

	public Double getDepAuthFreeAccBalance() {
		return depAuthFreeAccBalance;
	}

	public void setDepAuthFreeAccBalance(Double depAuthFreeAccBalance) {
		this.depAuthFreeAccBalance = depAuthFreeAccBalance;
	}

	public Double getDepRedFreeAccBalance() {
		return depRedFreeAccBalance;
	}

	public void setDepRedFreeAccBalance(Double depRedFreeAccBalance) {
		this.depRedFreeAccBalance = depRedFreeAccBalance;
	}
}
