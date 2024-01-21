package com.pinting.gateway.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.pinting.gateway.bird.out.model.LoanReq;
import com.pinting.gateway.bird.out.model.LoanRes;
import com.pinting.gateway.bird.out.service.NoticeService;

/**
 * 
 * @project gateway
 * @title TestBird.java
 * @author Dragon & cat
 * @date 2017-6-8
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description
 */
public class TestBird extends TestBase {
	@Autowired
	private NoticeService noticeService;
	
 	@Test
    public void noticeService() throws Exception{
        LoanReq req = new LoanReq();
        req.setOrderNo("111111111");
        req.setLoanId("1111111111");
        req.setLoanResultCode("SUCC");
        req.setLoanResultMsg("我是快乐的中文哦~");
        LoanRes res = noticeService.noticeLoan(req);
        System.err.println(res.getResponseTime());
        /*BankLimitReq req = new BankLimitReq();
        req.setLimits(null);
        BankLimitRes res= noticeService.sandBankLimit(req);*/
    }
}
