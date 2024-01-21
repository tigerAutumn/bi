package com.pinting.gateway.business.out.service;

import com.pinting.gateway.hessian.message.dafy.G2BReqMsg_Customer_ReceiveMoney;
import com.pinting.gateway.hessian.message.dafy.G2BReqMsg_DafyBankCard_CardBindResult;
import com.pinting.gateway.hessian.message.dafy.G2BReqMsg_DafyLoan_ActiveRepayConfirm;
import com.pinting.gateway.hessian.message.dafy.G2BReqMsg_DafyLoan_ActiveRepayPre;
import com.pinting.gateway.hessian.message.dafy.G2BReqMsg_DafyLoan_ActiveRepaySmsCodeRepeat;
import com.pinting.gateway.hessian.message.dafy.G2BReqMsg_DafyLoan_AgreementNotice;
import com.pinting.gateway.hessian.message.dafy.G2BReqMsg_DafyLoan_ApplyLoan;
import com.pinting.gateway.hessian.message.dafy.G2BReqMsg_DafyLoan_CutRepayConfirm;
import com.pinting.gateway.hessian.message.dafy.G2BReqMsg_DafyLoan_DailyAvailableAmountLimit;
import com.pinting.gateway.hessian.message.dafy.G2BReqMsg_DafyLoan_LateRepayNotice;
import com.pinting.gateway.hessian.message.dafy.G2BReqMsg_DafyLoan_PushBill;
import com.pinting.gateway.hessian.message.dafy.G2BReqMsg_DafyLoan_QueryLoanResult;
import com.pinting.gateway.hessian.message.dafy.G2BReqMsg_DafyLoan_QuerySignResult;
import com.pinting.gateway.hessian.message.dafy.G2BReqMsg_DafyLoan_FillFinishNotice;
import com.pinting.gateway.hessian.message.dafy.G2BReqMsg_DafyLoan_QueryRepayResult;
import com.pinting.gateway.hessian.message.dafy.G2BReqMsg_DafyPayment_BuyProductResult;
import com.pinting.gateway.hessian.message.dafy.G2BReqMsg_DafyPayment_SysBuyProductNotice;
import com.pinting.gateway.hessian.message.dafy.G2BReqMsg_DafyPayment_SysReturnMoneyNotice;
import com.pinting.gateway.hessian.message.dafy.G2BReqMsg_DafyRegister_RealCertificateResult;
import com.pinting.gateway.hessian.message.dafy.G2BReqMsg_Withdraw_CustomerWithdrawCheck;
import com.pinting.gateway.hessian.message.dafy.G2BReqMsg_Withdraw_CustomerWithdrawResult;
import com.pinting.gateway.hessian.message.dafy.G2BReqMsg_Withdraw_SysWithdrawResult;
import com.pinting.gateway.hessian.message.dafy.G2BResMsg_Customer_ReceiveMoney;
import com.pinting.gateway.hessian.message.dafy.G2BResMsg_DafyBankCard_CardBindResult;
import com.pinting.gateway.hessian.message.dafy.G2BResMsg_DafyLoan_ActiveRepayConfirm;
import com.pinting.gateway.hessian.message.dafy.G2BResMsg_DafyLoan_ActiveRepayPre;
import com.pinting.gateway.hessian.message.dafy.G2BResMsg_DafyLoan_ActiveRepaySmsCodeRepeat;
import com.pinting.gateway.hessian.message.dafy.G2BResMsg_DafyLoan_AgreementNotice;
import com.pinting.gateway.hessian.message.dafy.G2BResMsg_DafyLoan_ApplyLoan;
import com.pinting.gateway.hessian.message.dafy.G2BResMsg_DafyLoan_CutRepayConfirm;
import com.pinting.gateway.hessian.message.dafy.G2BResMsg_DafyLoan_DailyAvailableAmountLimit;
import com.pinting.gateway.hessian.message.dafy.G2BResMsg_DafyLoan_LateRepayNotice;
import com.pinting.gateway.hessian.message.dafy.G2BResMsg_DafyLoan_PushBill;
import com.pinting.gateway.hessian.message.dafy.G2BResMsg_DafyLoan_QueryLoanResult;
import com.pinting.gateway.hessian.message.dafy.G2BResMsg_DafyLoan_QuerySignResult;
import com.pinting.gateway.hessian.message.dafy.G2BResMsg_DafyLoan_FillFinishNotice;
import com.pinting.gateway.hessian.message.dafy.G2BResMsg_DafyLoan_QueryRepayResult;
import com.pinting.gateway.hessian.message.dafy.G2BResMsg_DafyPayment_BuyProductResult;
import com.pinting.gateway.hessian.message.dafy.G2BResMsg_DafyPayment_SysBuyProductNotice;
import com.pinting.gateway.hessian.message.dafy.G2BResMsg_DafyPayment_SysReturnMoneyNotice;
import com.pinting.gateway.hessian.message.dafy.G2BResMsg_DafyRegister_RealCertificateResult;
import com.pinting.gateway.hessian.message.dafy.G2BResMsg_Withdraw_CustomerWithdrawCheck;
import com.pinting.gateway.hessian.message.dafy.G2BResMsg_Withdraw_CustomerWithdrawResult;
import com.pinting.gateway.hessian.message.dafy.G2BResMsg_Withdraw_SysWithdrawResult;
import com.pinting.gateway.hessian.message.pay19.G2BReqMsg_Pay19AcctTrans_AcctTransResultNotice;
import com.pinting.gateway.hessian.message.pay19.G2BReqMsg_Pay19NewCounter_NewCounterResultNotice;
import com.pinting.gateway.hessian.message.pay19.G2BReqMsg_Pay19Pay4Another_Pay4AnotherResultNotice;
import com.pinting.gateway.hessian.message.pay19.G2BReqMsg_Pay19QuickPay_PayResultNotice;
import com.pinting.gateway.hessian.message.pay19.G2BResMsg_Pay19AcctTrans_AcctTransResultNotice;
import com.pinting.gateway.hessian.message.pay19.G2BResMsg_Pay19NewCounter_NewCounterResultNotice;
import com.pinting.gateway.hessian.message.pay19.G2BResMsg_Pay19Pay4Another_Pay4AnotherResultNotice;
import com.pinting.gateway.hessian.message.pay19.G2BResMsg_Pay19QuickPay_PayResultNotice;
import com.pinting.gateway.hessian.message.reapal.G2BReqMsg_ReapalQuickPay_ReapalNotify;
import com.pinting.gateway.hessian.message.reapal.G2BResMsg_ReapalQuickPay_ReapalNotify;
import com.pinting.gateway.hessian.message.xicai.G2BReqMsg_Xicai_GetP2P;
import com.pinting.gateway.hessian.message.xicai.G2BReqMsg_Xicai_InvestCount;
import com.pinting.gateway.hessian.message.xicai.G2BReqMsg_Xicai_UserCount;
import com.pinting.gateway.hessian.message.xicai.G2BResMsg_Xicai_GetP2P;
import com.pinting.gateway.hessian.message.xicai.G2BResMsg_Xicai_InvestCount;
import com.pinting.gateway.hessian.message.xicai.G2BResMsg_Xicai_UserCount;

