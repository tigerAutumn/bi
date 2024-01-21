package com.pinting.business.mockito;

import com.pinting.business.accounting.loan.service.DepFixedRevenueSettleService;
import com.pinting.gateway.hessian.message.baofoo.B2GReqMsg_BaoFooQuickPay_Pay4OnlineTrans;
import com.pinting.gateway.hessian.message.baofoo.B2GResMsg_BaoFooQuickPay_Pay4OnlineTrans;
import com.pinting.gateway.out.service.BaoFooTransportService;
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
 * 达飞自主-系统营收结算-宝付钱包转账（系统）
 * @author bianyatian
 * @2017-2-15 下午3:17:06
 */
//@Aspect
//@Component
public class DepRepeatMockAspect {

	@Autowired
	private DepFixedRevenueSettleService depFixedRevenueSettleService;
	
	@Autowired
    private BaoFooTransportService springBaoFooTransportService;
	private Logger logger = LoggerFactory.getLogger(UserWithdrawMockAspect.class);

	
	@Pointcut("execution(public * com.pinting.business.accounting.loan.service.impl.DepFixedRevenueSettleServiceImpl.repayRepeatSettle(..))")
	public void baofooPay4OnlineTrans(){}
	@Pointcut("execution(public * com.pinting.business.accounting.loan.service.impl.DepFixedRevenueSettleServiceImpl.sevenRepayRepeatSettle(..))")
	public void baofooPay4SevenOnlineTrans(){}


	@Before(value = "baofooPay4OnlineTrans()")
	public void mockBefore(JoinPoint joinPoint) {
		BaoFooTransportService mockBaoFooTransportService = Mockito.mock(BaoFooTransportService.class);
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(depFixedRevenueSettleService), "baoFooTransportService", mockBaoFooTransportService);
		B2GResMsg_BaoFooQuickPay_Pay4OnlineTrans res = new B2GResMsg_BaoFooQuickPay_Pay4OnlineTrans();
		res.setResCode("000000");
		res.setResMsg("交易成功");
//		res.setState("0");//转账中
		res.setState("1");//转账成功
		//res.setState("-1");//转账失败
		//res.setState("2");//转账退款
		Mockito.doReturn(res).when(mockBaoFooTransportService).pay4OnlineTrans(Mockito.any(B2GReqMsg_BaoFooQuickPay_Pay4OnlineTrans.class));
	}


	
	@After(value = "baofooPay4OnlineTrans()")
	public void mockAfter() {
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(depFixedRevenueSettleService), "baoFooTransportService", springBaoFooTransportService);
	}

	@Before(value = "baofooPay4SevenOnlineTrans()")
	public void mockBaofooPay4SevenOnlineTransBefore(JoinPoint joinPoint) {
		BaoFooTransportService mockBaoFooTransportService = Mockito.mock(BaoFooTransportService.class);
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(depFixedRevenueSettleService), "baoFooTransportService", mockBaoFooTransportService);
		B2GResMsg_BaoFooQuickPay_Pay4OnlineTrans res = new B2GResMsg_BaoFooQuickPay_Pay4OnlineTrans();
		res.setResCode("000000");
		res.setResMsg("交易成功");
//		res.setState("0");//转账中
		res.setState("1");//转账成功
		//res.setState("-1");//转账失败
		//res.setState("2");//转账退款
		Mockito.doReturn(res).when(mockBaoFooTransportService).pay4OnlineTrans(Mockito.any(B2GReqMsg_BaoFooQuickPay_Pay4OnlineTrans.class));
	}



	@After(value = "baofooPay4SevenOnlineTrans()")
	public void mockBaofooPay4SevenOnlineTransAfter() {
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(depFixedRevenueSettleService), "baoFooTransportService", springBaoFooTransportService);
	}
}
