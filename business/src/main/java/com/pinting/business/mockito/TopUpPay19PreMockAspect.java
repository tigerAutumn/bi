package com.pinting.business.mockito;

import com.pinting.business.accounting.finance.service.UserTopUpService;
import com.pinting.gateway.hessian.message.pay19.B2GReqMsg_QuickPay_PreOrder;
import com.pinting.gateway.hessian.message.pay19.B2GResMsg_QuickPay_PreOrder;
import com.pinting.gateway.out.service.Pay19TransportService;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Date;
/**
 * 充值预下单 -19付 -mock切面
 * @author Dragon & cat
 * @date 2016-9-22
 */
//@Aspect
//@Component
public class TopUpPay19PreMockAspect {


	@Autowired
	private UserTopUpService userTopUpService;
	
	@Autowired
    private Pay19TransportService springpPay19TransportService;
	
	@Pointcut("execution(public * com.pinting.business.accounting.finance.service.impl.UserTopUpServiceImpl.pre(..))")
	public void pay19PreOrder(){}
	
	@Before(value = "pay19PreOrder()")
	public void mockBefore() {
		
		Pay19TransportService mockPay19TransportService = Mockito.mock(Pay19TransportService.class);
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(userTopUpService), "pay19TransportService", mockPay19TransportService);
		
		B2GResMsg_QuickPay_PreOrder res = new B2GResMsg_QuickPay_PreOrder();
		res.setOrderId("1234");
		res.setMpOrderId("1111");
		res.setOrderDate(new Date());
		res.setResCode("000000");
		res.setResMsg("交易成功");
		Mockito.doReturn(res).when(mockPay19TransportService).preOrder(Mockito.any(B2GReqMsg_QuickPay_PreOrder.class));
	}
	
	@After(value = "pay19PreOrder()")
	public void mockAfter() {
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(userTopUpService), "pay19TransportService", springpPay19TransportService);
	}



}
