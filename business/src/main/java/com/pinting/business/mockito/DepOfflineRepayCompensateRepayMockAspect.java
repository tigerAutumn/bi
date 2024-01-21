package com.pinting.business.mockito;

import com.pinting.business.accounting.loan.service.DepOfflineRepayService;
import com.pinting.gateway.hessian.message.hfbank.B2GReqMsg_HFBank_CompensateRepay;
import com.pinting.gateway.hessian.message.hfbank.B2GResMsg_HFBank_CompensateRepay;
import com.pinting.gateway.hessian.message.hfbank.model.CompensateRepayResData;
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
 * @title DepOfflineRepayCompensateRepayMockAspect.java
 * @author Dragon & cat
 * @date 2017-4-22
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description 线下还款 -代偿还款mock
 */
//@Aspect
//@Component
public class DepOfflineRepayCompensateRepayMockAspect {


    @Autowired
    private DepOfflineRepayService depOfflineRepayService;
    @Autowired
    private HfbankTransportService springHfbankTransportService;

    
    @Pointcut("execution(public * com.pinting.business.accounting.loan.service.impl.DepOfflineRepayServiceImpl.compRepay2DepTarget(..))")
    public void depFixedOfflineCompRepay2DepTarget(){}

    
    
    @Before(value = "depFixedOfflineCompRepay2DepTarget()")
    public void depFixedMockBefore() {
    	HfbankTransportService mockHfbankTransportService = Mockito.mock(HfbankTransportService.class);
        
        ReflectionTestUtils.setField(AopTargetUtils.getTarget(depOfflineRepayService), "hfbankTransportService", mockHfbankTransportService);
        B2GResMsg_HFBank_CompensateRepay res = new B2GResMsg_HFBank_CompensateRepay();
        res.setResCode("000000");
        res.setResMsg("成功");
        CompensateRepayResData data = new CompensateRepayResData();
        data.setOrder_status("2");
        res.setData(data);
        Mockito.doReturn(res).when(mockHfbankTransportService).compensateRepay(Mockito.any(B2GReqMsg_HFBank_CompensateRepay.class));
    }

    @After(value = "depFixedOfflineCompRepay2DepTarget()")
    public void depFixedMockAfter() {
        ReflectionTestUtils.setField(AopTargetUtils.getTarget(depOfflineRepayService), "hfbankTransportService", springHfbankTransportService);
    }
}
