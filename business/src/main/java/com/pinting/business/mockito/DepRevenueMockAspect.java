package com.pinting.business.mockito;

import com.pinting.business.accounting.loan.service.DepFixedRevenueSettleService;
import com.pinting.gateway.hessian.message.baofoo.B2GReqMsg_BaoFooQuickPay_Pay4OnlineTrans;
import com.pinting.gateway.hessian.message.baofoo.B2GResMsg_BaoFooQuickPay_Pay4OnlineTrans;
import com.pinting.gateway.hessian.message.loan.G2BReqMsg_Loan_Loan;
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
 * 达飞自主-系统营收结算-宝付钱包转账（系统）
 * @author bianyatian
 * @2017-2-15 下午3:17:06
 */
//@Aspect
//@Component
public class DepRevenueMockAspect {

	@Autowired
	private DepFixedRevenueSettleService depFixedRevenueSettleService;
	
	@Autowired
    private BaoFooTransportService springBaoFooTransportService;
	private Logger logger = LoggerFactory.getLogger(UserWithdrawMockAspect.class);

	
	@Pointcut("execution(public * com.pinting.business.accounting.loan.service.impl.DepFixedRevenueSettleServiceImpl.revenueSettle(..))")
	public void baofooPay4OnlineTrans(){}
	@Pointcut("execution(public * com.pinting.business.accounting.loan.service.impl.DepFixedRevenueSettleServiceImpl.repayRepeatSettle(..))")
	public void baofooPay4OnlineTransForRepayRepeatSettle(){}
	
	@Pointcut("execution(public * com.pinting.business.accounting.loan.service.impl.DepFixedRevenueSettleServiceImpl.zsdRevenueSettle(..))")
	public void baofooPay4ZsdOnlineTrans(){}
	@Pointcut("execution(public * com.pinting.business.accounting.loan.service.impl.DepFixedRevenueSettleServiceImpl.zsdRepayRepeatSettle(..))")
	public void baofooPay4ZsdRepayOnlineTrans(){}
	@Pointcut("execution(public * com.pinting.business.accounting.loan.service.impl.DepFixedRevenueSettleServiceImpl.sevenDaiRevenueSettle(..))")
	public void baofooPay4SevenRepayOnlineTrans(){}
	
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
	
	@Before(value = "baofooPay4OnlineTransForRepayRepeatSettle()")
	public void mockBeforeForRepayRepeatSettle(JoinPoint joinPoint) {
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

	@After(value = "baofooPay4OnlineTransForRepayRepeatSettle()")
	public void mockAfterForRepayRepeatSettle() {
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(depFixedRevenueSettleService), "baoFooTransportService", springBaoFooTransportService);
	}
	
	
	@Before(value = "baofooPay4ZsdOnlineTrans()")
	public void zsdMockBefore(JoinPoint joinPoint) {
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

	@After(value = "baofooPay4ZsdOnlineTrans()")
	public void zsdMockAfter() {
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(depFixedRevenueSettleService), "baoFooTransportService", springBaoFooTransportService);
	}
	
	@Before(value = "baofooPay4ZsdRepayOnlineTrans()")
	public void zsdRepayMockBefore(JoinPoint joinPoint) {
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

	@After(value = "baofooPay4ZsdRepayOnlineTrans()")
	public void zsdRepayMockAfter() {
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(depFixedRevenueSettleService), "baoFooTransportService", springBaoFooTransportService);
	}


	@Before(value = "baofooPay4SevenRepayOnlineTrans()")
	public void sevenRepayMockBefore(JoinPoint joinPoint) {
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

	@After(value = "baofooPay4SevenRepayOnlineTrans()")
	public void sevenRepayMockAfter() {
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(depFixedRevenueSettleService), "baoFooTransportService", springBaoFooTransportService);
	}

}
