package com.pinting.gateway.business.out.service.impl;

import com.pinting.gateway.hessian.message.zsd.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.pinting.core.hessian.service.HessianService;
import com.pinting.gateway.business.out.service.ZsdSendBusinessService;

import com.pinting.gateway.hessian.message.zsd.G2BReqMsg_ZsdLoanApply_AddLoan;
import com.pinting.gateway.hessian.message.zsd.G2BReqMsg_ZsdRepay_LateRepayNotice;
import com.pinting.gateway.hessian.message.zsd.G2BResMsg_ZsdLoanApply_AddLoan;
import com.pinting.gateway.hessian.message.zsd.G2BReqMsg_ZsdLoanCif_AddLoaner;
import com.pinting.gateway.hessian.message.zsd.G2BResMsg_ZsdLoanCif_AddLoaner;
import com.pinting.gateway.hessian.message.zsd.G2BReqMsg_ZsdRepay_CutpaymentRepay;
import com.pinting.gateway.hessian.message.zsd.G2BResMsg_ZsdRepay_CutpaymentRepay;
import com.pinting.gateway.hessian.message.zsd.G2BResMsg_ZsdRepay_LateRepayNotice;
@Service
public class ZsdSendBusinessServiceImpl implements ZsdSendBusinessService {

	
	@Autowired
	@Qualifier("gatewayZsdService")
	private HessianService gatewayZsdService;
	

	@Override
	public G2BResMsg_ZsdLoanCif_AddLoaner addLoaner(
			G2BReqMsg_ZsdLoanCif_AddLoaner req) {
		return (G2BResMsg_ZsdLoanCif_AddLoaner) gatewayZsdService.handleMsg(req);
	}

	@Override
	public G2BResMsg_ZsdRepay_CutpaymentRepay cutpaymentRepay(G2BReqMsg_ZsdRepay_CutpaymentRepay req) {
		return (G2BResMsg_ZsdRepay_CutpaymentRepay) gatewayZsdService.handleMsg(req);
	}
	@Override
	public G2BResMsg_ZsdLoanApply_AddLoan addLoan(
			G2BReqMsg_ZsdLoanApply_AddLoan req) {
		return (G2BResMsg_ZsdLoanApply_AddLoan) gatewayZsdService.handleMsg(req);
	}

	@Override
	public G2BResMsg_ZsdLoanApply_QueryLoan queryLoanStatus(
			G2BReqMsg_ZsdLoanApply_QueryLoan req) {
		return (G2BResMsg_ZsdLoanApply_QueryLoan) gatewayZsdService.handleMsg(req);
	}

	@Override
	public G2BResMsg_ZsdRepay_PreRepay preRepay(G2BReqMsg_ZsdRepay_PreRepay req) {
		return (G2BResMsg_ZsdRepay_PreRepay) gatewayZsdService.handleMsg(req);
	}

	@Override
	public G2BResMsg_ZsdLoan_QueryDailyLimit queryDailyLimit(G2BReqMsg_ZsdLoan_QueryDailyLimit req) {
		return (G2BResMsg_ZsdLoan_QueryDailyLimit) gatewayZsdService.handleMsg(req);
	}

	@Override
	public G2BResMsg_ZsdBankLimit_QueryBankLimit queryBankLimit(G2BReqMsg_ZsdBankLimit_QueryBankLimit req) {
		return (G2BResMsg_ZsdBankLimit_QueryBankLimit) gatewayZsdService.handleMsg(req);
	}

	@Override
	public G2BResMsg_ZsdRepay_LateRepayNotice lateRepayNotice(
			G2BReqMsg_ZsdRepay_LateRepayNotice req) {
		return (G2BResMsg_ZsdRepay_LateRepayNotice) gatewayZsdService.handleMsg(req);
	}
	@Override
	public G2BResMsg_ZsdRepay_RepayConfirm repayConfirm(G2BReqMsg_ZsdRepay_RepayConfirm req) {
		return (G2BResMsg_ZsdRepay_RepayConfirm) gatewayZsdService.handleMsg(req);
	}

	@Override
	public G2BResMsg_ZsdRepay_QueryRepayResult queryResult(G2BReqMsg_ZsdRepay_QueryRepayResult req) {
		return (G2BResMsg_ZsdRepay_QueryRepayResult) gatewayZsdService.handleMsg(req);
	}

	@Override
	public G2BResMsg_ZsdRepay_BadDebt badDebt(G2BReqMsg_ZsdRepay_BadDebt req) {
		return (G2BResMsg_ZsdRepay_BadDebt) gatewayZsdService.handleMsg(req);
	}

}
