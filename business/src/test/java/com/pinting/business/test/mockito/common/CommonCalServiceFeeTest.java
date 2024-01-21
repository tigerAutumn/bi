package com.pinting.business.test.mockito.common;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

import com.pinting.business.dao.BsSysConfigMapper;
import com.pinting.business.enums.TransTypeEnum;
import com.pinting.business.model.BsSysConfig;
import com.pinting.business.model.vo.CommissionVO;
import com.pinting.business.service.common.CommissionService;
import com.pinting.business.test.TestBase;
import com.pinting.business.test.mockito.AopTargetUtils;
import com.pinting.business.util.Constants;
/**
 * 公共服务-手续费
 * @author bianyatian
 * @2016-9-14 上午10:29:09
 */
public class CommonCalServiceFeeTest extends TestBase{
	
	@Autowired
	private CommissionService commissionService;
	
	@Mock
	private BsSysConfigMapper bsSysConfigMapper;
	
	@Before
	public void mockBefore() {
		 MockitoAnnotations.initMocks(this);
		 ReflectionTestUtils.setField(AopTargetUtils.getTarget(commissionService),"bsSysConfigMapper",bsSysConfigMapper);
	}
	
	@Test
	public void selectTest() {
		//提现
		BsSysConfig withdraw  = new BsSysConfig();
		withdraw.setConfKey(Constants.FINANCE_WITHDRAW_COUNTER_FEE);
		withdraw.setConfValue("2");
		Mockito.doReturn(withdraw).when(bsSysConfigMapper).selectByPrimaryKey(Constants.FINANCE_WITHDRAW_COUNTER_FEE);
		//借款
		BsSysConfig userLoan  = new BsSysConfig();
		userLoan.setConfKey(Constants.LOAN_USER_LOAN_FEE);
		userLoan.setConfValue("3");
		Mockito.doReturn(userLoan).when(bsSysConfigMapper).selectByPrimaryKey(Constants.LOAN_USER_LOAN_FEE);
		//还款
		BsSysConfig userRepay  = new BsSysConfig();
		userRepay.setConfKey(Constants.LOAN_USER_REPAY_FEE_RATE);
		userRepay.setConfValue("5");
		Mockito.doReturn(userRepay).when(bsSysConfigMapper).selectByPrimaryKey(Constants.LOAN_USER_REPAY_FEE_RATE);
		
		
		//执行
		CommissionVO commonTopUp = commissionService.calServiceFee(null, TransTypeEnum.USER_TOP_UP_QUICK_PAY, null);
		System.out.println("充值:need"+commonTopUp.getNeedPayAmount()+",act:"+commonTopUp.getActPayAmount());
		CommissionVO commonWithdraw = commissionService.calServiceFee(null, TransTypeEnum.USER_WITHDRAW, null);
		System.out.println("提现:need"+commonWithdraw.getNeedPayAmount()+",act:"+commonWithdraw.getActPayAmount());
		CommissionVO commonLoan = commissionService.calServiceFee(null, TransTypeEnum.LOAN_USER_LOAN, null);
		System.out.println("借款:need"+commonLoan.getNeedPayAmount()+",act:"+commonLoan.getActPayAmount());
		CommissionVO commonRepay = commissionService.calServiceFee(10000d, TransTypeEnum.LOAN_USER_REPAY_QUICK_PAY, null);
		System.out.println("还款:need"+commonRepay.getNeedPayAmount()+",act:"+commonRepay.getActPayAmount());
		//验证mock方法是否被调用
		Mockito.verify(bsSysConfigMapper).selectByPrimaryKey(Constants.FINANCE_WITHDRAW_COUNTER_FEE);
		Mockito.verify(bsSysConfigMapper).selectByPrimaryKey(Constants.LOAN_USER_LOAN_FEE);
		Mockito.verify(bsSysConfigMapper).selectByPrimaryKey(Constants.LOAN_USER_REPAY_FEE_RATE);
	
	}

}
