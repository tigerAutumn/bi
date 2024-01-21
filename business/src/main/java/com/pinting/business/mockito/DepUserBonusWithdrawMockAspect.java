package com.pinting.business.mockito;

import com.alibaba.fastjson.JSONObject;
import com.pinting.business.accounting.finance.service.DepUserBonusWithdrawService;
import com.pinting.business.hessian.site.message.ReqMsg_UserBalance_Withdraw;
import com.pinting.business.util.Constants;
import com.pinting.gateway.hessian.message.baofoo.B2GReqMsg_BaoFooQuickPay_Pay4Trans;
import com.pinting.gateway.hessian.message.baofoo.B2GResMsg_BaoFooQuickPay_Pay4Trans;
import com.pinting.gateway.out.service.BaoFooTransportService;
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

import java.util.HashMap;
import java.util.Map;

/**
 * Author:      cyb
 * Date:        2017/4/19
 * Description:
 */
@Aspect
@Component
public class DepUserBonusWithdrawMockAspect {

    @Autowired
    private DepUserBonusWithdrawService depUserBonusWithdrawService;
    @Autowired
    private BaoFooTransportService springBaoFooTransportService;
    private Logger logger = LoggerFactory.getLogger(DepUserBonusWithdrawMockAspect.class);

    @Pointcut("execution(public * com.pinting.business.accounting.finance.service.impl.DepUserBonusWithdrawServiceImpl.userBonusWithdraw(..))")
    public void userBonusWithdrawPointcut(){}

    @Before(value = "userBonusWithdrawPointcut()")
    public void mockBefore(JoinPoint jp) {
        try {
            Object[] args = jp.getArgs();
            Map<Integer, Object> result = new HashMap<>();
            int i = 0;
            for (Object arg: args) {
                result.put(i, arg);
                i++;
            }
            logger.info("userBonusWithdrawPointcut奖励金提现入参：{}", result);
        } catch (Exception e) {
            e.printStackTrace();
        }

        BaoFooTransportService mockBaoFooTransportService = Mockito.mock(BaoFooTransportService.class);
        ReflectionTestUtils.setField(AopTargetUtils.getTarget(depUserBonusWithdrawService), "baoFooTransportService", mockBaoFooTransportService);
        B2GResMsg_BaoFooQuickPay_Pay4Trans res = new B2GResMsg_BaoFooQuickPay_Pay4Trans();
        res.setResCode("000000");
//        res.setResCode(Constants.GATEWAY_PAYING_RES_CODE);
//        res.setResMsg("奖励金提现-测试mock处理中");
//        res.setResCode(Constants.GATEWAY_PAYING_RES_CODE);

//        res.setResCode("980999");
//        res.setResMsg("mock失败");
        Mockito.doReturn(res).when(mockBaoFooTransportService).pay4Trans(Mockito.any(B2GReqMsg_BaoFooQuickPay_Pay4Trans.class));
    }

    @After(value = "userBonusWithdrawPointcut()")
    public void mockAfter() {
        ReflectionTestUtils.setField(AopTargetUtils.getTarget(depUserBonusWithdrawService), "baoFooTransportService", springBaoFooTransportService);
    }

}

