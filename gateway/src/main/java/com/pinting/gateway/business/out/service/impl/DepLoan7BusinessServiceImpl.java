package com.pinting.gateway.business.out.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.pinting.core.hessian.service.HessianService;
import com.pinting.gateway.business.out.service.DepLoan7BusinessService;
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

@Service
public class DepLoan7BusinessServiceImpl implements DepLoan7BusinessService {
	
	@Autowired
	@Qualifier("gatewayDepLoan7Service")
	private HessianService gatewayDepLoan7Service;
	
	
	@Override
	public G2BResMsg_DepLoan7_DailyAvailableAmountLimit sendBsDailyAvailableAmountLimit(
			G2BReqMsg_DepLoan7_DailyAvailableAmountLimit req) {
		G2BResMsg_DepLoan7_DailyAvailableAmountLimit res = (G2BResMsg_DepLoan7_DailyAvailableAmountLimit) gatewayDepLoan7Service
				.handleMsg(req);
		return res;
	}

	@Override
	public G2BResMsg_DepLoan7_ApplyLoan applyLoan(
			G2BReqMsg_DepLoan7_ApplyLoan req) {
		G2BResMsg_DepLoan7_ApplyLoan res = (G2BResMsg_DepLoan7_ApplyLoan) gatewayDepLoan7Service
				.handleMsg(req);
		return res;
	}

	@Override
	public G2BResMsg_DepLoan7_FillFinishNotice fillFinishNotic(
			G2BReqMsg_DepLoan7_FillFinishNotice req) {
		G2BResMsg_DepLoan7_FillFinishNotice res = (G2BResMsg_DepLoan7_FillFinishNotice)gatewayDepLoan7Service
				.handleMsg(req);
		return res;
	}

	@Override
	public G2BResMsg_DepLoan7_QueryLoanResult queryLoanResult(
			G2BReqMsg_DepLoan7_QueryLoanResult req) {
		G2BResMsg_DepLoan7_QueryLoanResult res = (G2BResMsg_DepLoan7_QueryLoanResult) gatewayDepLoan7Service
				.handleMsg(req);
		return res;
	}
	
	@Override
	public G2BResMsg_DepLoan7_PushBill pushBill(G2BReqMsg_DepLoan7_PushBill req) {
		G2BResMsg_DepLoan7_PushBill res = (G2BResMsg_DepLoan7_PushBill) gatewayDepLoan7Service
				.handleMsg(req);
		return res;
	}

	@Override
	public G2BResMsg_DepLoan7_QuerySignResult querySignResult(
			G2BReqMsg_DepLoan7_QuerySignResult req) {
		G2BResMsg_DepLoan7_QuerySignResult res = (G2BResMsg_DepLoan7_QuerySignResult) gatewayDepLoan7Service
				.handleMsg(req);
		return res;
	}

	@Override
	public G2BResMsg_DepLoan7_ActiveRepayPre activeRepayPre(
			G2BReqMsg_DepLoan7_ActiveRepayPre req) {
		G2BResMsg_DepLoan7_ActiveRepayPre res = (G2BResMsg_DepLoan7_ActiveRepayPre) gatewayDepLoan7Service
				.handleMsg(req);
		return res;
	}
	
	
	@Override
	public G2BResMsg_DepLoan7_ActiveRepayConfirm activeRepayConfirm(
			G2BReqMsg_DepLoan7_ActiveRepayConfirm req) {
		G2BResMsg_DepLoan7_ActiveRepayConfirm res = (G2BResMsg_DepLoan7_ActiveRepayConfirm) gatewayDepLoan7Service
				.handleMsg(req);
		return res;
	}

	@Override
	public G2BResMsg_DepLoan7_LateRepayNotice lateRepayNotice(
			G2BReqMsg_DepLoan7_LateRepayNotice req) {
		G2BResMsg_DepLoan7_LateRepayNotice res = (G2BResMsg_DepLoan7_LateRepayNotice)gatewayDepLoan7Service
				.handleMsg(req);
		return res;
	}

	@Override
	public G2BResMsg_DepLoan7_QueryRepayResult queryRepayResult(
			G2BReqMsg_DepLoan7_QueryRepayResult req) {
		G2BResMsg_DepLoan7_QueryRepayResult res = (G2BResMsg_DepLoan7_QueryRepayResult)gatewayDepLoan7Service
				.handleMsg(req);
		return res;
	}

	@Override
	public G2BResMsg_DepLoan7_CutRepayConfirm cutRepayConfirm(
			G2BReqMsg_DepLoan7_CutRepayConfirm req) {
		G2BResMsg_DepLoan7_CutRepayConfirm res = (G2BResMsg_DepLoan7_CutRepayConfirm)gatewayDepLoan7Service
				.handleMsg(req);
		return res;
	}

	@Override
	public G2BResMsg_DepLoan7_AgreementNotice agreementNotice(
			G2BReqMsg_DepLoan7_AgreementNotice req) {
		G2BResMsg_DepLoan7_AgreementNotice res = (G2BResMsg_DepLoan7_AgreementNotice)gatewayDepLoan7Service
				.handleMsg(req);
		return res;
	}

	@Override
	public G2BResMsg_DepLoan7_ActiveRepaySmsCodeRepeat activeRepaySmsCodeRepeat(
			G2BReqMsg_DepLoan7_ActiveRepaySmsCodeRepeat req) {
		G2BResMsg_DepLoan7_ActiveRepaySmsCodeRepeat res = (G2BResMsg_DepLoan7_ActiveRepaySmsCodeRepeat)gatewayDepLoan7Service
				.handleMsg(req);
		return res;
	}
}
