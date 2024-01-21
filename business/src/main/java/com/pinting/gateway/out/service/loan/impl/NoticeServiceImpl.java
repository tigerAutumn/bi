package com.pinting.gateway.out.service.loan.impl;

import com.pinting.core.hessian.service.HessianService;
import com.pinting.gateway.hessian.message.loan.*;
import com.pinting.gateway.out.service.loan.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Created by 剑钊 on 2016/8/19.
 */
@Service
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    @Qualifier("loanNoticeGatewayService")
    private HessianService noticeGatewayService;

    @Override
    public B2GResMsg_LoanNotice_NoticeLoan noticeLoan(B2GReqMsg_LoanNotice_NoticeLoan req) {
        return (B2GResMsg_LoanNotice_NoticeLoan) noticeGatewayService.handleMsg(req);
    }

    @Override
    public B2GResMsg_RepayNotice_NoticeRepay noticeRepay(B2GReqMsg_RepayNotice_NoticeRepay req) {
        return (B2GResMsg_RepayNotice_NoticeRepay)noticeGatewayService.handleMsg(req);
    }

	@Override
	public B2GResMsg_BankLimit_LimitList noticeBankLimit(B2GReqMsg_BankLimit_LimitList req) {
		return (B2GResMsg_BankLimit_LimitList)noticeGatewayService.handleMsg(req);
	}

    @Override
    public B2GResMsg_MarketNotice_NoticeMarketTrans noticeMarketTrans(B2GReqMsg_MarketNotice_NoticeMarketTrans req) {
        return (B2GResMsg_MarketNotice_NoticeMarketTrans) noticeGatewayService.handleMsg(req);
    }
}
