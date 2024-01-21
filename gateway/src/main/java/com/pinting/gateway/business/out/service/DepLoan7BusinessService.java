package com.pinting.gateway.business.out.service;

import com.pinting.gateway.hessian.message.loan7.G2BReqMsg_DepLoan7_ActiveRepayConfirm;
import com.pinting.gateway.hessian.message.loan7.G2BReqMsg_DepLoan7_ActiveRepayPre;
import com.pinting.gateway.hessian.message.loan7.G2BReqMsg_DepLoan7_ActiveRepaySmsCodeRepeat;
import com.pinting.gateway.hessian.message.loan7.G2BReqMsg_DepLoan7_AgreementNotice;
import com.pinting.gateway.hessian.message.loan7.G2BReqMsg_DepLoan7_ApplyLoan;
import com.pinting.gateway.hessian.message.loan7.G2BReqMsg_DepLoan7_CutRepayConfirm;
import com.pinting.gateway.hessian.message.loan7.G2BReqMsg_DepLoan7_DailyAvailableAmountLimit;
import com.pinting.gateway.hessian.message.loan7.G2BReqMsg_DepLoan7_FillFinishNotice;
import com.pinting.gateway.hessian.message.loan7.G2BReqMsg_DepLoan7_LateRepayNotice;
import com.pinting.gateway.hessian.message.loan7.G2BReqMsg_DepLoan7_PushBill;
import com.pinting.gateway.hessian.message.loan7.G2BReqMsg_DepLoan7_QueryLoanResult;
import com.pinting.gateway.hessian.message.loan7.G2BReqMsg_DepLoan7_QueryRepayResult;
import com.pinting.gateway.hessian.message.loan7.G2BReqMsg_DepLoan7_QuerySignResult;
import com.pinting.gateway.hessian.message.loan7.G2BResMsg_DepLoan7_ActiveRepayConfirm;
import com.pinting.gateway.hessian.message.loan7.G2BResMsg_DepLoan7_ActiveRepayPre;
import com.pinting.gateway.hessian.message.loan7.G2BResMsg_DepLoan7_ActiveRepaySmsCodeRepeat;
import com.pinting.gateway.hessian.message.loan7.G2BResMsg_DepLoan7_AgreementNotice;
import com.pinting.gateway.hessian.message.loan7.G2BResMsg_DepLoan7_ApplyLoan;
import com.pinting.gateway.hessian.message.loan7.G2BResMsg_DepLoan7_CutRepayConfirm;
import com.pinting.gateway.hessian.message.loan7.G2BResMsg_DepLoan7_DailyAvailableAmountLimit;
import com.pinting.gateway.hessian.message.loan7.G2BResMsg_DepLoan7_FillFinishNotice;
import com.pinting.gateway.hessian.message.loan7.G2BResMsg_DepLoan7_LateRepayNotice;
import com.pinting.gateway.hessian.message.loan7.G2BResMsg_DepLoan7_PushBill;
import com.pinting.gateway.hessian.message.loan7.G2BResMsg_DepLoan7_QueryLoanResult;
import com.pinting.gateway.hessian.message.loan7.G2BResMsg_DepLoan7_QueryRepayResult;
import com.pinting.gateway.hessian.message.loan7.G2BResMsg_DepLoan7_QuerySignResult;

public interface DepLoan7BusinessService {
	/**
	 * @Title: sendBsDailyAvailableAmountLimit 
	 * @Description: 向business获得每日可借额度信息
	 * @param req G2BReqMsg_DepLoan7_DailyAvailableAmountLimit
	 * @return G2BResMsg_DepLoan7_DailyAvailableAmountLimit
	 */
	public G2BResMsg_DepLoan7_DailyAvailableAmountLimit sendBsDailyAvailableAmountLimit(
			G2BReqMsg_DepLoan7_DailyAvailableAmountLimit req);
	/**
	 * @Title: applyLoan 
	 * @Description: 向business请求放款
	 * @param req G2BReqMsg_DepLoan7_ApplyLoan
	 * @return G2BResMsg_DepLoan7_ApplyLoan
	 */
	public G2BResMsg_DepLoan7_ApplyLoan applyLoan(G2BReqMsg_DepLoan7_ApplyLoan req);
	
