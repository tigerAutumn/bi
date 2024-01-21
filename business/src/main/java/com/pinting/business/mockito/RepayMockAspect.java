package com.pinting.business.mockito;

import com.pinting.business.accounting.loan.enums.LoanStatus;
import com.pinting.business.accounting.loan.service.DepFixedRepayPaymentService;
import com.pinting.business.accounting.loan.service.DepOfflineRepayService;
import com.pinting.business.accounting.loan.service.RepayPaymentService;
import com.pinting.business.model.BsBatchReturnDetail;
import com.pinting.gateway.hessian.message.baofoo.*;
import com.pinting.gateway.hessian.message.dafy.G2BReqMsg_DafyLoan_ActiveRepayConfirm;
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
 * 还款正式下单
 */
//@Aspect
//@Component
public class RepayMockAspect {
	
	@Autowired
	private RepayPaymentService repayPaymentService;
    @Autowired
    private DepFixedRepayPaymentService depFixedRepayPaymentService;
	@Autowired
    private BaoFooTransportService springBaoFooTransportService;

	@Autowired
	private DepOfflineRepayService depOfflineRepayService;
	private Logger logger = LoggerFactory.getLogger(UserWithdrawMockAspect.class);

	@Pointcut("execution(public * com.pinting.business.accounting.loan.service.impl.RepayPaymentServiceImpl.repayConfirm(..))")
	public void repayPointcut(){}
	
    @Pointcut("execution(public * com.pinting.business.accounting.loan.service.impl.DepFixedRepayPaymentServiceImpl.doRepayConfirm(..))")
    public void depFixedRepayPointcut(){}

	@Before(value = "repayPointcut()")
	public void mockBefore(JoinPoint joinPoint) {
		try {
			G2BReqMsg_Repay_RepayConfirm req = (G2BReqMsg_Repay_RepayConfirm)joinPoint.getArgs()[0];
			logger.info("repayPointcut还款：{}", JSONObject.fromObject(req));
		} catch (Exception e) {
			e.printStackTrace();
		}
		BaoFooTransportService mockBaoFooTransportService = Mockito.mock(BaoFooTransportService.class);
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(repayPaymentService), "baoFooTransportService", mockBaoFooTransportService);
		B2GResMsg_BaoFooCutpayment_Cutpayment res = new B2GResMsg_BaoFooCutpayment_Cutpayment();
		res.setResCode("000000");
//		res.setResCode(LoanStatus.BAOFOO_PAY_STATUS_PROCESS.getCode());

//		res.setResCode(LoanStatus.BAOFOO_PAY_STATUS_PROCESS.getCode());
//		res.setResCode("939999");
//		res.setResMsg("还款失败啦~~~~");
		Mockito.doReturn(res).when(mockBaoFooTransportService).withholding(Mockito.any(B2GReqMsg_BaoFooCutpayment_Cutpayment.class));
	}

	@Before(value = "depFixedRepayPointcut()")
	public void mockIncrBefore(JoinPoint joinPoint) {
		try {
			G2BReqMsg_DafyLoan_ActiveRepayConfirm req = (G2BReqMsg_DafyLoan_ActiveRepayConfirm)joinPoint.getArgs()[0];
			logger.info("depFixedRepayPointcut：{}", JSONObject.fromObject(req));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		BaoFooTransportService mockBaoFooTransportService = Mockito.mock(BaoFooTransportService.class);
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(depFixedRepayPaymentService), "baoFooTransportService", mockBaoFooTransportService);
		B2GResMsg_BaoFooCutpayment_Cutpayment res = new B2GResMsg_BaoFooCutpayment_Cutpayment();
		res.setResCode("000000");
		res.setResMsg("代扣");
//		res.setResCode(LoanStatus.BAOFOO_PAY_STATUS_PROCESS.getCode());
		Mockito.doReturn(res).when(mockBaoFooTransportService).withholding(Mockito.any(B2GReqMsg_BaoFooCutpayment_Cutpayment.class));
		
		//协议支付
		B2GResMsg_BaoFooAgreementPay_DirectAgreementPay agreemRes = new B2GResMsg_BaoFooAgreementPay_DirectAgreementPay();
		agreemRes.setResCode("000000");
		agreemRes.setResMsg("协议支付");
		//agreemRes.setResCode(LoanStatus.BAOFOO_PAY_STATUS_PROCESS.getCode());
		Mockito.doReturn(agreemRes).when(mockBaoFooTransportService).directAgreementPay(Mockito.any(B2GReqMsg_BaoFooAgreementPay_DirectAgreementPay.class));
	}
	
	@After(value = "repayPointcut()")
	public void mockAfter() {
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(repayPaymentService), "baoFooTransportService", springBaoFooTransportService);
	}
}
