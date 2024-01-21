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
import com.pinting.gateway.hessian.message.hfbank.B2GReqMsg_HFBank_PlatformTransfer;
import com.pinting.gateway.hessian.message.hfbank.B2GResMsg_HFBank_PlatformTransfer;
import com.pinting.gateway.out.service.HfbankTransportService;
/**
*
* @author SHENGP
* @date  2017年11月10日 下午1:22:50
*/
//@Aspect
//@Component
public class DepSysTransferToPersonMockAspect {
	

	@Autowired
	private BsHfBankService bsHfBankService;
	
	@Autowired
	private HfbankTransportService hfbankTransportService;
	
	@Pointcut("execution(public * com.pinting.business.service.manage.impl.BsHfBankServiceImpl.sysAccountTransferToPerson(..))")
	public void sysAccountTransferToPerson(){}
	
	@Before(value = "sysAccountTransferToPerson()")
	public void mockBefore() {
		HfbankTransportService hfbankTransportService = Mockito.mock(HfbankTransportService.class);
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(bsHfBankService), "hfbankTransportService", hfbankTransportService);
		B2GResMsg_HFBank_PlatformTransfer res = new B2GResMsg_HFBank_PlatformTransfer();
		res.setRecode("10000");
		res.setResCode("10000");
		res.setResMsg("成功");
		Mockito.doReturn(res).when(hfbankTransportService).platformTransfer(Mockito.any(B2GReqMsg_HFBank_PlatformTransfer.class));
	}
	
	@After(value = "sysAccountTransferToPerson()")
	public void mockAfter() {
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(bsHfBankService), "hfbankTransportService", hfbankTransportService);
	}
	
	
}
