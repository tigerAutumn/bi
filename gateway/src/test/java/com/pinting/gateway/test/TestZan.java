package com.pinting.gateway.test;


import com.pinting.gateway.zsd.out.model.LoanReq;
import com.pinting.gateway.zsd.out.model.LoanRes;
import com.pinting.gateway.zsd.out.service.ZsdNoticeService;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Zhou Changzai
 * @Project: gateway
 * @Title: TestDafy.java
 * @date 2015-2-12 下午3:31:50
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public class TestZan extends TestBase {

    @Autowired
    private ZsdNoticeService noticeService;

    @Test
    public void noticeService() throws Exception{
        LoanReq req = new LoanReq();
        req.setOrderNo("7b5c5d5b54074deba1606e440d48c531");
        req.setLoanId("7b5c5d5b54074deba1606e440d48c531");
        req.setLoanResultCode("SUCCESS");
        req.setLoanResultMsg("测试手动通知");
        LoanRes res = noticeService.noticeLoan(req);
        System.err.println(res.getResponseTime());
        /*BankLimitReq req = new BankLimitReq();
        req.setLimits(null);
        BankLimitRes res= noticeService.sandBankLimit(req);*/
    }
}
