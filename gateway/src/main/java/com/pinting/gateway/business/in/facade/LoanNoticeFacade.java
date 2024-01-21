package com.pinting.gateway.business.in.facade;

import com.pinting.gateway.bird.out.model.LoanReq;
import com.pinting.gateway.bird.out.service.NoticeService;
import com.pinting.gateway.hessian.message.loan.B2GReqMsg_LoanNotice_NoticeLoan;
import com.pinting.gateway.hessian.message.loan.B2GResMsg_LoanNotice_NoticeLoan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by 剑钊 on 2016/8/3.
 */
@Component("LoanNotice")
public class LoanNoticeFacade {

    @Autowired
    private NoticeService noticeService;

    public void  noticeLoan(B2GReqMsg_LoanNotice_NoticeLoan req,B2GResMsg_LoanNotice_NoticeLoan res) throws Exception {

        LoanReq loanReq=new LoanReq();
        loanReq.setOrderNo(req.getOrderNo());
        loanReq.setLoanId(req.getLoanId());
        loanReq.setChannel(req.getPayChannel());
        loanReq.setLoanResultMsg(req.getLoanResultMsg());
        loanReq.setLoanResultCode(req.getLoanResultCode());

        noticeService.noticeLoan(loanReq);
    }
}
