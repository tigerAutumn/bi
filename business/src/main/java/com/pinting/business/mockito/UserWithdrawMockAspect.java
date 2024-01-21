package com.pinting.business.mockito;

import com.pinting.business.accounting.finance.service.UserBalanceWithdrawService;
import com.pinting.business.accounting.loan.enums.LoanStatus;
import com.pinting.business.hessian.manage.ReqMsg_UserBalance_WithdrawPass;
import com.pinting.business.hessian.site.message.ReqMsg_UserBalance_Withdraw;
import com.pinting.business.util.Constants;
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

import java.util.Map;

/**
 * 用户提现-mock-切面
 * @author bianyatian
 * @2016-9-22 下午8:27:07
 */
@Aspect
@Component
public class UserWithdrawMockAspect {
	
	@Autowired
	private UserBalanceWithdrawService userBalanceWithdrawService;

	@Autowired
	private BaoFooTransportService springBaoFooTransportService;

	private Logger logger = LoggerFactory.getLogger(UserWithdrawMockAspect.class);

	@Pointcut("execution(public * com.pinting.business.accounting.finance.service.impl.UserBalanceWithdrawServiceImpl.preWithdraw(..))")
	public void userWithdrawApply(){}

	@Pointcut("execution(public * com.pinting.business.accounting.finance.service.impl.UserBalanceWithdrawServiceImpl.pass(..))")
	public void userWithdrawPass(){}


	@Before(value = "userWithdrawApply()")
	public void mockBefore(JoinPoint joinPoint) {
		try {
			ReqMsg_UserBalance_Withdraw req = (ReqMsg_UserBalance_Withdraw)joinPoint.getArgs()[0];
			logger.info("userWithdrawApply用户提现入参：{}", JSONObject.fromObject(req));
		} catch (Exception e) {
			e.printStackTrace();
		}
		BaoFooTransportService mockBaoFooTransportService = Mockito.mock(BaoFooTransportService.class);
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(userBalanceWithdrawService), "baoFooTransportService", mockBaoFooTransportService);
		B2GResMsg_BaoFooQuickPay_Pay4Trans res = new B2GResMsg_BaoFooQuickPay_Pay4Trans();
		res.setResCode(ConstantUtil.BSRESCODE_SUCCESS);
//		res.setResCode(Constants.GATEWAY_PAYING_RES_CODE);
//		res.setResMsg("余额提现-测试mock处理中");
//		res.setResCode(Constants.GATEWAY_PAYING_RES_CODE);
//		res.setResCode(ConstantUtil.BSRESCODE_FAIL);
//		res.setResMsg("测试宝付提现失败");
		Mockito.doReturn(res).when(mockBaoFooTransportService).pay4Trans(Mockito.any(B2GReqMsg_BaoFooQuickPay_Pay4Trans.class));
	}
	
	@After(value = "userWithdrawApply()")
	public void mockAfter() {
//		ReflectionTestUtils.setField(AopTargetUtils.getTarget(userBalanceWithdrawService), "baoFooTransportService", springBaoFooTransportService);
	}

	@Before(value = "userWithdrawPass()")
	public void mockPassBefore(JoinPoint joinPoint) {
		try {
			ReqMsg_UserBalance_WithdrawPass req = (ReqMsg_UserBalance_WithdrawPass)joinPoint.getArgs()[0];
			logger.info("userWithdrawPass用户提现入参：{}", JSONObject.fromObject(req));
		} catch (Exception e) {
			e.printStackTrace();
		}
		BaoFooTransportService mockBaoFooTransportService = Mockito.mock(BaoFooTransportService.class);
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(userBalanceWithdrawService), "baoFooTransportService", mockBaoFooTransportService);
		B2GResMsg_BaoFooQuickPay_Pay4Trans res = new B2GResMsg_BaoFooQuickPay_Pay4Trans();
		res.setResCode(ConstantUtil.BSRESCODE_SUCCESS);
//		res.setResCode(Constants.GATEWAY_PAYING_RES_CODE);
//		res.setResMsg("余额提现-测试mock处理中");
		Mockito.doReturn(res).when(mockBaoFooTransportService).pay4Trans(Mockito.any(B2GReqMsg_BaoFooQuickPay_Pay4Trans.class));
	}

	@After(value = "userWithdrawPass()")
	public void mockPassAfter() {
//		ReflectionTestUtils.setField(AopTargetUtils.getTarget(userBalanceWithdrawService), "baoFooTransportService", springBaoFooTransportService);
	}
	
}
