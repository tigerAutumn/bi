package com.pinting.gateway.out.service.loan;

import com.pinting.gateway.hessian.message.loan.*;

/**
 * Created by 剑钊 on 2016/8/19.
 */
public interface NoticeService {

    /**
     * 借款通知
     * @param req
     * @return
     */
    B2GResMsg_LoanNotice_NoticeLoan noticeLoan(B2GReqMsg_LoanNotice_NoticeLoan req);


    /**
     * 还款通知
     * @param req
     * @return
     */
    B2GResMsg_RepayNotice_NoticeRepay noticeRepay(B2GReqMsg_RepayNotice_NoticeRepay req);
    
    
    /**
     * 银行卡限额通知
     * @param req
     * @return
     */
    B2GResMsg_BankLimit_LimitList noticeBankLimit(B2GReqMsg_BankLimit_LimitList req);


    /**
     * 合作方营销代付通知
     * @param req
     * @return
     */
    B2GResMsg_MarketNotice_NoticeMarketTrans noticeMarketTrans(B2GReqMsg_MarketNotice_NoticeMarketTrans req);
}
