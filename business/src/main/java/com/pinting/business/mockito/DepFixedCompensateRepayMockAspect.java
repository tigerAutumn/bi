package com.pinting.business.mockito;

import com.pinting.business.accounting.loan.service.RepayPaymentService;
import com.pinting.business.util.Constants;
import com.pinting.gateway.hessian.message.hfbank.B2GReqMsg_HFBank_RepayCompensate;
import com.pinting.gateway.hessian.message.hfbank.B2GResMsg_HFBank_RepayCompensate;
import com.pinting.gateway.hessian.message.hfbank.model.RepayCompensateResData;
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
 * @title DepOfflineRepayCompensateMockAspect.java
 * @author Dragon & cat
 * @date 2017-4-22
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description  借款人还款代偿（委托）  ---借款人还款到代偿人
 * 
 */
//@Aspect
//@Component
public class DepFixedCompensateRepayMockAspect {


    @Autowired
    private RepayPaymentService repayPaymentService;
    @Autowired
    private HfbankTransportService springHfbankTransportService;

    
    @Pointcut("execution(public * com.pinting.business.accounting.loan.service.impl.RepayPaymentServiceImpl.overdueRepay2Compensate(..))")
    public void depFixedOverdueRepay2Compensate(){}

    
    
    @Before(value = "depFixedOverdueRepay2Compensate()")
    public void depFixedMockBefore() {
    	HfbankTransportService mockHfbankTransportService = Mockito.mock(HfbankTransportService.class);
        
        ReflectionTestUtils.setField(AopTargetUtils.getTarget(repayPaymentService), "hfbankTransportService", mockHfbankTransportService);
        B2GResMsg_HFBank_RepayCompensate res = new B2GResMsg_HFBank_RepayCompensate();
        res.setResCode("000000");
        res.setResMsg("成功");
        RepayCompensateResData data = new RepayCompensateResData();
        data.setOrder_status(Constants.HF_ORDER_STATUS_SUCC);
        res.setData(data);
        Mockito.doReturn(res).when(mockHfbankTransportService).repayCompensate(Mockito.any(B2GReqMsg_HFBank_RepayCompensate.class));
    }

    @After(value = "depFixedOverdueRepay2Compensate()")
    public void depFixedMockAfter() {
        ReflectionTestUtils.setField(AopTargetUtils.getTarget(repayPaymentService), "hfbankTransportService", springHfbankTransportService);
    }
}
