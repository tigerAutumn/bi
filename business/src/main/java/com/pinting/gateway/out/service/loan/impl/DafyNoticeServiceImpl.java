package com.pinting.gateway.out.service.loan.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.pinting.core.hessian.service.HessianService;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_DafyLoanNotice_AgreementNotice;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_DafyLoanNotice_LoanResultNotice;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_DafyLoanNotice_QueryBill;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_DafyLoanNotice_RepayResultNotice;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_DafyLoanNotice_RevenueSettle;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_DafyLoanNotice_SignResultNotice;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_DafyLoanNotice_WaitFill;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_DafyLoanNotice_AgreementNotice;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_DafyLoanNotice_LoanResultNotice;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_DafyLoanNotice_QueryBill;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_DafyLoanNotice_RepayResultNotice;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_DafyLoanNotice_RevenueSettle;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_DafyLoanNotice_SignResultNotice;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_DafyLoanNotice_WaitFill;
import com.pinting.gateway.out.service.loan.DafyNoticeService;
import org.springframework.stereotype.Service;

@Service
public class DafyNoticeServiceImpl implements DafyNoticeService{

    @Autowired
    @Qualifier("dafyLoanNoticeGatewayService")
    private HessianService noticeGatewayService;
    
	@Override
	public B2GResMsg_DafyLoanNotice_LoanResultNotice noticeLoan(
			B2GReqMsg_DafyLoanNotice_LoanResultNotice req) {
		return (B2GResMsg_DafyLoanNotice_LoanResultNotice) noticeGatewayService.handleMsg(req);
	}

	@Override
	public B2GResMsg_DafyLoanNotice_RepayResultNotice noticeRepay(
			B2GReqMsg_DafyLoanNotice_RepayResultNotice req) {
		return (B2GResMsg_DafyLoanNotice_RepayResultNotice) noticeGatewayService.handleMsg(req);
	}
	
	@Override
	public B2GResMsg_DafyLoanNotice_RevenueSettle noticeRevenueSettle(
			B2GReqMsg_DafyLoanNotice_RevenueSettle req) {
		return (B2GResMsg_DafyLoanNotice_RevenueSettle) noticeGatewayService.handleMsg(req);
	}

	@Override
	public B2GResMsg_DafyLoanNotice_WaitFill noticeWaitFill(
			B2GReqMsg_DafyLoanNotice_WaitFill req) {
		return (B2GResMsg_DafyLoanNotice_WaitFill) noticeGatewayService.handleMsg(req);
	}

	@Override
	public B2GResMsg_DafyLoanNotice_SignResultNotice signResultNotice(
			B2GReqMsg_DafyLoanNotice_SignResultNotice req) {
		return (B2GResMsg_DafyLoanNotice_SignResultNotice) noticeGatewayService.handleMsg(req);
	}

	@Override
	public B2GResMsg_DafyLoanNotice_QueryBill queryBill(
			B2GReqMsg_DafyLoanNotice_QueryBill req) {
		return (B2GResMsg_DafyLoanNotice_QueryBill) noticeGatewayService.handleMsg(req);
	}

	@Override
	public B2GResMsg_DafyLoanNotice_AgreementNotice agreementNotice(
			B2GReqMsg_DafyLoanNotice_AgreementNotice req) {
		return (B2GResMsg_DafyLoanNotice_AgreementNotice) noticeGatewayService.handleMsg(req);
	}
}
