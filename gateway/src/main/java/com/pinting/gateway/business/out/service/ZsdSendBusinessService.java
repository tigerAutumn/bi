package com.pinting.gateway.business.out.service;

import com.pinting.gateway.hessian.message.zsd.*;

/**
 * 结果通知接口（赞时贷发起通知，由gateway再通知business）
 * @project gateway
 * @title ZsdSendBusinessService.java
 * @author Dragon & cat
 * @date 2017-9-1
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description
 */
public interface ZsdSendBusinessService {
	
	/**
	 * 借款人信息登记
	 * @param req
	 * @return
	 */
	G2BResMsg_ZsdLoanCif_AddLoaner addLoaner(G2BReqMsg_ZsdLoanCif_AddLoaner req);

	/**
	 * 代扣还款
	 * @param req
	 * @return
	 */
	G2BResMsg_ZsdRepay_CutpaymentRepay cutpaymentRepay(G2BReqMsg_ZsdRepay_CutpaymentRepay req);
	/**
	 * 代偿通知
	 * @param req
	 * @return
	 */
	G2BResMsg_ZsdRepay_LateRepayNotice lateRepayNotice(G2BReqMsg_ZsdRepay_LateRepayNotice req);
	
	/**
	 * 借款申请
	 * @param req
	 * @return
	 */
	G2BResMsg_ZsdLoanApply_AddLoan addLoan(G2BReqMsg_ZsdLoanApply_AddLoan req);

	/**
	 * 还款确认
	 * @param req
	 * @return
	 */
	G2BResMsg_ZsdRepay_RepayConfirm repayConfirm(G2BReqMsg_ZsdRepay_RepayConfirm req);

	/**
	 * 还款结果查询
	 * @param req
	 * @return
	 */
	G2BResMsg_ZsdRepay_QueryRepayResult queryResult(G2BReqMsg_ZsdRepay_QueryRepayResult req);

	/**
	 * 坏账处理
	 * @param req
	 * @return
	 */
	G2BResMsg_ZsdRepay_BadDebt badDebt(G2BReqMsg_ZsdRepay_BadDebt req);
	
	/**
	 * 借款结果查询
	 * @param req
	 * @return
	 */
	G2BResMsg_ZsdLoanApply_QueryLoan queryLoanStatus(G2BReqMsg_ZsdLoanApply_QueryLoan req);
	
	/**
	 * 还款预下单
	 * @param req
	 * @return
	 */
	G2BResMsg_ZsdRepay_PreRepay preRepay(G2BReqMsg_ZsdRepay_PreRepay req);
	
	/**
	 * 赞时贷查询币港湾当日可用额度
	 * @param req
	 * @return
     */
	G2BResMsg_ZsdLoan_QueryDailyLimit queryDailyLimit(G2BReqMsg_ZsdLoan_QueryDailyLimit req);

	/**
	 * 查询银行卡限额
	 * @param req
	 * @return
     */
	G2BResMsg_ZsdBankLimit_QueryBankLimit queryBankLimit(G2BReqMsg_ZsdBankLimit_QueryBankLimit req);

}
