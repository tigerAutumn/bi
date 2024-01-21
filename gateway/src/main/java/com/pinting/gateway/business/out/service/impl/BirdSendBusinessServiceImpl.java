package com.pinting.gateway.business.out.service.impl;

import com.pinting.gateway.hessian.message.loan.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.pinting.core.hessian.service.HessianService;
import com.pinting.gateway.business.out.service.BirdSendBusinessService;


/**
 * Created by 剑钊 on 2016/8/17.
 */
@Service
public class BirdSendBusinessServiceImpl implements BirdSendBusinessService {

	@Autowired
	@Qualifier("gatewayLoanService")
	private HessianService gatewayLoanService;
	
	@Override
	public G2BResMsg_LoanCif_AddLoaner addLoaner(G2BReqMsg_LoanCif_AddLoaner req) {
		return (G2BResMsg_LoanCif_AddLoaner) gatewayLoanService.handleMsg(req);
	}

	@Override
	public G2BResMsg_BankLimit_LimitList getBankLimit(
			G2BReqMsg_BankLimit_LimitList req) {
		return (G2BResMsg_BankLimit_LimitList)gatewayLoanService.handleMsg(req);
	}
	
	@Override
	public G2BResMsg_LoanCif_PreBindCard preBindCard(G2BReqMsg_LoanCif_PreBindCard req) {
		return (G2BResMsg_LoanCif_PreBindCard)gatewayLoanService.handleMsg(req);
	}

	@Override
	public G2BResMsg_LoanCif_BindCardConfirm bindCard(G2BReqMsg_LoanCif_BindCardConfirm req) {
		return (G2BResMsg_LoanCif_BindCardConfirm) gatewayLoanService.handleMsg(req);
	}

	@Override
	public G2BResMsg_LoanCif_UnBindCard unBindCard(G2BReqMsg_LoanCif_UnBindCard req) {
		return (G2BResMsg_LoanCif_UnBindCard) gatewayLoanService.handleMsg(req);
	}

	@Override
	public G2BResMsg_Loan_Loan loan(G2BReqMsg_Loan_Loan req) {

		return (G2BResMsg_Loan_Loan) gatewayLoanService.handleMsg(req);
	}

	@Override
	public G2BResMsg_Partner_MarketingTrans marketingTrans(G2BReqMsg_Partner_MarketingTrans req) {
		return (G2BResMsg_Partner_MarketingTrans) gatewayLoanService.handleMsg(req);
	}

	@Override
	public G2BResMsg_Repay_PreRepay preRepay(G2BReqMsg_Repay_PreRepay req) {
		return (G2BResMsg_Repay_PreRepay) gatewayLoanService.handleMsg(req);
	}

	@Override
	public G2BResMsg_Repay_WithholdingRepay repay(G2BReqMsg_Repay_WithholdingRepay req) {
		return (G2BResMsg_Repay_WithholdingRepay)gatewayLoanService.handleMsg(req);
	}

	@Override
	public G2BResMsg_Repay_RepayConfirm repayConfirm(G2BReqMsg_Repay_RepayConfirm req) {
		return (G2BResMsg_Repay_RepayConfirm) gatewayLoanService.handleMsg(req);
	}

	@Override
	public G2BResMsg_Loan_DailyAmount getDailyAmount(
			G2BReqMsg_Loan_DailyAmount req) {
		return (G2BResMsg_Loan_DailyAmount) gatewayLoanService.handleMsg(req);
	}

	@Override
	public G2BResMsg_Loan_QueryLoan queryLoanStatus(G2BReqMsg_Loan_QueryLoan req) {

		return (G2BResMsg_Loan_QueryLoan) gatewayLoanService.handleMsg(req);
	}

	@Override
	public G2BResMsg_Partner_QueryMarketingTrans queryMarketingTrans(G2BReqMsg_Partner_QueryMarketingTrans req) {
		return (G2BResMsg_Partner_QueryMarketingTrans)gatewayLoanService.handleMsg(req);
	}

	@Override
	public G2BResMsg_Partner_QueryBalance queryBalance(G2BReqMsg_Partner_QueryBalance req) {
		return (G2BResMsg_Partner_QueryBalance)gatewayLoanService.handleMsg(req);
	}

	@Override
	public G2BResMsg_Repay_QueryRepayResult queryResult(
			G2BReqMsg_Repay_QueryRepayResult req) {
		return (G2BResMsg_Repay_QueryRepayResult)gatewayLoanService.handleMsg(req);
	}

	@Override
	public G2BResMsg_Repay_BadDebt badDebt(G2BReqMsg_Repay_BadDebt req) {
		return (G2BResMsg_Repay_BadDebt)gatewayLoanService.handleMsg(req);
	}

}
