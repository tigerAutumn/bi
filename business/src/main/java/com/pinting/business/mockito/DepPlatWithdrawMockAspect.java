package com.pinting.business.mockito;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.util.ReflectionTestUtils;

import com.pinting.business.service.manage.BsHfBankService;
import com.pinting.gateway.hessian.message.hfbank.B2GReqMsg_HFBank_PlatWithDraw;
import com.pinting.gateway.hessian.message.hfbank.B2GResMsg_HFBank_PlatWithDraw;
import com.pinting.gateway.out.service.HfbankTransportService;

/**
 *
 * @author SHENGP
 * @date  2017年5月11日 上午9:31:12
 */
//@Aspect
//@Component
public class DepPlatWithdrawMockAspect {

	@Autowired
	private BsHfBankService bsHfBankService;
	
	@Autowired
	private HfbankTransportService hfbankTransportService;
	
	@Pointcut("execution(public * com.pinting.business.service.manage.impl.BsHfBankServiceImpl.sysWithdraw(..))")
	public void sysWithdraw(){}
	
	@Before(value = "sysWithdraw()")
	public void mockBefore() {
		HfbankTransportService hfbankTransportService = Mockito.mock(HfbankTransportService.class);
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(bsHfBankService), "hfbankTransportService", hfbankTransportService);
		B2GResMsg_HFBank_PlatWithDraw res = new B2GResMsg_HFBank_PlatWithDraw();
		res.setRecode("999999");
		res.setResCode("999999");
		res.setResMsg("失败");
		
		Mockito.doReturn(res).when(hfbankTransportService).platWithDraw(Mockito.any(B2GReqMsg_HFBank_PlatWithDraw.class));
	}
	
	@After(value = "sysWithdraw()")
	public void mockAfter() {
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(bsHfBankService), "hfbankTransportService", hfbankTransportService);
	}
	
}
