package com.pinting.business.test.mockito.common;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

import com.pinting.business.dao.LnLoanMapper;
import com.pinting.business.model.LnLoan;
import com.pinting.business.service.common.AlgorithmService;
import com.pinting.business.test.TestBase;
import com.pinting.business.test.mockito.AopTargetUtils;
/**
 * 计算某笔借款某期应提保证金
 * 借款总额*3%/12
 * @author bianyatian
 * @2016-9-14 下午4:20:17
 */
public class AlgorithmCalZANDeposit extends TestBase {

	@Autowired
	private AlgorithmService algorithmService;
	
	@Mock
	private LnLoanMapper lnLoanMapper;
	
	@Before
    public void mockBefore() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(AopTargetUtils.getTarget(algorithmService), "lnLoanMapper", lnLoanMapper);
	}
	
	
	@Test
	public void selectTest() {
		LnLoan loan = new LnLoan();
		loan.setApproveAmount(3600d);
		Mockito.doReturn(loan).when(lnLoanMapper).selectByPrimaryKey(1);
		
		Double amount = algorithmService.calZANDeposit(1, 3);
		System.out.println(amount);
		Mockito.verify(lnLoanMapper).selectByPrimaryKey(1);
	}
}
