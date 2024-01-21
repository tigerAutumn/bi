package com.pinting.business.mockito;

import com.pinting.business.accounting.loan.enums.LoanStatus;
import com.pinting.business.accounting.loan.service.SysDailyBizService;
import com.pinting.business.model.BsRevenueTransRecord;
import com.pinting.gateway.hessian.message.baofoo.B2GReqMsg_BaoFooQuickPay_Pay4OnlineTrans;
import com.pinting.gateway.hessian.message.baofoo.B2GResMsg_BaoFooQuickPay_Pay4OnlineTrans;
import com.pinting.gateway.hessian.message.loan.G2BReqMsg_Repay_RepayConfirm;
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
 * 营收结算
 */
@Aspect
@Component
public class RevenuePlanMockAspect {
	
	@Autowired
	private SysDailyBizService sysDailyBizService;
	
	@Autowired
    private BaoFooTransportService springBaoFooTransportService;
	private Logger logger = LoggerFactory.getLogger(UserWithdrawMockAspect.class);

	@Pointcut("execution(public * com.pinting.business.accounting.loan.service.impl.SysDailyBizServiceImpl.transDailyPartnerRevenue(..))")
	public void revenuePlanPointcut(){}

	@Before(value = "revenuePlanPointcut()")
	public void mockBefore(JoinPoint joinPoint) {
		try {
			BsRevenueTransRecord req = (BsRevenueTransRecord)joinPoint.getArgs()[0];
			logger.info("revenuePlanPointcut营收结算：{}", JSONObject.fromObject(req));
		} catch (Exception e) {
			e.printStackTrace();
		}
		BaoFooTransportService mockBaoFooTransportService = Mockito.mock(BaoFooTransportService.class);
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(sysDailyBizService), "baoFooTransportService", mockBaoFooTransportService);
		B2GResMsg_BaoFooQuickPay_Pay4OnlineTrans res = new B2GResMsg_BaoFooQuickPay_Pay4OnlineTrans();
		res.setResCode("000000");
		res.setResMsg("交易成功");
//		res.setState("0");//转账中
		res.setState("1");//转账成功
		//res.setState("-1");//转账失败
		//res.setState("2");//转账退款
		Mockito.doReturn(res).when(mockBaoFooTransportService).pay4OnlineTrans(Mockito.any(B2GReqMsg_BaoFooQuickPay_Pay4OnlineTrans.class));
	}
	
	@After(value = "revenuePlanPointcut()")
	public void mockAfter() {
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(sysDailyBizService), "baoFooTransportService", springBaoFooTransportService);
	}
}
