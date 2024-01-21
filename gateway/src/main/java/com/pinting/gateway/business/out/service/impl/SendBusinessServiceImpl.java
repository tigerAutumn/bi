package com.pinting.gateway.business.out.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.pinting.core.hessian.service.HessianService;
import com.pinting.gateway.business.out.service.SendBusinessService;
import com.pinting.gateway.hessian.message.dafy.G2BReqMsg_Customer_ReceiveMoney;
import com.pinting.gateway.hessian.message.dafy.G2BReqMsg_DafyBankCard_CardBindResult;
import com.pinting.gateway.hessian.message.dafy.G2BReqMsg_DafyLoan_ActiveRepayConfirm;
import com.pinting.gateway.hessian.message.dafy.G2BReqMsg_DafyLoan_ActiveRepayPre;
import com.pinting.gateway.hessian.message.dafy.G2BReqMsg_DafyLoan_ActiveRepaySmsCodeRepeat;
import com.pinting.gateway.hessian.message.dafy.G2BReqMsg_DafyLoan_AgreementNotice;
import com.pinting.gateway.hessian.message.dafy.G2BReqMsg_DafyLoan_ApplyLoan;
import com.pinting.gateway.hessian.message.dafy.G2BReqMsg_DafyLoan_CutRepayConfirm;
import com.pinting.gateway.hessian.message.dafy.G2BReqMsg_DafyLoan_DailyAvailableAmountLimit;
import com.pinting.gateway.hessian.message.dafy.G2BReqMsg_DafyLoan_FillFinishNotice;
import com.pinting.gateway.hessian.message.dafy.G2BReqMsg_DafyLoan_LateRepayNotice;
import com.pinting.gateway.hessian.message.dafy.G2BReqMsg_DafyLoan_PushBill;
import com.pinting.gateway.hessian.message.dafy.G2BReqMsg_DafyLoan_QueryLoanResult;
import com.pinting.gateway.hessian.message.dafy.G2BReqMsg_DafyLoan_QueryRepayResult;
import com.pinting.gateway.hessian.message.dafy.G2BReqMsg_DafyLoan_QuerySignResult;
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
import com.pinting.gateway.hessian.message.dafy.G2BResMsg_DafyLoan_FillFinishNotice;
import com.pinting.gateway.hessian.message.dafy.G2BResMsg_DafyLoan_LateRepayNotice;
import com.pinting.gateway.hessian.message.dafy.G2BResMsg_DafyLoan_PushBill;
import com.pinting.gateway.hessian.message.dafy.G2BResMsg_DafyLoan_QueryLoanResult;
import com.pinting.gateway.hessian.message.dafy.G2BResMsg_DafyLoan_QueryRepayResult;
import com.pinting.gateway.hessian.message.dafy.G2BResMsg_DafyLoan_QuerySignResult;
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

@Service
public class SendBusinessServiceImpl implements SendBusinessService {

	@Autowired
	@Qualifier("gatewayDafyService")
	private HessianService gatewayDafyService;
	@Autowired
	@Qualifier("gatewayPay19Service")
	private HessianService gatewayPay19Service;
	@Autowired
	@Qualifier("gatewayReapalService")
	private HessianService gatewayReapalService;
	@Autowired
	@Qualifier("gatewayCsaiService")
	private HessianService gatewayCsaiService;


	@Override
	public G2BResMsg_DafyBankCard_CardBindResult sendBsCardBindResult(
			G2BReqMsg_DafyBankCard_CardBindResult req) {

		G2BResMsg_DafyBankCard_CardBindResult res = (G2BResMsg_DafyBankCard_CardBindResult) gatewayDafyService
				.handleMsg(req);

		return res;
	}

	@Override
	public G2BResMsg_DafyRegister_RealCertificateResult sendBsRegisterResult(
			G2BReqMsg_DafyRegister_RealCertificateResult req) {
		G2BResMsg_DafyRegister_RealCertificateResult res = (G2BResMsg_DafyRegister_RealCertificateResult) gatewayDafyService
				.handleMsg(req);
		return res;
	}

	@Override
	public G2BResMsg_DafyPayment_BuyProductResult sendBsBuyProductResult(
			G2BReqMsg_DafyPayment_BuyProductResult req) {
		G2BResMsg_DafyPayment_BuyProductResult res = (G2BResMsg_DafyPayment_BuyProductResult) gatewayDafyService
				.handleMsg(req);
		return res;
	}

	@Override
	public G2BResMsg_Customer_ReceiveMoney sendBsReceiveMoneyResult(G2BReqMsg_Customer_ReceiveMoney req) {
		G2BResMsg_Customer_ReceiveMoney res = (G2BResMsg_Customer_ReceiveMoney) gatewayDafyService
				.handleMsg(req);
		return res;

	}
	
