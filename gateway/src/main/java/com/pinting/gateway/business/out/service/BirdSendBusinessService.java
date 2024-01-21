package com.pinting.gateway.business.out.service;

import com.pinting.gateway.hessian.message.loan.*;

/**
 * Created by 剑钊 on 2016/8/17.
 * 结果通知接口（蜂鸟发起通知，由gateway再通知business）
 *
 * @Project: gateway
 * @Title: birdService.java
 * @author liujz
 * @date 2016/8/17 上午11:01:18
 * @Copyright: 2016 BiGangWan.com Inc. All rights reserved.
 */
public interface BirdSendBusinessService {
	
	/**
	 * 借款人信息登记
	 * @param req
	 * @return
	 */
	G2BResMsg_LoanCif_AddLoaner addLoaner(G2BReqMsg_LoanCif_AddLoaner req);

	/**
	 * 银行卡限额
	 * @param req
	 * @return
	 */
	G2BResMsg_BankLimit_LimitList getBankLimit(G2BReqMsg_BankLimit_LimitList req);
	
	/**
	 * 预绑卡
	 * @param req
	 * @return
     */
	G2BResMsg_LoanCif_PreBindCard preBindCard(G2BReqMsg_LoanCif_PreBindCard req);

	/**
	 * 确认绑卡
	 * @param req
	 * @return
     */
	G2BResMsg_LoanCif_BindCardConfirm bindCard(G2BReqMsg_LoanCif_BindCardConfirm req);

	/**
	 * 解绑卡
	 * @param req
	 * @return
	 */
	G2BResMsg_LoanCif_UnBindCard unBindCard(G2BReqMsg_LoanCif_UnBindCard req);

	/**
	 * 借款
	 * @param req
	 * @return
     */
	G2BResMsg_Loan_Loan loan(G2BReqMsg_Loan_Loan req);

	/**
	 * 营销代付
	 * @param req
	 * @return
     */
	G2BResMsg_Partner_MarketingTrans marketingTrans(G2BReqMsg_Partner_MarketingTrans req);

	/**
	 * 预还款
	 * @param req
	 * @return
	 */
	G2BResMsg_Repay_PreRepay preRepay(G2BReqMsg_Repay_PreRepay req);

	/**
	 * 代扣还款
	 * @param req
	 * @return
     */
	G2BResMsg_Repay_WithholdingRepay repay(G2BReqMsg_Repay_WithholdingRepay req);

	/**
	 * 还款确认
	 * @param req
	 * @return
	 */
	G2BResMsg_Repay_RepayConfirm repayConfirm(G2BReqMsg_Repay_RepayConfirm req);
	
	/**
	 * 还款结果查询
	 * @param req
	 * @return
	 */
	G2BResMsg_Repay_QueryRepayResult queryResult(G2BReqMsg_Repay_QueryRepayResult req);
	
	
	/**
	 * 蜂鸟查询币港湾当日可用额度
	 * @param req
	 * @return
	 */
	G2BResMsg_Loan_DailyAmount getDailyAmount(G2BReqMsg_Loan_DailyAmount req);

	/**
	 * 查询借款状态
	 * @param req
	 * @return
     */
	G2BResMsg_Loan_QueryLoan queryLoanStatus(G2BReqMsg_Loan_QueryLoan req);


	/**
	 * 查询营销代付状态
	 * @param req
	 * @return
     */
	G2BResMsg_Partner_QueryMarketingTrans queryMarketingTrans(G2BReqMsg_Partner_QueryMarketingTrans req);

	/**
	 * 查询宝付账户余额
	 * @param req
	 * @return
     */
	G2BResMsg_Partner_QueryBalance queryBalance(G2BReqMsg_Partner_QueryBalance req);
	/**
	 * 坏账处理
	 * @param req G2BReqMsg_Repay_BadDebt
	 * @return
	 */
	G2BResMsg_Repay_BadDebt badDebt(G2BReqMsg_Repay_BadDebt req);

}
