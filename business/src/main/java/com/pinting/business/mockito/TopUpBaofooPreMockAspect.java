package com.pinting.business.mockito;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.util.ReflectionTestUtils;

import com.pinting.business.accounting.finance.service.UserTopUpService;
import com.pinting.gateway.hessian.message.baofoo.B2GReqMsg_BaoFooQuickPay_QuickPay;
import com.pinting.gateway.hessian.message.baofoo.B2GResMsg_BaoFooQuickPay_QuickPay;
import com.pinting.gateway.out.service.BaoFooTransportService;

/**
 * 充值预下单 -宝付 -mock切面
 * @author Dragon & cat
 * @date 2016-9-22
 */
@Aspect
@Component
public class TopUpBaofooPreMockAspect {

	@Autowired
	private UserTopUpService userTopUpService;
	
	@Autowired
    private BaoFooTransportService springBaoFooTransportService;
	
	@Pointcut("execution(public * com.pinting.business.accounting.finance.service.impl.UserTopUpServiceImpl.pre(..))")
	public void baofooPreTopUpPointcut(){}
	
	@Before(value = "baofooPreTopUpPointcut()")
	public void mockBefore() {
		BaoFooTransportService mockBaoFooTransportService = Mockito.mock(BaoFooTransportService.class);
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(userTopUpService), "baoFooTransportService", mockBaoFooTransportService);
		B2GResMsg_BaoFooQuickPay_QuickPay res = new B2GResMsg_BaoFooQuickPay_QuickPay();
		res.setResCode("000000");
		res.setResMsg("交易成功");
		Mockito.doReturn(res).when(mockBaoFooTransportService).quickPay(Mockito.any(B2GReqMsg_BaoFooQuickPay_QuickPay.class));
	}
	
	@After(value = "baofooPreTopUpPointcut()")
	public void mockAfter() {
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(userTopUpService), "baoFooTransportService", springBaoFooTransportService);
	}


}
