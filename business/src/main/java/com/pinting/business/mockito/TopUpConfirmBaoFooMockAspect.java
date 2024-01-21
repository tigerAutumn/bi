package com.pinting.business.mockito;

import com.pinting.business.accounting.finance.service.UserTopUpService;
import com.pinting.business.accounting.loan.enums.LoanStatus;
import com.pinting.core.util.ConstantUtil;
import com.pinting.gateway.hessian.message.baofoo.B2GReqMsg_BaoFooQuickPay_QuickPayConfirm;
import com.pinting.gateway.hessian.message.baofoo.B2GResMsg_BaoFooQuickPay_QuickPayConfirm;
import com.pinting.gateway.out.service.BaoFooTransportService;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.util.ReflectionTestUtils;
/**
 * 充值正式下单-宝付-mock切面
 * @author bianyatian
 * @2016-9-22 下午9:05:03
 */
@Aspect
@Component
public class TopUpConfirmBaoFooMockAspect {
	
	@Autowired
	private UserTopUpService userTopUpService;
	
	@Autowired
	private BaoFooTransportService springBaoFooTransportService;
	
	@Pointcut("execution(public * com.pinting.business.accounting.finance.service.impl.UserTopUpServiceImpl.confirm(..))")
	public void topUpConfirmBaofooPay(){}

	@Before(value = "topUpConfirmBaofooPay()")
	public void mockBefore() {
		BaoFooTransportService mockBaoFooTransportService = Mockito.mock(BaoFooTransportService.class);
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(userTopUpService), "baoFooTransportService", mockBaoFooTransportService);
		B2GResMsg_BaoFooQuickPay_QuickPayConfirm res = new B2GResMsg_BaoFooQuickPay_QuickPayConfirm();
		res.setResCode(ConstantUtil.BSRESCODE_SUCCESS);
		Mockito.doReturn(res).when(mockBaoFooTransportService).quickPayConfirm(Mockito.any(B2GReqMsg_BaoFooQuickPay_QuickPayConfirm.class));
	}
	
	@After(value = "topUpConfirmBaofooPay()")
	public void mockAfter() {
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(userTopUpService), "baoFooTransportService", springBaoFooTransportService);
	}

}
