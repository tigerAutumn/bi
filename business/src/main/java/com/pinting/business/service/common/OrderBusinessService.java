package com.pinting.business.service.common;

import com.pinting.business.model.dto.OrderResultInfo;

/**
 * 涉及订单的业务处理单元
 * @Project: business
 * @Title: OrderBusinessService.java
 * @author Zhou Changzai
 * @date 2016-9-2下午3:04:44
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public interface OrderBusinessService {


	public static enum BusinessType{
	    QUICKPAY_TOPUP("QUICKPAY_TOPUP","理财用户快捷充值"),
		EBANK_TOPUP("EBANK_TOPUP","理财用户网银充值"),
	    WITHDRAW("WITHDRAW","理财人提现"),
	    REG_RETURN("REG_RETURN","普通理财产品回款到卡"),
	    REG_D_RETURN("REG_D_RETURN","分期理财产品回款到卡"),
		REG_TRANS("REG_TRANS","理财产品系统转账"),
		REVENUE_TRANS("REVENUE_TRANS","营收结算系统转账"),
	    LOAN("LOAN","借款人借款"),
	    REPAY("REPAY","借款人还款"),
		MARKET_PAY("MARKET_PAY","合作方营销代付"),
		WITHDRAW_2_DEP_REPAY_CARD("WITHDRAW_2_DEP_REPAY_CARD","提现到存管还款专户"),
		YUN_REVENUE_TRANS("YUN_REVENUE_TRANS","云贷营收结算系统转账"),
		YUN_SELF_LOAN("YUN_SELF_LOAN","云贷借款人借款"),
		YUN_SELF_REPAY("YUN_SELF_REPAY","云贷借款人还款"),
		TRANS_TYPE_DF_2_BORROWER("DF_2_BORROWER","代付到借款人"),
		CUT_REPAY_2_BORROWER("CUT_REPAY_2_BORROWER","批量代扣还款到借款人"),

		ZSD_REPAY("ZSD_REPAY","赞时贷借款人还款"),
		ZSD_LOAN("ZSD_LOAN","云贷借款人借款"),
		SEVEN_SELF_LOAN("SEVEN_SELF_LOAN","云贷借款人借款"),
		SEVEN_DAI_SELF_REPAY("SEVEN_DAI_SELF_REPAY", "7贷借款人还款"),
		SEVEN_DAI_REVENUE_TRANS("SEVEN_DAI_REVENUE_TRANS","7贷营收结算系统转账"),
	    ;
	    
	    private String code;
	    private String description;
	    BusinessType(String code, String description){
	    	this.code = code;
	    	this.description = description;
	    }
	}

	/**
	 * 理财人快捷充值通知
	 * @param orderResultInfo
     */
	public void financerQuickPayTopUp(OrderResultInfo orderResultInfo);
	/**
	 * 理财人网银充值通知
	 * @param orderResultInfo
	 */
	public void financerEBankTopUp(OrderResultInfo orderResultInfo);
	/**
	 * 理财人提现通知
	 * @param orderResultInfo
	 */
	public void financerWithdraw(OrderResultInfo orderResultInfo);
	/**
	 * 理财人普通产品回款通知
	 * @param orderResultInfo
	 */
	public void financerRegProductReturn(OrderResultInfo orderResultInfo);
	/**
	 * 理财人分期产品回款通知
	 * @param orderResultInfo
	 */
	public void financerRegDProductReturn(OrderResultInfo orderResultInfo);
	/**
	 * 借款人借款通知
	 * @param orderResultInfo
	 */
	public void loanerLoan(OrderResultInfo orderResultInfo);
	/**
	 * 借款人还款通知
	 * @param orderResultInfo
	 */
	public void loanerRepay(OrderResultInfo orderResultInfo);
	/**
	 * 系统产品购买转账
	 * @param orderResultInfo
	 */
	public void sysRegTrans(OrderResultInfo orderResultInfo);
	/**
	 * 系统营收结算转账
	 * @param orderResultInfo
	 */
	public void sysRevenueTrans(OrderResultInfo orderResultInfo);
	/**
	 * 合作方营销代付
	 * @param orderResultInfo
	 */
	public void partnerMarketingTrans(OrderResultInfo orderResultInfo);
	/**
	 * 提现到存管还款专户
	 * @param orderResultInfo
	 */
	public void withdraw2DepRepayCard(OrderResultInfo orderResultInfo);

	/**
	 * 理财人充值通知（存管）
	 * @param orderResultInfo
	 */
	public void financeHFTopUp(OrderResultInfo orderResultInfo);

	/**
	 * 云贷借款人还款通知
	 * @param orderResultInfo
	 */
	public void repayResult(OrderResultInfo orderResultInfo);

	/**
	 * 理财人提现通知（存管）
	 * @param orderResultInfo
     */
	void financeHFWithdraw(OrderResultInfo orderResultInfo);

	/**
	 * 云贷借款人出账通知失败，标的重发，借款人代付提现
	 * @param orderResultInfo
     */
	void borrowerDFWithdraw(OrderResultInfo orderResultInfo);

	/**
	 * 云贷借款人出账通知
	 * @param orderResultInfo
     */
	void outOfAccountResultAccept(OrderResultInfo orderResultInfo);

	/**
	 * 赞分期借款人出账通知
	 * @param orderResultInfo
	 */
	void outOfAccountResultAcceptZan(OrderResultInfo orderResultInfo);

	/**
	 * 云贷每日营收结算转账
	 * @param orderResultInfo
	 */
	void revenueSettle(OrderResultInfo orderResultInfo);

	/**
	 * 7贷每日营收结算转账
	 * @param orderResultInfo
     */
	void sevenDaiRevenueSettle(OrderResultInfo orderResultInfo);

	/**
	 * 批量代扣还款到借款人
	 * @param orderResultInfo
	 */
	void cutRepayToBorrower(OrderResultInfo orderResultInfo);
}