	@Override
	public G2BResMsg_Withdraw_SysWithdrawResult sendBsSysWithdraw(
			G2BReqMsg_Withdraw_SysWithdrawResult req) {
		G2BResMsg_Withdraw_SysWithdrawResult res = (G2BResMsg_Withdraw_SysWithdrawResult) gatewayDafyService.handleMsg(req);
		return res;
	}

	@Override
	public G2BResMsg_Withdraw_CustomerWithdrawResult sendBsCustomerWithdraw(
			G2BReqMsg_Withdraw_CustomerWithdrawResult req) {

		G2BResMsg_Withdraw_CustomerWithdrawResult res = (G2BResMsg_Withdraw_CustomerWithdrawResult) gatewayDafyService.handleMsg(req);	
		return res;
	}

	@Override
	public G2BResMsg_Withdraw_CustomerWithdrawCheck sendBsCustomerWithdrawCheck(
			G2BReqMsg_Withdraw_CustomerWithdrawCheck req) {
		G2BResMsg_Withdraw_CustomerWithdrawCheck res = (G2BResMsg_Withdraw_CustomerWithdrawCheck) gatewayDafyService.handleMsg(req);	
		return res;
	}

	@Override
	public G2BResMsg_Pay19QuickPay_PayResultNotice sendPayResultNotice(
			G2BReqMsg_Pay19QuickPay_PayResultNotice req) {
		G2BResMsg_Pay19QuickPay_PayResultNotice res = (G2BResMsg_Pay19QuickPay_PayResultNotice) gatewayPay19Service.handleMsg(req);	
		return res;
	}

	@Override
	public G2BResMsg_Pay19Pay4Another_Pay4AnotherResultNotice sendPay4AnotherResultNotice(
			G2BReqMsg_Pay19Pay4Another_Pay4AnotherResultNotice req) {
		G2BResMsg_Pay19Pay4Another_Pay4AnotherResultNotice res = (G2BResMsg_Pay19Pay4Another_Pay4AnotherResultNotice) gatewayPay19Service.handleMsg(req);	
		return res;
	}

	@Override
	public G2BResMsg_Pay19NewCounter_NewCounterResultNotice sendNewCounterResultNotice(
			G2BReqMsg_Pay19NewCounter_NewCounterResultNotice req) {
		G2BResMsg_Pay19NewCounter_NewCounterResultNotice res = (G2BResMsg_Pay19NewCounter_NewCounterResultNotice) gatewayPay19Service.handleMsg(req);	
		return res;
	}

	@Override
	public G2BResMsg_Pay19AcctTrans_AcctTransResultNotice sendAcctTransResultNotice(
			G2BReqMsg_Pay19AcctTrans_AcctTransResultNotice req) {
		G2BResMsg_Pay19AcctTrans_AcctTransResultNotice res = (G2BResMsg_Pay19AcctTrans_AcctTransResultNotice) gatewayPay19Service.handleMsg(req);	
		return res;
	}

	@Override
	public G2BResMsg_DafyPayment_SysBuyProductNotice sendSysBuyProductNotice(
			G2BReqMsg_DafyPayment_SysBuyProductNotice req) {
		G2BResMsg_DafyPayment_SysBuyProductNotice res = (G2BResMsg_DafyPayment_SysBuyProductNotice) gatewayDafyService.handleMsg(req);	
		return res;
	}

	@Override
	public G2BResMsg_DafyPayment_SysReturnMoneyNotice sendSysReturnMoneyNotice(
			G2BReqMsg_DafyPayment_SysReturnMoneyNotice req) {
		G2BResMsg_DafyPayment_SysReturnMoneyNotice res = (G2BResMsg_DafyPayment_SysReturnMoneyNotice) gatewayDafyService.handleMsg(req);	
		return res;
	}

	@Override
	public G2BResMsg_ReapalQuickPay_ReapalNotify sendReapalQuickPayNotify(G2BReqMsg_ReapalQuickPay_ReapalNotify req) {
		G2BResMsg_ReapalQuickPay_ReapalNotify res = (G2BResMsg_ReapalQuickPay_ReapalNotify) gatewayReapalService.handleMsg(req);
		return res;
	}
	@Override
	public G2BResMsg_Xicai_GetP2P sendXicaiGetP2P(G2BReqMsg_Xicai_GetP2P req) {
		G2BResMsg_Xicai_GetP2P res = (G2BResMsg_Xicai_GetP2P) gatewayCsaiService.handleMsg(req);
		return res;
	}

	@Override
	public G2BResMsg_Xicai_UserCount sendXicaiUserCount(G2BReqMsg_Xicai_UserCount req) {
		G2BResMsg_Xicai_UserCount res = (G2BResMsg_Xicai_UserCount) gatewayCsaiService.handleMsg(req);
		return res;
	}

	@Override
	public G2BResMsg_Xicai_InvestCount sendXicaiInvestCount(G2BReqMsg_Xicai_InvestCount req) {
		G2BResMsg_Xicai_InvestCount res = (G2BResMsg_Xicai_InvestCount) gatewayCsaiService.handleMsg(req);
		return res;
	}

