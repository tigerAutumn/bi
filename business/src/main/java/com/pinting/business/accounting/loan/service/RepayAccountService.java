package com.pinting.business.accounting.loan.service;

import com.pinting.business.accounting.loan.model.BaseAccount;
import com.pinting.business.accounting.loan.model.ReceiveMoneyInfo;
import com.pinting.business.accounting.loan.model.RepayInfo;

/**
 * 还款相关账务服务
 * 
 * F：理财人；SF：超级理财人；S：系统；B：借款人
 * 
 * @Project: business
 * @Title: RepayAccountService.java
 * @author dingpf
 * @date 2016-8-23 下午4:57:27
 * @Copyright: 2016 BiGangWan.com Inc. All rights reserved.
 */
public interface RepayAccountService {
	
	/**
	 * 正常还款记账
	 * B:LOAN - 本金
	 * S:REG_ZAN - 本金
	 * S:RETURN_ZAN + (THD_REPAY)
	 * S:REVENUE_ZAN + (还款总金额 - 币港湾结算 -保证金)
	 * S:THD_MARGIN_ZAN+ (保证金)
	 * S:THD_REPAY + 协议利率（本+息）
	 * S:THD_BGW_REVENUE_ZAN + （币港湾结算-每笔债权协议利率）为负值
	 * S:FEE + 
	 * @param repayInfo 合作方、借款人借款户编号、发生金额、本次本金、本次理财人利息、手续费、服务费、保证金、计费规则编号 必输
	 */
	void chargeNormalRepay(RepayInfo repayInfo);
	
	/**
	 * 赞分期理财人提前赎回后，赞分期借款人主动还款或提前还款的分账记账
	 * B:LOAN - 本金
	 * S:REVENUE_ZAN + 赞分期营收(还款总金额 - 币港湾结算 -保证金)
	 * S:MARGIN_ZAN+ (保证金)
	 * S:THD_BGW_REVENUE_ZAN_TEMP + 币港湾营收记临时户（币港湾结算）
	 * S:FEE +
	 * @param repayInfo 入参：合作方、借款人借款户编号、本次本金、手续费、赞分期营收、币港湾营收、保证金（需校验，必输）
	 */
	void chargeNormalRepay4ZANNew(RepayInfo repayInfo);
	
	
	/**
	 * 赞分期理财人提前赎回,进行恒丰前分账，系统记账
	 * @author bianyatian
	 * S:REG_ZAN - 本金
	 * S:RETURN_ZAN + (THD_REPAY)
	 * S:THD_REPAY + 协议利率（本+息）
	 * @param repayInfo 入参：合作方、本次本金、协议利率（本+息）
	 */
	void chargeNormalRepay4ZANHF(RepayInfo repayInfo);
	
	/**
	 * 逾期垫付记账
	 * S:REG_ZAN - 本金
	 * S:RETURN_ZAN + (本金+用户利息)
	 * S:REVENUE_ZAN >>> REVENUE
	 * @param repayInfo 合作方、借款人借款户编号、发生金额、本金、服务费 必输
	 */
	void chargeOverdueAdvance(RepayInfo repayInfo);
	
	
	/**
	 * 逾期还款记账（赞分期和赞时贷共用）
	 * B:LOAN - 本金  （仅赞分期）
	 * S:REVENUE_ZAN + (还款总金额 - 币港湾结算 -保证金)
	 * S:THD_MARGIN_ZAN+ (保证金)
	 * S:THD_REPAY + 协议利率（本+息）)
	 * S:FEE +
	 * @param repayInfo 合作方、借款人借款户编号、发生金额、本次本金、本次理财人利息、保证金、手续费 必输
	 */
	void chargeOverdueRepay(RepayInfo repayInfo);
	
	/**
	 * 借款人DEP_JSH-，代偿人DEP_JSH+，系统用户余额户+
	 * @param lnDEPJSHId
	 * @param compensateDEPJSHId
	 * @param planTotal
	 */
	void chargeRepay2CcompensateSuccAct(Integer lnDEPJSHId, Integer compensateDEPJSHId, Double planTotal);
	
	/**
	 * 正常坏账记账
	 * S:REVENUE_ZAN >>> MARGIN_ZAN
	 */
	void chargeNormalBadLoans(BaseAccount baseAccount);
	
	/**
	 * 用户回款到余额记账
	 * F:REG_D - 本金
	 * F:DEP_JSH + (本金+用户利息)
	 * S:BGW_USER + (本金+用户利息)
	 * S:BGW_RETURN_ZAN - (本金+用户利息+币港湾营收)
	 * S:DEP_BGW_REVENUE_ZAN +
	 * @param receiveMoneyInfo 合作方、理财人用户编号、理财人产品户编号、发生金额、本次本金、本次理财人利息、币港湾营收 必输
	 */
	void chargeReceiveMoney2Balance(ReceiveMoneyInfo receiveMoneyInfo);

	/**
	 * 回款到卡记账
	 * S:RETURN_ZAN - (本金+用户利息)
	 * F:REG - 本金
	 * @param receiveMoneyInfo 合作方、理财人用户编号、理财人产品户编号、发生金额、本次本金、本次理财人利息 必输
     */
	void chargeReceiveMoney2Card(ReceiveMoneyInfo receiveMoneyInfo);
}
/**
用户子账户表扩展{
    投资人结算户：一个（余额账户）JSH
    投资人站岗户：多个（站岗账户）AUTH
    投资人产品户1：多个（投资金额固定不变，一次性还款）  REG
    投资人产品户2：多个（投资金额变动，允许多次回款）  REG_D
    投资人奖励金户：JLJ
}
系统子账户表扩展{
    系统结算户：JSH
    系统用户余额户：USER
    云贷产品户：REG_YUN
    云贷回款户：RETURN_YUN
 7贷产品户：REG_7
 7贷回款户：RETURN_7
    系统红包户: REDPACKET
    赞分期站岗户：AUTH_ZAN
    赞分期产品户：REG_ZAN
    赞分期回款户：RETURN_ZAN
    赞分期风险保证金账户：MARGIN_ZAN
    赞分期营收账户：REVENUE_ZAN
    币港湾对赞分期营收账户：REVENUE
    币港湾手续费账户：FEE
    平台备付金账户：（暂无）
    赞分期坏账专用账户：（暂无）
}
借款人子账户表{
    借款人结算户：JSH
    借款人借款户：LOAN（待还账户）多个
}
**/