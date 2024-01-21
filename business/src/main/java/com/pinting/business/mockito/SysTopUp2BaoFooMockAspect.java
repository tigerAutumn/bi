package com.pinting.business.mockito;

import com.pinting.business.service.manage.MSysTopUp2BaoFooService;
import com.pinting.gateway.hessian.message.baofoo.B2GReqMsg_BaoFooQuickPay_QuickPay;
import com.pinting.gateway.hessian.message.baofoo.B2GReqMsg_BaoFooQuickPay_QuickPayConfirm;
import com.pinting.gateway.hessian.message.baofoo.B2GResMsg_BaoFooQuickPay_QuickPay;
import com.pinting.gateway.hessian.message.baofoo.B2GResMsg_BaoFooQuickPay_QuickPayConfirm;
import com.pinting.gateway.out.service.BaoFooTransportService;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * Author:      cyb
 * Date:        2017/7/24
 * Description:
 */
//@Aspect
//@Component
public class SysTopUp2BaoFooMockAspect {

    @Autowired
    private MSysTopUp2BaoFooService mSysTopUp2BaoFooService;

    @Autowired
    private BaoFooTransportService springBaoFooTransportService;

    @Pointcut("execution(public * com.pinting.business.service.manage.impl.MSysTopUp2BaoFooServiceImpl.preTopUp(..))")
    public void sysTopUp2BaoFooRreTopUpPointcut(){}

    @Pointcut("execution(public * com.pinting.business.service.manage.impl.MSysTopUp2BaoFooServiceImpl.topUp(..))")
    public void sysTopUp2BaoFooConfirmTopUpPointcut(){}

    @Before(value = "sysTopUp2BaoFooRreTopUpPointcut()")
    public void mockBefore() {
        BaoFooTransportService mockBaoFooTransportService = Mockito.mock(BaoFooTransportService.class);
        ReflectionTestUtils.setField(AopTargetUtils.getTarget(mSysTopUp2BaoFooService), "baoFooTransportService", mockBaoFooTransportService);
        B2GResMsg_BaoFooQuickPay_QuickPay res = new B2GResMsg_BaoFooQuickPay_QuickPay();
        res.setResCode("000000");
        res.setResMsg("交易成功");
        Mockito.doReturn(res).when(mockBaoFooTransportService).quickPay(Mockito.any(B2GReqMsg_BaoFooQuickPay_QuickPay.class));
    }

    @After(value = "sysTopUp2BaoFooRreTopUpPointcut()")
    public void mockAfter() {
        ReflectionTestUtils.setField(AopTargetUtils.getTarget(mSysTopUp2BaoFooService), "baoFooTransportService", springBaoFooTransportService);
    }

    @Before(value = "sysTopUp2BaoFooConfirmTopUpPointcut()")
    public void mockTopUpBefore() {
        BaoFooTransportService mockBaoFooTransportService = Mockito.mock(BaoFooTransportService.class);
        ReflectionTestUtils.setField(AopTargetUtils.getTarget(mSysTopUp2BaoFooService), "baoFooTransportService", mockBaoFooTransportService);
        B2GResMsg_BaoFooQuickPay_QuickPayConfirm res = new B2GResMsg_BaoFooQuickPay_QuickPayConfirm();
        res.setResCode("000000");
        res.setResMsg("交易成功");
        Mockito.doReturn(res).when(mockBaoFooTransportService).quickPayConfirm(Mockito.any(B2GReqMsg_BaoFooQuickPay_QuickPayConfirm.class));
    }

    @After(value = "sysTopUp2BaoFooConfirmTopUpPointcut()")
    public void mockTopUpAfter() {
        ReflectionTestUtils.setField(AopTargetUtils.getTarget(mSysTopUp2BaoFooService), "baoFooTransportService", springBaoFooTransportService);
    }
}
