package com.pinting.business.mockito;

import com.pinting.business.accounting.loan.service.DepOfflineRepayService;
import com.pinting.gateway.hessian.message.hfbank.B2GReqMsg_HFBank_BatchExtRepay;
import com.pinting.gateway.hessian.message.hfbank.B2GResMsg_HFBank_BatchExtRepay;
import com.pinting.gateway.out.service.HfbankTransportService;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.util.ReflectionTestUtils;
/**
 * 
 * @project business
 * @title DepOfflineRepayBatchExtMockAspect.java
 * @author Dragon & cat
 * @date 2017-4-21
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description  线下还款 -借款人批量还款mock
 */
//@Aspect
//@Component
public class DepOfflineRepayBatchExtMockAspect {


    @Autowired
    private DepOfflineRepayService depOfflineRepayService;
    @Autowired
    private HfbankTransportService springHfbankTransportService;

    
    @Pointcut("execution(public * com.pinting.business.accounting.loan.service.impl.DepOfflineRepayServiceImpl.cutRepay2Borrower(..))")
    public void depFixedOfflineRepayPointcut1(){}

    
    
    @Before(value = "depFixedOfflineRepayPointcut1()")
    public void depFixedMockBefore() {
    	HfbankTransportService mockHfbankTransportService = Mockito.mock(HfbankTransportService.class);
        
        ReflectionTestUtils.setField(AopTargetUtils.getTarget(depOfflineRepayService), "hfbankTransportService", mockHfbankTransportService);
        B2GResMsg_HFBank_BatchExtRepay res = new B2GResMsg_HFBank_BatchExtRepay();
        res.setResCode("000000");
        res.setResMsg("成功");
        Mockito.doReturn(res).when(mockHfbankTransportService).batchExtRepay(Mockito.any(B2GReqMsg_HFBank_BatchExtRepay.class));
    }

    @After(value = "depFixedOfflineRepayPointcut1()")
    public void depFixedMockAfter() {
        ReflectionTestUtils.setField(AopTargetUtils.getTarget(depOfflineRepayService), "hfbankTransportService", springHfbankTransportService);
    }
}
