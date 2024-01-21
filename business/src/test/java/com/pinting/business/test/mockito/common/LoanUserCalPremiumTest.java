package com.pinting.business.test.mockito.common;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

import com.pinting.business.accounting.loan.enums.LoanSubjectRulesEnum;
import com.pinting.business.accounting.loan.enums.LoanSubjects;
import com.pinting.business.accounting.loan.enums.PartnerEnum;
import com.pinting.business.accounting.loan.enums.LoanSubjectRulesEnum.LeftRuleEnum;
import com.pinting.business.dao.LnSubjectMapper;
import com.pinting.business.model.vo.LnSubjectVO;
import com.pinting.business.service.loan.LoanUserService;
import com.pinting.business.test.TestBase;
import com.pinting.business.test.mockito.AopTargetUtils;

/**
 * 计算某笔借款某期应还保费
 * @author bianyatian
 * @2016-9-18 下午3:40:44
 */
public class LoanUserCalPremiumTest extends TestBase {
	
	@Autowired
	private LoanUserService loanUserService ;
	
	@Mock
	private LnSubjectMapper lnSubjectMapper;

	@Before
	public void mockBefore() {
		MockitoAnnotations.initMocks(this);
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(loanUserService), "lnSubjectMapper", lnSubjectMapper);
	}
	
	@Test
	public void selectTest() {
		//对方法设定预期返回值
		LnSubjectVO subjectVo = new LnSubjectVO();
		subjectVo.setApproveAmount(20000d);
		subjectVo.setTerm(3);
		subjectVo.setLeftRule(LeftRuleEnum.LAST.getCode());
		subjectVo.setReserveRule(LoanSubjectRulesEnum.ReserveRuleEnum.FLOOR.getCode());
		Mockito.doReturn(subjectVo).when(lnSubjectMapper).selectByLoanId(1, PartnerEnum.ZAN.getCode(), 
				LoanSubjects.SUBJECT_CODE_PREMIUM.getCode());
		
		//执行方法
		Double amount = loanUserService.calPremium(1, 1);
		System.out.println(amount);
		Double amount2 = loanUserService.calPremium(1, 2);
		System.out.println(amount2);
		Double amount3 = loanUserService.calPremium(1, 3);
		System.out.println(amount3);
		
		//验证mock方法是否被调用
		Mockito.verify(lnSubjectMapper, Mockito.atLeast(1)).selectByLoanId(1, PartnerEnum.ZAN.getCode(), 
				LoanSubjects.SUBJECT_CODE_PREMIUM.getCode());
		
		
	}
}
