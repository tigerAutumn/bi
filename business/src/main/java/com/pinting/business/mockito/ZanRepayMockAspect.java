package com.pinting.business.mockito;

import com.pinting.business.accounting.loan.enums.LoanStatus;
import com.pinting.business.accounting.loan.service.RepayPaymentService;
import com.pinting.business.hessian.site.message.ReqMsg_UserBalance_Withdraw;
import com.pinting.gateway.hessian.message.baofoo.B2GReqMsg_BaoFooCutpayment_Cutpayment;
import com.pinting.gateway.hessian.message.baofoo.B2GResMsg_BaoFooCutpayment_Cutpayment;
import com.pinting.gateway.hessian.message.loan.G2BReqMsg_Repay_RepayConfirm;
import com.pinting.gateway.out.service.BaoFooTransportService;
import net.sf.json.JSONObject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * Created by zhangbao on 2017/5/17.
 * 赞分期还款
 */
//@Aspect
//@Component
public class ZanRepayMockAspect {
    @Autowired
    private RepayPaymentService repayPaymentService;
    @Autowired
    private BaoFooTransportService springBaoFooTransportService;
    private Logger logger = LoggerFactory.getLogger(UserWithdrawMockAspect.class);

    @Pointcut("execution(public * com.pinting.business.accounting.loan.service.impl.RepayPaymentServiceImpl.repayConfirm(..))")
    public void repayCutPointcutZan(){}


    @Before(value = "repayCutPointcutZan()")
    public void mockZanBefore(JoinPoint joinPoint) {
        try {
            G2BReqMsg_Repay_RepayConfirm req = (G2BReqMsg_Repay_RepayConfirm)joinPoint.getArgs()[0];
            logger.info("repayCutPointcutZan赞分期还款：{}", JSONObject.fromObject(req));
        } catch (Exception e) {
            e.printStackTrace();
        }
        BaoFooTransportService mockBaoFooTransportService = Mockito.mock(BaoFooTransportService.class);
        ReflectionTestUtils.setField(AopTargetUtils.getTarget(repayPaymentService), "baoFooTransportService", mockBaoFooTransportService);
        B2GResMsg_BaoFooCutpayment_Cutpayment res = new B2GResMsg_BaoFooCutpayment_Cutpayment();
        res.setResCode("000000");
//        res.setResCode(LoanStatus.BAOFOO_PAY_STATUS_PROCESS.getCode());
        Mockito.doReturn(res).when(mockBaoFooTransportService).withholding(Mockito.any(B2GReqMsg_BaoFooCutpayment_Cutpayment.class));
    }

    @After(value = "repayCutPointcutZan()")
    public void mockAfter() {
        ReflectionTestUtils.setField(AopTargetUtils.getTarget(repayPaymentService), "baoFooTransportService", springBaoFooTransportService);
    }
}
