package com.pinting.business.test.mockito.common;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

import com.pinting.business.dao.LnFinanceRepayScheduleMapper;
import com.pinting.business.dao.LnLoanMapper;
import com.pinting.business.enums.TransTypeEnum;
import com.pinting.business.model.LnLoan;
import com.pinting.business.model.vo.CommissionVO;
import com.pinting.business.model.vo.ZANRevenueModelVO;
import com.pinting.business.service.common.AlgorithmService;
import com.pinting.business.service.common.CommissionService;
import com.pinting.business.service.loan.LoanUserService;
import com.pinting.business.test.TestBase;
import com.pinting.business.test.mockito.AopTargetUtils;
/**
 * 计算某笔借款某期赞分期营收
 * @author bianyatian
 * @2016-9-18 上午9:48:43
 */
public class AlgorithmCalZANRevenueTest extends TestBase {
	@Autowired
	private AlgorithmService algorithmService;
	@Mock
	private LoanUserService loanUserService;
	@Mock
	private CommissionService commissionService;
	@Mock
	private LnFinanceRepayScheduleMapper lnFinanceRepayScheduleMapper;
	@Mock
	private LnLoanMapper lnLoanMapper;
	
	
	@Before
    public void mockBefore() {
		MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(AopTargetUtils.getTarget(algorithmService), "loanUserService", loanUserService);
        ReflectionTestUtils.setField(AopTargetUtils.getTarget(algorithmService), "commissionService", commissionService);
        ReflectionTestUtils.setField(AopTargetUtils.getTarget(algorithmService), "lnFinanceRepayScheduleMapper", lnFinanceRepayScheduleMapper);
        ReflectionTestUtils.setField(AopTargetUtils.getTarget(algorithmService), "lnLoanMapper", lnLoanMapper);
	}
	
	@Test
	public void selectTest() {
		LnLoan loan = new LnLoan();
		loan.setApproveAmount(30000d);
		loan.setPeriod(3);
		//应还总费用(还款金额)
		Mockito.doReturn(10400d).when(loanUserService).calTotalRepay(1, 1);
		//计算某笔借款某期应还理财人本息
		Mockito.doReturn(10030d).when(lnFinanceRepayScheduleMapper).sumAmountByLoanIdSerialId(1, 1);
		//保证金-借款总额
		Mockito.doReturn(loan).when(lnLoanMapper).selectByPrimaryKey(1);
		//币港湾服务费
		
		//手续费
		CommissionVO commissionVO  = new CommissionVO();
		commissionVO.setActPayAmount(7d);
		Mockito.doReturn(commissionVO).when(commissionService).calServiceFee(10400d,  TransTypeEnum.LOAN_USER_REPAY_QUICK_PAY, null);
	
		ZANRevenueModelVO zANRevenueModelVO  = algorithmService.calZANRevenue(1, 1);
		System.out.println(zANRevenueModelVO);
		
		Mockito.verify(loanUserService, Mockito.atLeast(1)).calTotalRepay(1, 1);
		Mockito.verify(lnFinanceRepayScheduleMapper, Mockito.atLeast(1)).sumAmountByLoanIdSerialId(1, 1);
		Mockito.verify(lnLoanMapper, Mockito.atLeast(1)).selectByPrimaryKey(1);
		Mockito.verify(commissionService, Mockito.atLeast(1)).calServiceFee(10400d,  TransTypeEnum.LOAN_USER_REPAY_QUICK_PAY, null);
	}
}
