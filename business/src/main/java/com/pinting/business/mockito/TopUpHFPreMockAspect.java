package com.pinting.business.mockito;

import com.pinting.business.accounting.finance.service.DepUserTopUpService;
import com.pinting.business.accounting.finance.service.UserTopUpService;
import com.pinting.business.util.Constants;
import com.pinting.gateway.hessian.message.baofoo.B2GReqMsg_BaoFooQuickPay_QuickPay;
import com.pinting.gateway.hessian.message.baofoo.B2GResMsg_BaoFooQuickPay_QuickPay;
import com.pinting.gateway.hessian.message.hfbank.B2GReqMsg_HFBank_UserQuickPayPreRecharge;
import com.pinting.gateway.hessian.message.hfbank.B2GResMsg_HFBank_UserQuickPayPreRecharge;
import com.pinting.gateway.out.service.BaoFooTransportService;
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
 * Author:      cyb
 * Date:        2017/4/11
 * Description: 充值预下单
 */
@Aspect
@Component
public class TopUpHFPreMockAspect {

    @Autowired
    private DepUserTopUpService depUserTopUpService;

    @Autowired
    private HfbankTransportService hfbankTransportService;

    @Pointcut("execution(public * com.pinting.business.accounting.finance.service.impl.DepUserTopUpServiceImpl.hfPre(..))")
    public void hfbankPreTopUpPointcut(){}

    @Before(value = "hfbankPreTopUpPointcut()")
    public void mockBefore() {
        HfbankTransportService mockHfbankTransportService = Mockito.mock(HfbankTransportService.class);
        ReflectionTestUtils.setField(AopTargetUtils.getTarget(depUserTopUpService), "hfbankTransportService", mockHfbankTransportService);
        B2GResMsg_HFBank_UserQuickPayPreRecharge res = new B2GResMsg_HFBank_UserQuickPayPreRecharge();
        res.setResCode("000000");
        res.setResMsg("交易成功");
//        res.setResMsg(Constants.GATEWAY_PAYING_RES_CODE);
        Mockito.doReturn(res).when(mockHfbankTransportService).userQuickPayPreRecharge(Mockito.any(B2GReqMsg_HFBank_UserQuickPayPreRecharge.class));
    }

    @After(value = "hfbankPreTopUpPointcut()")
    public void mockAfter() {
        ReflectionTestUtils.setField(AopTargetUtils.getTarget(depUserTopUpService), "hfbankTransportService", hfbankTransportService);
    }


}
