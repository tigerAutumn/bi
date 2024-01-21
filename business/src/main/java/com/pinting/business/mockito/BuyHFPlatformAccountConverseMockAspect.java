package com.pinting.business.mockito;

import com.pinting.business.accounting.finance.service.DepUserTopUpService;
import com.pinting.business.util.DateUtil;
import com.pinting.gateway.hessian.message.hfbank.B2GReqMsg_HFBank_PlatformAccountConverse;
import com.pinting.gateway.hessian.message.hfbank.B2GReqMsg_HFBank_UserQuickPayConfirmRecharge;
import com.pinting.gateway.hessian.message.hfbank.B2GResMsg_HFBank_PlatformAccountConverse;
import com.pinting.gateway.hessian.message.hfbank.B2GResMsg_HFBank_UserQuickPayConfirmRecharge;
import com.pinting.gateway.hessian.message.hfbank.model.DirectQuickConfirmData;
import com.pinting.gateway.out.service.HfbankTransportService;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Date;

/**
 * Author:      cyb
 * Date:        2017/4/11
 * Description: 充值预下单
 */
//@Aspect
//@Component
public class BuyHFPlatformAccountConverseMockAspect {

    @Autowired
    private DepUserTopUpService depUserTopUpService;

    @Autowired
    private HfbankTransportService hfbankTransportService;

    @Pointcut("execution(public * com.pinting.business.accounting.finance.service.impl.DepUserBalanceBuyServiceImpl.buyFixed(..))")
    public void hfbankBuyPointcut(){}

    @Before(value = "hfbankBuyPointcut()")
    public void mockBefore() {
        HfbankTransportService mockHfbankTransportService = Mockito.mock(HfbankTransportService.class);
        ReflectionTestUtils.setField(AopTargetUtils.getTarget(depUserTopUpService), "hfbankTransportService", mockHfbankTransportService);
        B2GResMsg_HFBank_PlatformAccountConverse res = new B2GResMsg_HFBank_PlatformAccountConverse();
        res.setResCode("000000");
        res.setResMsg("交易成功");
        Mockito.doReturn(res).when(mockHfbankTransportService).platformAccountConverse(Mockito.any(B2GReqMsg_HFBank_PlatformAccountConverse.class));
    }

    @After(value = "hfbankBuyPointcut()")
    public void mockAfter() {
        ReflectionTestUtils.setField(AopTargetUtils.getTarget(depUserTopUpService), "hfbankTransportService", hfbankTransportService);
    }


}
