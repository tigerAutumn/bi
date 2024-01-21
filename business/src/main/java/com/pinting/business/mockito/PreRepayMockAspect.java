package com.pinting.business.mockito;

import com.pinting.business.accounting.loan.service.RepayPaymentService;
import com.pinting.gateway.hessian.message.baofoo.B2GReqMsg_BaoFooQuickPay_QuickPay;
import com.pinting.gateway.hessian.message.baofoo.B2GReqMsg_BaoFooQuickPay_QuickPayConfirm;
import com.pinting.gateway.hessian.message.baofoo.B2GResMsg_BaoFooQuickPay_QuickPay;
import com.pinting.gateway.hessian.message.baofoo.B2GResMsg_BaoFooQuickPay_QuickPayConfirm;
import com.pinting.gateway.hessian.message.loan.G2BReqMsg_Repay_PreRepay;
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
 * 还款预下单
 */
@Aspect
@Component
public class PreRepayMockAspect {
	private Logger logger = LoggerFactory.getLogger(UserWithdrawMockAspect.class);

	@Autowired
	private RepayPaymentService repayPaymentService;
	
	@Autowired
    private BaoFooTransportService springBaoFooTransportService;
	
	@Pointcut("execution(public * com.pinting.business.accounting.loan.service.impl.RepayPaymentServiceImpl.preRepay(..))")
	public void preRepayPointcut(){}

	@Before(value = "preRepayPointcut()")
	public void mockBefore(JoinPoint joinPoint) {
		try {
			G2BReqMsg_Repay_PreRepay req = (G2BReqMsg_Repay_PreRepay)joinPoint.getArgs()[0];
			logger.info("preRepayPointcut还款预下单：{}", JSONObject.fromObject(req));
		} catch (Exception e) {
			e.printStackTrace();
		}
		BaoFooTransportService mockBaoFooTransportService = Mockito.mock(BaoFooTransportService.class);
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(repayPaymentService), "baoFooTransportService", mockBaoFooTransportService);
		B2GResMsg_BaoFooQuickPay_QuickPay res = new B2GResMsg_BaoFooQuickPay_QuickPay();
		res.setResCode("000000");
		Mockito.doReturn(res).when(mockBaoFooTransportService).quickPay(Mockito.any(B2GReqMsg_BaoFooQuickPay_QuickPay.class));
	}
	
	@After(value = "preRepayPointcut()")
	public void mockAfter() {
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(repayPaymentService), "baoFooTransportService", springBaoFooTransportService);
	}
}
