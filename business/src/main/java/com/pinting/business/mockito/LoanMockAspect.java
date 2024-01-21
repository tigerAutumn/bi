package com.pinting.business.mockito;

import com.pinting.business.accounting.loan.enums.LoanStatus;
import com.pinting.business.accounting.loan.service.LoanPaymentService;
import com.pinting.business.model.LnLoan;
import com.pinting.gateway.hessian.message.baofoo.B2GReqMsg_BaoFooQuickPay_Pay4Trans;
import com.pinting.gateway.hessian.message.baofoo.B2GResMsg_BaoFooQuickPay_Pay4Trans;
import com.pinting.gateway.hessian.message.loan.G2BReqMsg_Loan_Loan;
import com.pinting.gateway.hessian.message.loan.G2BReqMsg_Partner_MarketingTrans;
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
 * 借款
 */
@Aspect
@Component
public class LoanMockAspect {
	private Logger logger = LoggerFactory.getLogger(UserWithdrawMockAspect.class);

	@Autowired
	private LoanPaymentService loanPaymentService;
	
	@Autowired
    private BaoFooTransportService springBaoFooTransportService;
	
	@Pointcut("execution(public * com.pinting.business.accounting.loan.service.impl.LoanPaymentServiceImpl.loan(..))")
	public void loanPointcut(){}

	@Pointcut("execution(public * com.pinting.business.accounting.loan.service.impl.LoanPaymentServiceImpl.matchAndLoanPay(..))")
	public void matchAndLoanPayPointcut(){}



	@Before(value = "loanPointcut()")
	public void mockBefore(JoinPoint joinPoint) {
		try {
			G2BReqMsg_Loan_Loan req = (G2BReqMsg_Loan_Loan)joinPoint.getArgs()[0];
			logger.info("loanPointcut借款：{}", JSONObject.fromObject(req));
		} catch (Exception e) {
			e.printStackTrace();
		}
		BaoFooTransportService mockBaoFooTransportService = Mockito.mock(BaoFooTransportService.class);
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(loanPaymentService), "baoFooTransportService", mockBaoFooTransportService);
		B2GResMsg_BaoFooQuickPay_Pay4Trans res = new B2GResMsg_BaoFooQuickPay_Pay4Trans();
		res.setResCode("000000");
		Mockito.doReturn(res).when(mockBaoFooTransportService).pay4Trans(Mockito.any(B2GReqMsg_BaoFooQuickPay_Pay4Trans.class));
	}

	@Before(value = "matchAndLoanPayPointcut()")
	public void mockMatchAndLoanBefore(JoinPoint joinPoint) {
		try {
			LnLoan req = (LnLoan)joinPoint.getArgs()[0];
			logger.info("matchAndLoanPayPointcut借款：{}", JSONObject.fromObject(req));
		} catch (Exception e) {
			e.printStackTrace();
		}
		BaoFooTransportService mockBaoFooTransportService = Mockito.mock(BaoFooTransportService.class);
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(loanPaymentService), "baoFooTransportService", mockBaoFooTransportService);
		B2GResMsg_BaoFooQuickPay_Pay4Trans res = new B2GResMsg_BaoFooQuickPay_Pay4Trans();
		res.setResCode("000000");
//		res.setResCode(LoanStatus.BAOFOO_PAY_STATUS_PROCESS.getCode());
		Mockito.doReturn(res).when(mockBaoFooTransportService).pay4Trans(Mockito.any(B2GReqMsg_BaoFooQuickPay_Pay4Trans.class));
	}
	
	@After(value = "loanPointcut()")
	public void mockAfter() {
		//ReflectionTestUtils.setField(AopTargetUtils.getTarget(loanPaymentService), "baoFooTransportService", springBaoFooTransportService);
	}
}
