package com.pinting.gateway.business.in.facade;


import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_DafyLoanNotice_SignResultNotice;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_DafyLoanNotice_SignResultNotice;
import com.pinting.gateway.hessian.message.zsd.*;
import com.pinting.gateway.zsd.out.model.ZsdBankLimitReq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinting.gateway.zsd.out.model.LoanReq;
import com.pinting.gateway.zsd.out.model.RepayReq;
import com.pinting.gateway.zsd.out.model.SignResultNoticeReqModel;
import com.pinting.gateway.zsd.out.model.SignResultNoticeResModel;
import com.pinting.gateway.zsd.out.service.ZsdNoticeService;

@Component("ZsdNotice")
public class ZsdNoticeFacade {
    @Autowired
    private ZsdNoticeService noticeService;

    public void  noticeLoan(B2GReqMsg_ZsdNotice_NoticeLoan req,B2GResMsg_ZsdNotice_NoticeLoan res) throws Exception {

        LoanReq loanReq=new LoanReq();
        loanReq.setOrderNo(req.getOrderNo());
        loanReq.setLoanId(req.getLoanId());
        loanReq.setChannel(req.getPayChannel());
        loanReq.setLoanResultMsg(req.getLoanResultMsg());
        loanReq.setLoanResultCode(req.getLoanResultCode());

        noticeService.noticeLoan(loanReq);
    }
    
    public void  noticeRepay(B2GReqMsg_ZsdNotice_NoticeRepay req, B2GResMsg_ZsdNotice_NoticeRepay res) throws Exception {

    	RepayReq repayReq = new RepayReq();
    	repayReq.setOrderNo(req.getOrderNo());
    	repayReq.setLoanId(req.getLoanId());
    	repayReq.setChannel(req.getPayChannel());
    	repayReq.setRepayResultMsg(req.getRepayResultMsg());
    	repayReq.setRepayResultCode(req.getRepayResultCode());
    	
        noticeService.noticeRepay(repayReq);
    }

    public void noticeBankLimit(B2GReqMsg_ZsdNotice_NoticeBankLimit req, B2GResMsg_ZsdNotice_NoticeBankLimit res) throws Exception {

        ZsdBankLimitReq zsdBankLimitReq = new ZsdBankLimitReq();
        zsdBankLimitReq.setLimits(req.getLimits());

        noticeService.noticeBankLimit(zsdBankLimitReq);
    }
    
    /**
     * 借款协议签章结果通知
     * @param req
     * @param res
     * @throws Exception
     */
    public void signResultNotice(B2GReqMsg_ZsdNotice_SignResultNotice req, B2GResMsg_ZsdNotice_SignResultNotice res) throws Exception {
    	SignResultNoticeReqModel signResultNoticeReqModel = new SignResultNoticeReqModel();
    	signResultNoticeReqModel.setAgreementNo(req.getAgreementNo());
    	signResultNoticeReqModel.setLoanId(req.getLoanId());
    	signResultNoticeReqModel.setSignResult(req.getSignResult());
    	signResultNoticeReqModel.setAgreementUrl(req.getAgreementUrl());
    	/*List<SignResultNoticeLender> list = new ArrayList<SignResultNoticeLender>();
    	if (req.getLenders() != null ) {
    		for (SignResultNoticeLenderModel signResultNoticeLender : req.getLenders()) {
        		SignResultNoticeLender lender = new SignResultNoticeLender();
        		lender.setLenderName(signResultNoticeLender.getLenderName());
        		lender.setLenderIdcard(signResultNoticeLender.getLenderIdcard());
        		lender.setInvestAmount(MoneyUtil.multiply(signResultNoticeLender.getInvestAmount(), 100).longValue());
        		list.add(lender);
        	}
		}
    	signResultNoticeReqModel.setLenders(list);*/
    	SignResultNoticeResModel signResultNoticeResModel = noticeService.signResultNotice(signResultNoticeReqModel);
    }
    
}	
