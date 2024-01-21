package com.pinting.business.mockito;

import com.pinting.business.accounting.loan.model.Withdraw2DepRepayCardReq;
import com.pinting.business.accounting.loan.service.DepFixedRepayPaymentService;
import com.pinting.business.accounting.loan.service.DepOfflineRepayService;
import com.pinting.business.accounting.loan.service.RepayPaymentService;
import com.pinting.gateway.hessian.message.baofoo.*;
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
 * 线下还款
 */
@Aspect
@Component
public class OfflineRepayMockAspect {
	
	@Autowired
	private RepayPaymentService repayPaymentService;
    @Autowired
    private DepFixedRepayPaymentService depFixedRepayPaymentService;
	@Autowired
    private BaoFooTransportService springBaoFooTransportService;

	@Autowired
	private DepOfflineRepayService depOfflineRepayService;
	private Logger logger = LoggerFactory.getLogger(UserWithdrawMockAspect.class);

	@Pointcut("execution(public * com.pinting.business.accounting.loan.service.impl.DepOfflineRepayServiceImpl.withdraw2DepRepayCard(..))")
	public void offlineRepayPointcut1(){}


	@Before(value = "offlineRepayPointcut1()")
	public void mockOffBefore1(JoinPoint joinPoint) {
		try {
			Withdraw2DepRepayCardReq req = (Withdraw2DepRepayCardReq)joinPoint.getArgs()[0];
			logger.info("offlineRepayPointcut1还款：{}", JSONObject.fromObject(req));
		} catch (Exception e) {
			e.printStackTrace();
		}
		BaoFooTransportService mockBaoFooTransportService = Mockito.mock(BaoFooTransportService.class);
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(depOfflineRepayService), "baoFooTransportService", mockBaoFooTransportService);
		B2GResMsg_BaoFooQuickPay_Pay4Trans res = new B2GResMsg_BaoFooQuickPay_Pay4Trans();
		res.setResCode("000000");
//		res.setResCode(LoanStatus.BAOFOO_PAY_STATUS_PROCESS.getCode());
//		res.setResCode("939999");
//		res.setResMsg("代付失败啦~~~~");
		Mockito.doReturn(res).when(mockBaoFooTransportService).pay4Trans(Mockito.any(B2GReqMsg_BaoFooQuickPay_Pay4Trans.class));
	}
	
	@After(value = "offlineRepayPointcut1()")
	public void mockAfter() {
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(depOfflineRepayService), "baoFooTransportService", springBaoFooTransportService);
	}
}
