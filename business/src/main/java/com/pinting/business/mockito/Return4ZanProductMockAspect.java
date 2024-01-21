package com.pinting.business.mockito;

import com.pinting.business.accounting.loan.service.FinanceReceiveMoneyService;
import com.pinting.business.model.BsLoanFinanceRepay;
import com.pinting.business.model.BsRevenueTransRecord;
import com.pinting.core.util.ConstantUtil;
import com.pinting.gateway.hessian.message.baofoo.B2GReqMsg_BaoFooQuickPay_Pay4Trans;
import com.pinting.gateway.hessian.message.baofoo.B2GResMsg_BaoFooQuickPay_Pay4Trans;
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
 * 回款-分期产品
 * @author bianyatian
 * @2016-10-22 下午2:14:37
 */
@Aspect
@Component
public class Return4ZanProductMockAspect {
	
	@Autowired
	private FinanceReceiveMoneyService financeReceiveMoneyService;
	@Autowired
    private BaoFooTransportService springBaoFooTransportService;
	private Logger logger = LoggerFactory.getLogger(UserWithdrawMockAspect.class);

	@Pointcut("execution(public * com.pinting.business.accounting.loan.service.impl.FinanceReceiveMoneyServiceImpl.receiveMoney2Card(..))")
	public void baofooZanReturn(){}
	
	@Before(value = "baofooZanReturn()")
	public void mockBefore(JoinPoint joinPoint) {
		try {
			BsLoanFinanceRepay req = (BsLoanFinanceRepay)joinPoint.getArgs()[0];
			logger.info("baofooZanReturn回款-分期产品：{}", JSONObject.fromObject(req));
		} catch (Exception e) {
			e.printStackTrace();
		}
		BaoFooTransportService mockBaoFooTransportService = Mockito.mock(BaoFooTransportService.class);
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(financeReceiveMoneyService), "baoFooTransportService", mockBaoFooTransportService);
		B2GResMsg_BaoFooQuickPay_Pay4Trans res = new B2GResMsg_BaoFooQuickPay_Pay4Trans();
		res.setResCode(ConstantUtil.BSRESCODE_SUCCESS);
		res.setResMsg("交易成功");
		Mockito.doReturn(res).when(mockBaoFooTransportService).pay4Trans(Mockito.any(B2GReqMsg_BaoFooQuickPay_Pay4Trans.class));
	}
	
	@After(value = "baofooZanReturn()")
	public void mockAfter() {
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(financeReceiveMoneyService), "baoFooTransportService", springBaoFooTransportService);
	}


	
}
