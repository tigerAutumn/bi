package com.pinting.business.mockito;

import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.util.ReflectionTestUtils;

import com.pinting.business.accounting.finance.service.UserTopUpService;
import com.pinting.business.service.site.UserService;
import com.pinting.core.util.ConstantUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.hessian.message.baofoo.B2GReqMsg_BaoFooQuickPay_QuickPayConfirm;
import com.pinting.gateway.hessian.message.baofoo.B2GResMsg_BaoFooQuickPay_QuickPayConfirm;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_Customer_QueryUserByPhone;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_Customer_QueryUserByPhone;
import com.pinting.gateway.out.service.BaoFooTransportService;
import com.pinting.gateway.out.service.DafyTransportService;

/**
 * 客户经理登录切面
 * 支持角色，财务，部门主管，普通客户经理
 * @author Dragon & cat
 * @date 2016-11-18
 */
@Aspect
@Component
public class CustomerManagerLoginMockAspect {
	
	@Autowired
	private UserService  userService;
	@Autowired
	private DafyTransportService springDafyService;

	
	@Pointcut("execution(public * com.pinting.business.service.site.impl.UserServiceImpl.checkClientManagerLogin(..))")
	public void customerManagerLogin(){}

	@Before(value = "customerManagerLogin()")
	public void mockBefore() {
		DafyTransportService mockDafyTransportService = Mockito.mock(DafyTransportService.class);
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(userService), "dafyService", mockDafyTransportService);
		
		B2GResMsg_Customer_QueryUserByPhone res = new B2GResMsg_Customer_QueryUserByPhone();
		res.setResCode(ConstantUtil.BSRESCODE_SUCCESS);
		res.setResMsg("OK");
		res.setCode(0);
		Map<String, Object> map = new HashMap<>();
		
		//权限：达飞财务(17744547913)
		map.put("nWorkState", 1);
		map.put("result", true);
		map.put("lUserId", "1171759");
		map.put("strName", "王红永");
		map.put("strDeptCode", "990400000");
		map.put("strDeptName", "财务部");
		map.put("lDeptId", "19");
		map.put("nManager", 0);
		map.put("strRoles", "24");
		//权限：归属维护（18301425635）
/*		map.put("nWorkState", 1);
		map.put("result", true);
		map.put("lUserId", "1528469");
		map.put("strName", "郝亚静");
		map.put("strDeptCode", "990000000");
		map.put("strDeptName", "集团营业部（职能端）");
		map.put("lDeptId", "15");
		map.put("nManager", 0);
		map.put("strRoles", "25");*/
		res.setStrSalesman(map);
 		Mockito.doReturn(res).when(mockDafyTransportService).queryUserByPhone(Mockito.any(B2GReqMsg_Customer_QueryUserByPhone.class));
	}
	
	@After(value = "customerManagerLogin()")
	public void mockAfter() {
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(userService), "dafyService", springDafyService);
	}


}
