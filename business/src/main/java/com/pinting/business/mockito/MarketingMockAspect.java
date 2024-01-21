package com.pinting.business.mockito;

import com.pinting.business.accounting.loan.enums.LoanStatus;
import com.pinting.business.accounting.loan.service.PartnerTransService;
import com.pinting.gateway.hessian.message.baofoo.B2GReqMsg_BaoFooQuickPay_PartnerPay4Trans;
import com.pinting.gateway.hessian.message.baofoo.B2GResMsg_BaoFooQuickPay_PartnerPay4Trans;
import com.pinting.gateway.hessian.message.loan.G2BReqMsg_Partner_MarketingTrans;
import com.pinting.gateway.hessian.message.loan.G2BReqMsg_Repay_PreRepay;
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
 * 营销代付
 */
@Aspect
@Component
public class MarketingMockAspect {
	private Logger logger = LoggerFactory.getLogger(UserWithdrawMockAspect.class);

	@Autowired
	private PartnerTransService partnerTransService;
	
	@Autowired
    private BaoFooTransportService springBaoFooTransportService;
	
	@Pointcut("execution(public * com.pinting.business.accounting.loan.service.impl.PartnerTransServiceImpl.marketingTrans(..))")
	public void marketingPointcut(){}

	@Before(value = "marketingPointcut()")
	public void mockBefore(JoinPoint joinPoint) {
		try {
			G2BReqMsg_Partner_MarketingTrans req = (G2BReqMsg_Partner_MarketingTrans)joinPoint.getArgs()[0];
			logger.info("marketingPointcut营销代付：{}", JSONObject.fromObject(req));
		} catch (Exception e) {
			e.printStackTrace();
		}
		BaoFooTransportService mockBaoFooTransportService = Mockito.mock(BaoFooTransportService.class);
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(partnerTransService), "baoFooTransportService", mockBaoFooTransportService);
		B2GResMsg_BaoFooQuickPay_PartnerPay4Trans res = new B2GResMsg_BaoFooQuickPay_PartnerPay4Trans();
		res.setResCode("000000");
//        res.setResCode(LoanStatus.BAOFOO_PAY_STATUS_PROCESS.getCode());
		Mockito.doReturn(res).when(mockBaoFooTransportService).partnerPay4Trans(Mockito.any(B2GReqMsg_BaoFooQuickPay_PartnerPay4Trans.class));
	}
	
	@After(value = "marketingPointcut()")
	public void mockAfter() {
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(partnerTransService), "baoFooTransportService", springBaoFooTransportService);
	}
}
