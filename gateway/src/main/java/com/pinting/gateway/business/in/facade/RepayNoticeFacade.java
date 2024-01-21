package com.pinting.gateway.business.in.facade;

import com.pinting.gateway.bird.out.model.RepayReq;
import com.pinting.gateway.bird.out.service.NoticeService;
import com.pinting.gateway.hessian.message.loan.B2GReqMsg_RepayNotice_NoticeRepay;
import com.pinting.gateway.hessian.message.loan.B2GResMsg_RepayNotice_NoticeRepay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by 剑钊 on 2016/8/19.
 */
@Component("RepayNotice")
public class RepayNoticeFacade {

    @Autowired
    private NoticeService noticeService;

    public void noticeRepay(B2GReqMsg_RepayNotice_NoticeRepay req, B2GResMsg_RepayNotice_NoticeRepay res) throws Exception {

        RepayReq repayReq=new RepayReq();
        repayReq.setOrderNo(req.getOrderNo());
        repayReq.setChannel(req.getChannel());
        repayReq.setLoanId(req.getLoanId());
        repayReq.setRepayResultCode(req.getLoanResultCode());
        repayReq.setRepayResultMsg(req.getLoanResultMsg());

        noticeService.noticeRepay(repayReq);
    }
}
