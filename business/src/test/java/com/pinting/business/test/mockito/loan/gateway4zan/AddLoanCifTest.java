package com.pinting.business.test.mockito.loan.gateway4zan;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

import com.pinting.business.dao.LnAccountMapper;
import com.pinting.business.dao.LnUserMapper;
import com.pinting.business.model.LnAccount;
import com.pinting.business.model.LnUser;
import com.pinting.business.model.LnUserExample;
import com.pinting.business.service.loan.LoanUserService;
import com.pinting.business.test.TestBase;
import com.pinting.business.test.mockito.AopTargetUtils;
import com.pinting.gateway.hessian.message.loan.G2BReqMsg_LoanCif_AddLoaner;
import com.pinting.gateway.hessian.message.loan.G2BResMsg_LoanCif_AddLoaner;
/**
 * 借款用户信息登记
 * @author bianyatian
 * @2016-9-13 下午2:49:39
 */
public class AddLoanCifTest extends TestBase {
	
	@Autowired
	private LoanUserService loanUserService;
	
	@Mock
	private LnUserMapper lnUserMapper;
	@Mock
	private LnAccountMapper lnAccountMapper;
	
	@Before
	public void mockBefore() {
		MockitoAnnotations.initMocks(this);
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(loanUserService), "lnUserMapper", lnUserMapper);
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(loanUserService), "lnAccountMapper", lnAccountMapper);
	}
	
	@Test
	public void selectTest() {
		G2BReqMsg_LoanCif_AddLoaner req = new G2BReqMsg_LoanCif_AddLoaner();
		req.setName("123");
		req.setRegMobile("111222333");
		req.setIdCard("3301111111");
		req.setChannel("ZAN");
		req.setUserId("1234");
		
		//对方法设定预期返回值
		Mockito.doReturn(111).when(lnUserMapper).insertSelective(Mockito.any(LnUser.class));
		Mockito.doReturn(1).when(lnAccountMapper).insertSelective(Mockito.any(LnAccount.class));
		List<LnUser> list = new ArrayList<LnUser>();
		Mockito.doReturn(list).when(lnUserMapper).selectByExample(Mockito.any(LnUserExample.class));
		
		//执行方法
		G2BResMsg_LoanCif_AddLoaner res = new G2BResMsg_LoanCif_AddLoaner();
		loanUserService.addLoanCif(req,res);
		//验证mock方法是否被调用
		Mockito.verify(lnUserMapper).insertSelective(Mockito.any(LnUser.class));
		Mockito.verify(lnAccountMapper).insertSelective(Mockito.any(LnAccount.class));
		Mockito.verify(lnUserMapper).selectByExample(Mockito.any(LnUserExample.class));
	}
}
