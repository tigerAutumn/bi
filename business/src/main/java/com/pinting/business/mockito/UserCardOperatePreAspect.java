package com.pinting.business.mockito;

import com.pinting.business.accounting.finance.service.UserCardOperateService;
import com.pinting.business.accounting.loan.service.LoanCardOperateService;
import com.pinting.gateway.hessian.message.baofoo.B2GReqMsg_BaoFooQuickPay_BindCard;
import com.pinting.gateway.hessian.message.baofoo.B2GResMsg_BaoFooQuickPay_BindCard;
import com.pinting.gateway.hessian.message.hfbank.B2GReqMsg_HFBank_UserPreBindCard;
import com.pinting.gateway.hessian.message.hfbank.B2GResMsg_HFBank_UserPreBindCard;
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
 * Date:        2016/9/22
 * Description: 用户预绑卡mock切面
 */
//@Aspect
//@Component
public class UserCardOperatePreAspect {

    @Autowired
    private UserCardOperateService userCardOperateService;
    @Autowired
    private HfbankTransportService hfbankTransportService;
    @Autowired
    private LoanCardOperateService loanCardOperateService;

    @Pointcut("execution(public * com.pinting.business.accounting.finance.service.impl.UserCardOperateServiceImpl.preBindCard(..))")
    public void UserCardOperatePreAspect() {}

    @Before(value = "UserCardOperatePreAspect()")
    public void mockBefore() {
        HfbankTransportService mockHfbankTransportService = Mockito.mock(HfbankTransportService.class);
        ReflectionTestUtils.setField(AopTargetUtils.getTarget(userCardOperateService), "hfbankTransportService", mockHfbankTransportService);

        // 出参
        B2GResMsg_HFBank_UserPreBindCard res = new B2GResMsg_HFBank_UserPreBindCard();
        res.setResCode("000000");
        Mockito.doReturn(res).when(mockHfbankTransportService).userPreBindCard(Mockito.any(B2GReqMsg_HFBank_UserPreBindCard.class));
    }

    @After(value = "UserCardOperatePreAspect()")
    public void mockAfter() {
        ReflectionTestUtils.setField(AopTargetUtils.getTarget(userCardOperateService), "hfbankTransportService", hfbankTransportService);
    }
    
    
    /*========================================*/
    
    @Pointcut("execution(public * com.pinting.business.accounting.loan.service.impl.LoanCardOperateServiceImpl.preBindCard(..))")
    public void LoanCardOperatePreAspect() {}

    @Before(value = "LoanCardOperatePreAspect()")
    public void mockLoanCardOperatePreBefore() {
    	
        HfbankTransportService mockHfbankTransportService = Mockito.mock(HfbankTransportService.class);
        ReflectionTestUtils.setField(AopTargetUtils.getTarget(loanCardOperateService), "hfbankTransportService", mockHfbankTransportService);

        // 出参
        B2GResMsg_HFBank_UserPreBindCard res = new B2GResMsg_HFBank_UserPreBindCard();
        res.setResCode("000000");
        Mockito.doReturn(res).when(mockHfbankTransportService).userPreBindCard(Mockito.any(B2GReqMsg_HFBank_UserPreBindCard.class));
    }

    @After(value = "LoanCardOperatePreAspect()")
    public void mockLoanCardOperatePreAfter() {
        ReflectionTestUtils.setField(AopTargetUtils.getTarget(loanCardOperateService), "hfbankTransportService", hfbankTransportService);
    }

}
