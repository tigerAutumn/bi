package com.pinting.gateway.bird.out.service;

import com.pinting.gateway.bird.out.model.*;

/**
 * Created by 剑钊 on 2016/8/18.
 */
public interface NoticeService {

    /**
     * 借款通知
     * @param req
     * @return
     */
    LoanRes noticeLoan (LoanReq req) throws Exception;

    /**
     * 还款通知
     * @param req
     * @return
     */
    RepayRes noticeRepay (RepayReq req) throws Exception;

    /**
     * 银行卡限额通知
     * @param req
     * @return
     * @throws Exception
     */
    BankLimitRes sandBankLimit(BankLimitReq req)throws Exception;


    /**
     * 营销代付通知
     * @param req
     * @return
     */
    MarketTransRes noticeMarketTrans (MarketTransReq req) throws Exception;
}
