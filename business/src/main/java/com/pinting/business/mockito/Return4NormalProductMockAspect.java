package com.pinting.business.mockito;

import com.pinting.business.accounting.finance.service.UserReturnMoneyService;
import com.pinting.business.accounting.loan.model.Withdraw2DepRepayCardReq;
import com.pinting.business.model.BsBatchReturnDetail;
import com.pinting.business.model.BsLoanFinanceRepay;
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
 * 回款-普通产品
 * @author bianyatian
 * @2016-10-22 下午2:04:16
 */
@Aspect
@Component
public class Return4NormalProductMockAspect {

	@Autowired
	private UserReturnMoneyService userReturnMoneyService;
	
	@Autowired
    private BaoFooTransportService springBaoFooTransportService;
	private Logger logger = LoggerFactory.getLogger(UserWithdrawMockAspect.class);

	@Pointcut("execution(public * com.pinting.business.accounting.finance.service.impl.UserReturnMoneyServiceImpl.return2Card(..))")
	public void baofooNormalReturn(){}
	
	@Before(value = "baofooNormalReturn()")
	public void mockBefore(JoinPoint joinPoint) {
		try {
			Withdraw2DepRepayCardReq req = (Withdraw2DepRepayCardReq)joinPoint.getArgs()[0];
			logger.info("baofooNormalReturn回款-普通产品：{}", JSONObject.fromObject(req));
		} catch (Exception e) {
			e.printStackTrace();
		}
		BaoFooTransportService mockBaoFooTransportService = Mockito.mock(BaoFooTransportService.class);
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(userReturnMoneyService), "baoFooTransportService", mockBaoFooTransportService);
		B2GResMsg_BaoFooQuickPay_Pay4Trans res = new B2GResMsg_BaoFooQuickPay_Pay4Trans();
		res.setResCode(ConstantUtil.BSRESCODE_SUCCESS);
		res.setResMsg("交易成功");
		Mockito.doReturn(res).when(mockBaoFooTransportService).pay4Trans(Mockito.any(B2GReqMsg_BaoFooQuickPay_Pay4Trans.class));
	}
	
	@After(value = "baofooNormalReturn()")
	public void mockAfter() {
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(userReturnMoneyService), "baoFooTransportService", springBaoFooTransportService);
	}


}