	/**
	 * @Title: fillFinishNotic 
	 * @Description: 向business通知补账完成
	 * @param req
	 * @return
	 */
	public G2BResMsg_DepLoan7_FillFinishNotice fillFinishNotic(G2BReqMsg_DepLoan7_FillFinishNotice req);

	/**
	 * @Title: queryLoanResult 
	 * @Description: 向business请求查询放款结果
	 * @param req G2BReqMsg_DepLoan7_QueryLoanResult
	 * @return G2BResMsg_DepLoan7_QueryLoanResult
	 */
	public G2BResMsg_DepLoan7_QueryLoanResult queryLoanResult(
			G2BReqMsg_DepLoan7_QueryLoanResult req);
	
	/**
	 * @Title: pushBill 
	 * @Description: 向business请求查询放款结果
	 * @param req G2BReqMsg_DepLoan7_PushBill
	 * @return G2BResMsg_DepLoan7_PushBill
	 */
	public G2BResMsg_DepLoan7_PushBill pushBill(G2BReqMsg_DepLoan7_PushBill req);
	
	/**
	 * @Title: querySignResult 
	 * @Description: 向business请求查询签章结果信息
	 * @param req G2BReqMsg_DepLoan7_QuerySignResult
	 * @return G2BResMsg_DepLoan7_QuerySignResult
	 */
	public G2BResMsg_DepLoan7_QuerySignResult querySignResult(
			G2BReqMsg_DepLoan7_QuerySignResult req);

	/**
	 * @Title: activeRepayPre 
	 * @Description: 向business请求主动还款预下单
	 * @param req G2BReqMsg_DepLoan7_ActiveRepayPre
	 * @return G2BResMsg_DepLoan7_ActiveRepayPre
	 */
	public G2BResMsg_DepLoan7_ActiveRepayPre activeRepayPre(
			G2BReqMsg_DepLoan7_ActiveRepayPre req);
	
	/**
	 * @Title: activeRepayConfirm 
	 * @Description: 向business请求主动还款正式下单
	 * @param req G2BReqMsg_DepLoan7_ActiveRepayConfirm
	 * @return G2BResMsg_DepLoan7_ActiveRepayConfirm
	 */
	public G2BResMsg_DepLoan7_ActiveRepayConfirm activeRepayConfirm(G2BReqMsg_DepLoan7_ActiveRepayConfirm req);


	/**
	 * @Title: lateRepayNotice
	 * @Description:代偿通知:云贷代偿划拨完成后，需通知币港湾代偿信息及结果
	 * @param req
	 * @return
	 */
	public G2BResMsg_DepLoan7_LateRepayNotice lateRepayNotice(G2BReqMsg_DepLoan7_LateRepayNotice req);

	/**
	 * @Title: queryRepayResult
	 * @Description:还款结果查询
	 * @param req
	 * @return
	 */
	public G2BResMsg_DepLoan7_QueryRepayResult queryRepayResult(G2BReqMsg_DepLoan7_QueryRepayResult req);

	/**
	 * @Title: cutRepayConfirm
	 * @Description:代扣还款
	 * @param req
	 * @return
	 */
	public G2BResMsg_DepLoan7_CutRepayConfirm cutRepayConfirm(G2BReqMsg_DepLoan7_CutRepayConfirm req);

	/**
	 * 协议下载地址查询
	 * @param req
	 * @return
	 */
	public G2BResMsg_DepLoan7_AgreementNotice agreementNotice(G2BReqMsg_DepLoan7_AgreementNotice req);
	/**
	 * 还款预下单重发验证码短信
	 * @param req
	 * @return
	 */
	public G2BResMsg_DepLoan7_ActiveRepaySmsCodeRepeat activeRepaySmsCodeRepeat(
			G2BReqMsg_DepLoan7_ActiveRepaySmsCodeRepeat req);
}