	@Override
	public G2BResMsg_DafyLoan_DailyAvailableAmountLimit sendBsDailyAvailableAmountLimit(
			G2BReqMsg_DafyLoan_DailyAvailableAmountLimit req) {
		G2BResMsg_DafyLoan_DailyAvailableAmountLimit res = (G2BResMsg_DafyLoan_DailyAvailableAmountLimit) gatewayDafyService
				.handleMsg(req);
		return res;
	}

	@Override
	public G2BResMsg_DafyLoan_ApplyLoan applyLoan(
			G2BReqMsg_DafyLoan_ApplyLoan req) {
		G2BResMsg_DafyLoan_ApplyLoan res = (G2BResMsg_DafyLoan_ApplyLoan) gatewayDafyService
				.handleMsg(req);
		return res;
	}

	@Override
	public G2BResMsg_DafyLoan_FillFinishNotice fillFinishNotic(
			G2BReqMsg_DafyLoan_FillFinishNotice req) {
		G2BResMsg_DafyLoan_FillFinishNotice res = (G2BResMsg_DafyLoan_FillFinishNotice)gatewayDafyService
				.handleMsg(req);
		return res;
	}

	@Override
	public G2BResMsg_DafyLoan_QueryLoanResult queryLoanResult(
			G2BReqMsg_DafyLoan_QueryLoanResult req) {
		G2BResMsg_DafyLoan_QueryLoanResult res = (G2BResMsg_DafyLoan_QueryLoanResult) gatewayDafyService
				.handleMsg(req);
		return res;
	}
	
	@Override
	public G2BResMsg_DafyLoan_PushBill pushBill(G2BReqMsg_DafyLoan_PushBill req) {
		G2BResMsg_DafyLoan_PushBill res = (G2BResMsg_DafyLoan_PushBill) gatewayDafyService
				.handleMsg(req);
		return res;
	}

	@Override
	public G2BResMsg_DafyLoan_QuerySignResult querySignResult(
			G2BReqMsg_DafyLoan_QuerySignResult req) {
		G2BResMsg_DafyLoan_QuerySignResult res = (G2BResMsg_DafyLoan_QuerySignResult) gatewayDafyService
				.handleMsg(req);
		return res;
	}

	@Override
	public G2BResMsg_DafyLoan_ActiveRepayPre activeRepayPre(
			G2BReqMsg_DafyLoan_ActiveRepayPre req) {
		G2BResMsg_DafyLoan_ActiveRepayPre res = (G2BResMsg_DafyLoan_ActiveRepayPre) gatewayDafyService
				.handleMsg(req);
		return res;
	}
	
	
	@Override
	public G2BResMsg_DafyLoan_ActiveRepayConfirm activeRepayConfirm(
			G2BReqMsg_DafyLoan_ActiveRepayConfirm req) {
		G2BResMsg_DafyLoan_ActiveRepayConfirm res = (G2BResMsg_DafyLoan_ActiveRepayConfirm) gatewayDafyService
				.handleMsg(req);
		return res;
	}

	@Override
	public G2BResMsg_DafyLoan_LateRepayNotice lateRepayNotice(
			G2BReqMsg_DafyLoan_LateRepayNotice req) {
		G2BResMsg_DafyLoan_LateRepayNotice res = (G2BResMsg_DafyLoan_LateRepayNotice)gatewayDafyService
				.handleMsg(req);
		return res;
	}

	@Override
	public G2BResMsg_DafyLoan_QueryRepayResult queryRepayResult(
			G2BReqMsg_DafyLoan_QueryRepayResult req) {
		G2BResMsg_DafyLoan_QueryRepayResult res = (G2BResMsg_DafyLoan_QueryRepayResult)gatewayDafyService
				.handleMsg(req);
		return res;
	}

	@Override
	public G2BResMsg_DafyLoan_CutRepayConfirm cutRepayConfirm(
			G2BReqMsg_DafyLoan_CutRepayConfirm req) {
		G2BResMsg_DafyLoan_CutRepayConfirm res = (G2BResMsg_DafyLoan_CutRepayConfirm)gatewayDafyService
				.handleMsg(req);
		return res;
	}

	@Override
	public G2BResMsg_DafyLoan_AgreementNotice agreementNotice(
			G2BReqMsg_DafyLoan_AgreementNotice req) {
		G2BResMsg_DafyLoan_AgreementNotice res = (G2BResMsg_DafyLoan_AgreementNotice)gatewayDafyService
				.handleMsg(req);
		return res;
	}

	@Override
	public G2BResMsg_DafyLoan_ActiveRepaySmsCodeRepeat activeRepaySmsCodeRepeat(
			G2BReqMsg_DafyLoan_ActiveRepaySmsCodeRepeat req) {
		G2BResMsg_DafyLoan_ActiveRepaySmsCodeRepeat res = (G2BResMsg_DafyLoan_ActiveRepaySmsCodeRepeat)gatewayDafyService
				.handleMsg(req);
		return res;
	}
}
