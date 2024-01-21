package com.pinting.business.mockito;

import com.pinting.business.accounting.finance.service.UserTopUpService;
import com.pinting.gateway.hessian.message.reapal.B2GReqMsg_ReapalQuickPay_BindCardSign;
import com.pinting.gateway.hessian.message.reapal.B2GReqMsg_ReapalQuickPay_MemoryCardSign;
import com.pinting.gateway.hessian.message.reapal.B2GResMsg_ReapalQuickPay_BindCardSign;
import com.pinting.gateway.hessian.message.reapal.B2GResMsg_ReapalQuickPay_MemoryCardSign;
import com.pinting.gateway.out.service.ReapalTransportService;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;
/**
 * 充值预下单 -宝付 -mock切面
 * @author Dragon & cat
 * @date 2016-9-22
 */
//@Aspect
//@Component
public class TopUpReapalPreMockAspect {


	@Autowired
	private UserTopUpService userTopUpService;
	
	@Autowired
    private ReapalTransportService springReapalTransportService;
	
	@Pointcut("execution(public * com.pinting.business.accounting.finance.service.impl.UserTopUpServiceImpl.pre(..))")
	public void TopUpReapalPre(){}
	
	@Before(value = "TopUpReapalPre()")
	public void mockBefore() {
		
		ReapalTransportService mockReapalTransportService = Mockito.mock(ReapalTransportService.class);
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(userTopUpService), "reapalService", mockReapalTransportService);
		
		B2GResMsg_ReapalQuickPay_BindCardSign res1 = new B2GResMsg_ReapalQuickPay_BindCardSign();
		res1.setResCode("000000");
		res1.setResMsg("交易成功");
		Mockito.doReturn(res1).when(mockReapalTransportService).bindCardSign(Mockito.any(B2GReqMsg_ReapalQuickPay_BindCardSign.class));
		
		B2GResMsg_ReapalQuickPay_MemoryCardSign res2 = new B2GResMsg_ReapalQuickPay_MemoryCardSign();
		res2.setResCode("000000");
		res2.setResMsg("交易成功");
		res2.setCertificate("1");
		Mockito.doReturn(res2).when(mockReapalTransportService).memoryCardSign(Mockito.any(B2GReqMsg_ReapalQuickPay_MemoryCardSign.class));
	}
	
	@After(value = "TopUpReapalPre()")
	public void mockAfter() {
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(userTopUpService), "reapalService", springReapalTransportService);
	}

}