/**
 * 结果通知接口（达飞发起通知，由gateway再通知business）
 * 
 * @Project: gateway
 * @Title: CardBindResultNoticeService.java
 * @author dingpf
 * @date 2015-2-10 下午7:01:18
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public interface SendBusinessService {

	/**
	 * 向business发起卡购买结果通知 请求
	 * @param req 请求对象
	 * @return resp 响应回gateway
	 */
	public G2BResMsg_DafyPayment_BuyProductResult sendBsBuyProductResult(
			G2BReqMsg_DafyPayment_BuyProductResult req);
	
	/**
	 * 向business发起卡绑定结果通知 请求
	 * @param req 请求对象
	 * @return resp 响应回gateway
	 */
	public G2BResMsg_DafyBankCard_CardBindResult sendBsCardBindResult(
			G2BReqMsg_DafyBankCard_CardBindResult req);
	/**
	 * 向business发起客户达飞实名认证结果通知 请求
	 * @param req 请求对象
	 * @return resp 响应回gateway
	 */
	public G2BResMsg_DafyRegister_RealCertificateResult sendBsRegisterResult(
			G2BReqMsg_DafyRegister_RealCertificateResult req);
	
	/**
	 * 向business发起客户回款通知请求
	 * @param req 请求对象
	 * @return resp 相应回gateway
	 */
	public G2BResMsg_Customer_ReceiveMoney sendBsReceiveMoneyResult(G2BReqMsg_Customer_ReceiveMoney req);

	/**
	 * 向business发起系统提现通知请求
	 * @param req 请求对象
	 * @return resp 响应回gateway
	 */
	public G2BResMsg_Withdraw_SysWithdrawResult sendBsSysWithdraw(
			G2BReqMsg_Withdraw_SysWithdrawResult req);

	/**
	 * 向business发起用户提现通知
	 * @param req 请求对象
	 * @return resp 响应回gateway
	 */
	public G2BResMsg_Withdraw_CustomerWithdrawResult sendBsCustomerWithdraw(
			G2BReqMsg_Withdraw_CustomerWithdrawResult req);
	
	
	/**
	 * 向business发起用户提现验证
	 * @param req 请求对象
	 * @return resp 响应回gateway
	 */
	public G2BResMsg_Withdraw_CustomerWithdrawCheck sendBsCustomerWithdrawCheck(
			G2BReqMsg_Withdraw_CustomerWithdrawCheck req);
	
	/**
	 * 向business发起快捷支付结果通知
	 * @param req 请求对象
	 * @return resp 响应回gateway
	 */
	public G2BResMsg_Pay19QuickPay_PayResultNotice sendPayResultNotice(
			G2BReqMsg_Pay19QuickPay_PayResultNotice req);
	
	/**
	 * 向business发起代付结果通知
	 * @param req 请求对象
	 * @return resp 响应回gateway
	 */
	public G2BResMsg_Pay19Pay4Another_Pay4AnotherResultNotice sendPay4AnotherResultNotice(
			G2BReqMsg_Pay19Pay4Another_Pay4AnotherResultNotice req);
	
	/**
	 * 向business发起网银结果通知
	 * @param req 请求对象
	 * @return resp 响应回gateway
	 */
	public G2BResMsg_Pay19NewCounter_NewCounterResultNotice sendNewCounterResultNotice(
			G2BReqMsg_Pay19NewCounter_NewCounterResultNotice req);
	
	/**
	 * 向business发起转账结果通知
	 * @param req 请求对象
	 * @return resp 响应回gateway
	 */
	public G2BResMsg_Pay19AcctTrans_AcctTransResultNotice sendAcctTransResultNotice(
			G2BReqMsg_Pay19AcctTrans_AcctTransResultNotice req);
	
	/**
	 * 向business发起系统理财购买结果通知
	 * @param req 请求对象
	 * @return resp 响应回gateway
	 */
	public G2BResMsg_DafyPayment_SysBuyProductNotice sendSysBuyProductNotice(
			G2BReqMsg_DafyPayment_SysBuyProductNotice req);
	
	/**
	 * 向business发起系统回款通知
	 * @param req 请求对象
	 * @return resp 响应回gateway
	 */
	public G2BResMsg_DafyPayment_SysReturnMoneyNotice sendSysReturnMoneyNotice(
			G2BReqMsg_DafyPayment_SysReturnMoneyNotice req);
	
	/**
	 * 
	 * @Title: sendReapalQuickPayNotify 
	 * @Description: 向business发起融宝快捷支付异步通知
	 * @return
	 * @throws
	 */
	public G2BResMsg_ReapalQuickPay_ReapalNotify sendReapalQuickPayNotify(G2BReqMsg_ReapalQuickPay_ReapalNotify req);
	
	/**
	 * 
	 * @Title: sendXicaiGetP2P 
	 * @Description: 向business获得产品信息
	 * @param req
	 * @return
	 * @throws
	 */
	public G2BResMsg_Xicai_GetP2P sendXicaiGetP2P(G2BReqMsg_Xicai_GetP2P req);
	
	/**
	 * 
	 * @Title: sendXicaiUserCount 
	 * @Description: 向business获得用户统计信息
	 * @param req
	 * @return
	 * @throws
	 */
	public G2BResMsg_Xicai_UserCount sendXicaiUserCount(G2BReqMsg_Xicai_UserCount req);
	
	/**
	 * 
	 * @Title: sendXicaiInvestCount 
	 * @Description: 向business获得投资统计信息
	 * @param req
	 * @return
	 * @throws
	 */
	public G2BResMsg_Xicai_InvestCount sendXicaiInvestCount(G2BReqMsg_Xicai_InvestCount req);
	/**
	 * @Title: sendBsDailyAvailableAmountLimit 
	 * @Description: 向business获得每日可借额度信息
	 * @param req G2BReqMsg_DafyLoan_DailyAvailableAmountLimit
	 * @return G2BResMsg_DafyLoan_DailyAvailableAmountLimit
	 */
	public G2BResMsg_DafyLoan_DailyAvailableAmountLimit sendBsDailyAvailableAmountLimit(
			G2BReqMsg_DafyLoan_DailyAvailableAmountLimit req);
	/**
	 * @Title: applyLoan 
	 * @Description: 向business请求放款
	 * @param req G2BReqMsg_DafyLoan_ApplyLoan
	 * @return G2BResMsg_DafyLoan_ApplyLoan
	 */
	public G2BResMsg_DafyLoan_ApplyLoan applyLoan(G2BReqMsg_DafyLoan_ApplyLoan req);
	
	/**
	 * @Title: fillFinishNotic 
	 * @Description: 向business通知补账完成
	 * @param req
	 * @return
	 */
	public G2BResMsg_DafyLoan_FillFinishNotice fillFinishNotic(G2BReqMsg_DafyLoan_FillFinishNotice req);

	/**
	 * @Title: queryLoanResult 
	 * @Description: 向business请求查询放款结果
	 * @param req G2BReqMsg_DafyLoan_QueryLoanResult
	 * @return G2BResMsg_DafyLoan_QueryLoanResult
	 */
	public G2BResMsg_DafyLoan_QueryLoanResult queryLoanResult(
			G2BReqMsg_DafyLoan_QueryLoanResult req);
	
	/**
	 * @Title: pushBill 
	 * @Description: 向business请求查询放款结果
	 * @param req G2BReqMsg_DafyLoan_PushBill
	 * @return G2BResMsg_DafyLoan_PushBill
	 */
	public G2BResMsg_DafyLoan_PushBill pushBill(G2BReqMsg_DafyLoan_PushBill req);
	
	/**
	 * @Title: querySignResult 
	 * @Description: 向business请求查询签章结果信息
	 * @param req G2BReqMsg_DafyLoan_QuerySignResult
	 * @return G2BResMsg_DafyLoan_QuerySignResult
	 */
	public G2BResMsg_DafyLoan_QuerySignResult querySignResult(
			G2BReqMsg_DafyLoan_QuerySignResult req);

	/**
	 * @Title: activeRepayPre 
	 * @Description: 向business请求主动还款预下单
	 * @param req G2BReqMsg_DafyLoan_ActiveRepayPre
	 * @return G2BResMsg_DafyLoan_ActiveRepayPre
	 */
	public G2BResMsg_DafyLoan_ActiveRepayPre activeRepayPre(
			G2BReqMsg_DafyLoan_ActiveRepayPre req);
	
	/**
	 * @Title: activeRepayConfirm 
	 * @Description: 向business请求主动还款正式下单
	 * @param req G2BReqMsg_DafyLoan_ActiveRepayConfirm
	 * @return G2BResMsg_DafyLoan_ActiveRepayConfirm
	 */
	public G2BResMsg_DafyLoan_ActiveRepayConfirm activeRepayConfirm(G2BReqMsg_DafyLoan_ActiveRepayConfirm req);


	/**
	 * @Title: lateRepayNotice
	 * @Description:代偿通知:云贷代偿划拨完成后，需通知币港湾代偿信息及结果
	 * @param req
	 * @return
	 */
	public G2BResMsg_DafyLoan_LateRepayNotice lateRepayNotice(G2BReqMsg_DafyLoan_LateRepayNotice req);

	/**
	 * @Title: queryRepayResult
	 * @Description:还款结果查询
	 * @param req
	 * @return
	 */
	public G2BResMsg_DafyLoan_QueryRepayResult queryRepayResult(G2BReqMsg_DafyLoan_QueryRepayResult req);

	/**
	 * @Title: cutRepayConfirm
	 * @Description:代扣还款
	 * @param req
	 * @return
	 */
	public G2BResMsg_DafyLoan_CutRepayConfirm cutRepayConfirm(G2BReqMsg_DafyLoan_CutRepayConfirm req);

	/**
	 * 协议下载地址查询
	 * @param req
	 * @return
	 */
	public G2BResMsg_DafyLoan_AgreementNotice agreementNotice(G2BReqMsg_DafyLoan_AgreementNotice req);
	/**
	 * 还款预下单重发验证码短信
	 * @param req
	 * @return
	 */
	public G2BResMsg_DafyLoan_ActiveRepaySmsCodeRepeat activeRepaySmsCodeRepeat(
			G2BReqMsg_DafyLoan_ActiveRepaySmsCodeRepeat req);
}
