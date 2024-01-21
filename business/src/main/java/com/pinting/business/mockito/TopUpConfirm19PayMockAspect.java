package com.pinting.business.mockito;

import com.pinting.business.accounting.finance.service.UserTopUpService;
import com.pinting.core.util.ConstantUtil;
import com.pinting.gateway.hessian.message.pay19.B2GReqMsg_QuickPay_ConfirmOrder;
import com.pinting.gateway.hessian.message.pay19.B2GResMsg_QuickPay_ConfirmOrder;
import com.pinting.gateway.hessian.message.pay19.enums.TradeResult;
import com.pinting.gateway.out.service.Pay19TransportService;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * 充值正式下单-19付-mock切面
 * @author bianyatian
 * @2016-9-22 下午8:27:38
 */
@Aspect
@Component
public class TopUpConfirm19PayMockAspect {

	@Autowired
	private UserTopUpService userTopUpService;
	
	@Autowired
    private Pay19TransportService springPay19TransportService;
	
	@Pointcut("execution(public * com.pinting.business.accounting.finance.service.impl.UserTopUpServiceImpl.confirm(..))")
	public void topUpConfirm19Pay(){}

	@Before(value = "topUpConfirm19Pay()")
	public void mockBefore() {
		Pay19TransportService mockPay19TransportService = Mockito.mock(Pay19TransportService.class);
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(userTopUpService), "pay19TransportService", mockPay19TransportService);
		B2GResMsg_QuickPay_ConfirmOrder res = new B2GResMsg_QuickPay_ConfirmOrder();
		res.setResCode(ConstantUtil.BSRESCODE_SUCCESS);
		res.setTradeResult(TradeResult.SUCCESS);
		Mockito.doReturn(res).when(mockPay19TransportService).confirmOrder(Mockito.any(B2GReqMsg_QuickPay_ConfirmOrder.class));
	}
	
	@After(value = "topUpConfirm19Pay()")
	public void mockAfter() {
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(userTopUpService), "pay19TransportService", springPay19TransportService);
	}
}
