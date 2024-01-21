package com.pinting.business.mockito;

import com.pinting.business.accounting.loan.enums.LoanStatus;
import com.pinting.business.accounting.loan.service.DepOfflineRepayService;
import com.pinting.gateway.hessian.message.hfbank.B2GReqMsg_HFBank_BorrowCutRepayDK;
import com.pinting.gateway.hessian.message.hfbank.B2GResMsg_HFBank_BorrowCutRepayDK;
import com.pinting.gateway.out.service.HfbankTransportService;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * 
 * @project business
 * @title DepOfflineRepayBorrowCutDKMockAspect.java
 * @author Dragon & cat
 * @date 2017-4-22
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description 线下还款 -代扣还款到借款人mock
 */
//@Aspect
//@Component
public class DepOfflineRepayBorrowCutDKMockAspect {


    @Autowired
    private DepOfflineRepayService depOfflineRepayService;
    @Autowired
    private HfbankTransportService springHfbankTransportService;

    
    @Pointcut("execution(public * com.pinting.business.accounting.loan.service.impl.DepOfflineRepayServiceImpl.cutRepay2Borrower(..))")
    public void depFixedOfflineRepayPointcut2(){}

    
    
    @Before(value = "depFixedOfflineRepayPointcut2()")
    public void depFixedMockBefore() {
    	HfbankTransportService mockHfbankTransportService = Mockito.mock(HfbankTransportService.class);
        
        ReflectionTestUtils.setField(AopTargetUtils.getTarget(depOfflineRepayService), "hfbankTransportService", mockHfbankTransportService);
        B2GResMsg_HFBank_BorrowCutRepayDK res = new B2GResMsg_HFBank_BorrowCutRepayDK();
//        res.setResCode(LoanStatus.BAOFOO_PAY_STATUS_PROCESS.getCode());
//        res.setResMsg("处理中");
        res.setResCode("000000");
        res.setResMsg("成功");
        /*res.setResCode("999999");
        res.setResMsg("失败啦");*/
        Mockito.doReturn(res).when(mockHfbankTransportService).borrowCutRepayDK(Mockito.any(B2GReqMsg_HFBank_BorrowCutRepayDK.class));
    }

    @After(value = "depFixedOfflineRepayPointcut2()")
    public void depFixedMockAfter() {
        ReflectionTestUtils.setField(AopTargetUtils.getTarget(depOfflineRepayService), "hfbankTransportService", springHfbankTransportService);
    }
}
