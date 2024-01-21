package com.pinting.business.mockito;

import com.pinting.business.accounting.finance.service.UserTopUpService;
import com.pinting.core.util.ConstantUtil;
import com.pinting.gateway.hessian.message.reapal.B2GReqMsg_ReapalQuickPay_SubmitPay;
import com.pinting.gateway.hessian.message.reapal.B2GResMsg_ReapalQuickPay_SubmitPay;
import com.pinting.gateway.out.service.ReapalTransportService;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * 充值正式下单-融宝-mock切面
 * @author bianyatian
 * @2016-9-22 下午9:05:20
 */
@Aspect
@Component
public class TopUpConfirmReapalMockAspect {

	@Autowired
	private UserTopUpService userTopUpService;
	
	@Autowired
	private ReapalTransportService springReapalService;
	
	@Pointcut("execution(public * com.pinting.business.accounting.finance.service.impl.UserTopUpServiceImpl.confirm(..))")
	public void topUpConfirmReapalPay(){}

	@Before(value = "topUpConfirmReapalPay()")
	public void mockBefore() {
		ReapalTransportService mockReapalService = Mockito.mock(ReapalTransportService.class);
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(userTopUpService), "reapalService", mockReapalService);
		B2GResMsg_ReapalQuickPay_SubmitPay res = new B2GResMsg_ReapalQuickPay_SubmitPay();
		res.setResCode(ConstantUtil.BSRESCODE_SUCCESS);
		Mockito.doReturn(res).when(mockReapalService).submitPay(Mockito.any(B2GReqMsg_ReapalQuickPay_SubmitPay.class));
	}
	
	@After(value = "topUpConfirmReapalPay()")
	public void mockAfter() {
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(userTopUpService), "reapalService", springReapalService);
	}
}
