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
import com.pinting.gateway.hessian.message.hfbank.B2GReqMsg_HFBank_PlatformAccountConverse;
import com.pinting.gateway.hessian.message.hfbank.B2GResMsg_HFBank_PlatformAccountConverse;
import com.pinting.gateway.out.service.HfbankTransportService;

/**
 *
 * @author SHENGP
 * @date  2017年6月13日 下午4:49:59
 */
//@Aspect
//@Component
public class DepSysAccountTransferNoChargeMockAspect {

	@Autowired
	private BsHfBankService bsHfBankService;
	
	@Autowired
	private HfbankTransportService hfbankTransportService;
	
	@Pointcut("execution(public * com.pinting.business.service.manage.impl.BsHfBankServiceImpl.sysAccountTransferNoCharge(..))")
	public void sysAccountTransfer(){}
	
	@Before(value = "sysAccountTransfer()")
	public void mockBefore() {
		HfbankTransportService hfbankTransportService = Mockito.mock(HfbankTransportService.class);
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(bsHfBankService), "hfbankTransportService", hfbankTransportService);
		B2GResMsg_HFBank_PlatformAccountConverse res = new B2GResMsg_HFBank_PlatformAccountConverse();
		/*res.setRecode("999999");
		res.setResCode("999999");
		res.setResMsg("失败");*/
		res.setResCode("000000");
		res.setRecode("000000");
		Mockito.doReturn(res).when(hfbankTransportService).platformAccountConverse(Mockito.any(B2GReqMsg_HFBank_PlatformAccountConverse.class));
	}
	
	@After(value = "sysAccountTransfer()")
	public void mockAfter() {
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(bsHfBankService), "hfbankTransportService", hfbankTransportService);
	}
	
}
