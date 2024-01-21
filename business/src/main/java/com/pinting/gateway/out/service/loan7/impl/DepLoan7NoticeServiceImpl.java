package com.pinting.gateway.out.service.loan7.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.pinting.core.hessian.service.HessianService;
import com.pinting.gateway.hessian.message.loan7.B2GReqMsg_DepLoan7Notice_AgreementNotice;
import com.pinting.gateway.hessian.message.loan7.B2GReqMsg_DepLoan7Notice_LoanResultNotice;
import com.pinting.gateway.hessian.message.loan7.B2GReqMsg_DepLoan7Notice_QueryBill;
import com.pinting.gateway.hessian.message.loan7.B2GReqMsg_DepLoan7Notice_RepayResultNotice;
import com.pinting.gateway.hessian.message.loan7.B2GReqMsg_DepLoan7Notice_RevenueSettle;
import com.pinting.gateway.hessian.message.loan7.B2GReqMsg_DepLoan7Notice_SignResultNotice;
import com.pinting.gateway.hessian.message.loan7.B2GReqMsg_DepLoan7Notice_WaitFill;
import com.pinting.gateway.hessian.message.loan7.B2GResMsg_DepLoan7Notice_AgreementNotice;
import com.pinting.gateway.hessian.message.loan7.B2GResMsg_DepLoan7Notice_LoanResultNotice;
import com.pinting.gateway.hessian.message.loan7.B2GResMsg_DepLoan7Notice_QueryBill;
import com.pinting.gateway.hessian.message.loan7.B2GResMsg_DepLoan7Notice_RepayResultNotice;
import com.pinting.gateway.hessian.message.loan7.B2GResMsg_DepLoan7Notice_RevenueSettle;
import com.pinting.gateway.hessian.message.loan7.B2GResMsg_DepLoan7Notice_SignResultNotice;
import com.pinting.gateway.hessian.message.loan7.B2GResMsg_DepLoan7Notice_WaitFill;
import com.pinting.gateway.out.service.loan7.DepLoan7NoticeService;

/**
 *
 * @author SHENGUOPING
 * @date  2017年12月13日 上午10:49:51
 */
@Service
public class DepLoan7NoticeServiceImpl implements DepLoan7NoticeService {
	
	@Autowired
    @Qualifier("depLoan7NoticeGatewayService")
    private HessianService noticeGatewayService;
    
	@Override
	public B2GResMsg_DepLoan7Notice_LoanResultNotice noticeLoan(
			B2GReqMsg_DepLoan7Notice_LoanResultNotice req) {
		return (B2GResMsg_DepLoan7Notice_LoanResultNotice) noticeGatewayService.handleMsg(req);
	}

	@Override
	public B2GResMsg_DepLoan7Notice_RepayResultNotice noticeRepay(
			B2GReqMsg_DepLoan7Notice_RepayResultNotice req) {
		return (B2GResMsg_DepLoan7Notice_RepayResultNotice) noticeGatewayService.handleMsg(req);
	}

	@Override
	public B2GResMsg_DepLoan7Notice_RevenueSettle noticeRevenueSettle(
			B2GReqMsg_DepLoan7Notice_RevenueSettle req) {
		return (B2GResMsg_DepLoan7Notice_RevenueSettle) noticeGatewayService.handleMsg(req);
	}

	@Override
	public B2GResMsg_DepLoan7Notice_WaitFill noticeWaitFill(
			B2GReqMsg_DepLoan7Notice_WaitFill req) {
		return (B2GResMsg_DepLoan7Notice_WaitFill) noticeGatewayService.handleMsg(req);
	}

	@Override
	public B2GResMsg_DepLoan7Notice_SignResultNotice signResultNotice(
			B2GReqMsg_DepLoan7Notice_SignResultNotice req) {
		return (B2GResMsg_DepLoan7Notice_SignResultNotice) noticeGatewayService.handleMsg(req);
	}

	@Override
	public B2GResMsg_DepLoan7Notice_QueryBill queryBill(
			B2GReqMsg_DepLoan7Notice_QueryBill req) {
		return (B2GResMsg_DepLoan7Notice_QueryBill) noticeGatewayService.handleMsg(req);
	}

	@Override
	public B2GResMsg_DepLoan7Notice_AgreementNotice agreementNotice(
			B2GReqMsg_DepLoan7Notice_AgreementNotice req) {
		return (B2GResMsg_DepLoan7Notice_AgreementNotice) noticeGatewayService.handleMsg(req);
	}

}
