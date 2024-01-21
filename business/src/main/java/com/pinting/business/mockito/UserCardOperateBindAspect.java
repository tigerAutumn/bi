package com.pinting.business.mockito;

import com.pinting.business.accounting.finance.service.UserCardOperateService;
import com.pinting.business.accounting.loan.service.LoanCardOperateService;
import com.pinting.gateway.hessian.message.baofoo.B2GReqMsg_BaoFooQuickPay_BindCardConfirm;
import com.pinting.gateway.hessian.message.baofoo.B2GResMsg_BaoFooQuickPay_BindCardConfirm;
import com.pinting.gateway.hessian.message.hfbank.B2GReqMsg_HFBank_BatchBindCard4Elements;
import com.pinting.gateway.hessian.message.hfbank.B2GReqMsg_HFBank_UserBindCard;
import com.pinting.gateway.hessian.message.hfbank.B2GResMsg_HFBank_BatchBindCard4Elements;
import com.pinting.gateway.hessian.message.hfbank.B2GResMsg_HFBank_UserBindCard;
import com.pinting.gateway.hessian.message.hfbank.model.BatchRegistExtSuccess;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Author:      cyb
 * Date:        2016/9/22
 * Description: 用户正式绑卡mock切面
 */
//@Aspect
//@Component
public class UserCardOperateBindAspect {

    @Autowired
    private UserCardOperateService userCardOperateService;
    @Autowired
    private HfbankTransportService hfbankTransportService;
    @Autowired
    private	LoanCardOperateService loanCardOperateService;

    @Pointcut("execution(public * com.pinting.business.accounting.finance.service.impl.UserCardOperateServiceImpl.bindCard(..))")
    public void UserCardOperateBindAspect() {}

    @Before(value = "UserCardOperateBindAspect()")
    public void mockBefore() {
        HfbankTransportService mockHfbankTransportService = Mockito.mock(HfbankTransportService.class);
        ReflectionTestUtils.setField(AopTargetUtils.getTarget(userCardOperateService), "hfbankTransportService", mockHfbankTransportService);
        // 入参
        B2GResMsg_HFBank_BatchBindCard4Elements res = new B2GResMsg_HFBank_BatchBindCard4Elements();
        res.setResCode("000000");
        res.setSuccess_num("1");
        List<BatchRegistExtSuccess> success_data = new ArrayList<BatchRegistExtSuccess>();
        BatchRegistExtSuccess succ = new BatchRegistExtSuccess();
        succ.setPlatcust("12345678");
        success_data.add(succ);
        res.setSuccess_data(success_data);
        Mockito.doReturn(res).when(mockHfbankTransportService).batchBindCard4Elements(Mockito.any(B2GReqMsg_HFBank_BatchBindCard4Elements.class));
    }

    @After(value = "UserCardOperateBindAspect()")
    public void mockAfter() {
        ReflectionTestUtils.setField(AopTargetUtils.getTarget(userCardOperateService), "hfbankTransportService", hfbankTransportService);
    }
    
    
    
    @Pointcut("execution(public * com.pinting.business.accounting.loan.service.impl.LoanCardOperateServiceImpl.bindCardConfirm(..))")
    public void LoanCardOperateBindConfirmAspect() {}

    @Before(value = "LoanCardOperateBindConfirmAspect()")
    public void mockBindCardConfirmBefore() {
        HfbankTransportService mockHfbankTransportService = Mockito.mock(HfbankTransportService.class);
        ReflectionTestUtils.setField(AopTargetUtils.getTarget(loanCardOperateService), "hfbankTransportService", mockHfbankTransportService);
        // 入参
        B2GResMsg_HFBank_UserBindCard res = new B2GResMsg_HFBank_UserBindCard();
        res.setResCode("000000");
        res.setPlatcust("1888888");
        Mockito.doReturn(res).when(mockHfbankTransportService).userBindCard(Mockito.any(B2GReqMsg_HFBank_UserBindCard.class));
    }

    @After(value = "LoanCardOperateBindConfirmAspect()")
    public void mockBindCardConfirmAfter() {
        ReflectionTestUtils.setField(AopTargetUtils.getTarget(loanCardOperateService), "hfbankTransportService", hfbankTransportService);
    }


}
