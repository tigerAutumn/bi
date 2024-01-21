package com.pinting.business.test.mockito.common;

import java.util.ArrayList;
import java.util.List;

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
import com.pinting.business.dao.LnRepayScheduleMapper;
import com.pinting.business.dao.LnSubjectMapper;
import com.pinting.business.model.LnRepaySchedule;
import com.pinting.business.model.LnRepayScheduleExample;
import com.pinting.business.model.vo.LnSubjectVO;
import com.pinting.business.service.loan.LoanUserService;
import com.pinting.business.test.TestBase;
import com.pinting.business.test.mockito.AopTargetUtils;
import com.pinting.core.util.DateUtil;
/**
 * 算某笔借款某期应提滞纳金
 * @author bianyatian
 * @2016-9-19 下午3:17:15
 */
public class LoanUserCalLateFeeTest extends TestBase {

	@Autowired
	private LoanUserService loanUserService ;
	
	@Mock
	private LnSubjectMapper lnSubjectMapper;
	
	@Mock
	private LnRepayScheduleMapper lnRepayScheduleMapper;

	@Before
	public void mockBefore() {
		MockitoAnnotations.initMocks(this);
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(loanUserService), "lnSubjectMapper", lnSubjectMapper);
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(loanUserService), "lnRepayScheduleMapper", lnRepayScheduleMapper);
	}
	
	//违约金 = （月应还本金+月应还利息–实际该期已还款金额）× 违约金系数 × 逾期天数
	@Test
	public void selectTest() {
		//对方法设定预期返回值
		//-----------------------本金-------------------start
		//对方法设定预期返回值
		LnSubjectVO subjectVo1 = new LnSubjectVO();
		subjectVo1.setSubjectCode(LoanSubjects.SUBJECT_CODE_PRINCIPAL.getCode());
		subjectVo1.setApproveAmount(20000d);
		subjectVo1.setTerm(3);
		//先按照费率进行计算具体金额然后再进行分期
		subjectVo1.setCalRule(LoanSubjectRulesEnum.CalRuleEnum.AFTER.getCode());
		///余数规则-本金分期后多余的金额加到最后一期
		subjectVo1.setLeftRule(LeftRuleEnum.LAST.getCode());
		//计数保留规则-四舍五入
		subjectVo1.setReserveRule(LoanSubjectRulesEnum.ReserveRuleEnum.ROUND.getCode());
		subjectVo1.setRatetype(LoanSubjectRulesEnum.RateTypeEnum.RATE.getCode());
		subjectVo1.setNumValue(10000d);
		Mockito.doReturn(subjectVo1).when(lnSubjectMapper).selectByLoanId(1, PartnerEnum.ZAN.getCode(), 
				LoanSubjects.SUBJECT_CODE_PRINCIPAL.getCode());
		
		//执行方法
		Double amount = loanUserService.calPrincipal(1, 1);
		Double amount1 = loanUserService.calPrincipal(1, 2);
		Double amount2 = loanUserService.calPrincipal(1, 3);
		System.out.println("本金:"+amount+";"+amount1+";"+amount2);
		
		//验证mock方法是否被调用
		Mockito.verify(lnSubjectMapper, Mockito.atLeast(1)).selectByLoanId(1, PartnerEnum.ZAN.getCode(), 
				LoanSubjects.SUBJECT_CODE_PRINCIPAL.getCode());
		
		//-----------------------本金-------------------end
		//-----------------------利息-------------------start
		//对方法设定预期返回值
		LnSubjectVO subjectVo2 = new LnSubjectVO();
		subjectVo2.setSubjectCode(LoanSubjects.SUBJECT_CODE_INTEREST.getCode());
		subjectVo2.setApproveAmount(20000d);
		subjectVo2.setTerm(3);
		//先按照费率进行计算具体金额然后再进行分期
		subjectVo2.setCalRule(LoanSubjectRulesEnum.CalRuleEnum.AFTER.getCode());
		///余数规则-本金分期后多余的金额加到最后一期
		subjectVo2.setLeftRule(LeftRuleEnum.LAST.getCode());
		//计数保留规则-四舍五入
		subjectVo2.setReserveRule(LoanSubjectRulesEnum.ReserveRuleEnum.ROUND.getCode());
		subjectVo2.setRatetype(LoanSubjectRulesEnum.RateTypeEnum.RATE.getCode());
		subjectVo2.setNumValue(144d);
		Mockito.doReturn(subjectVo2).when(lnSubjectMapper).selectByLoanId(1, PartnerEnum.ZAN.getCode(), 
				LoanSubjects.SUBJECT_CODE_INTEREST.getCode());
		
		//执行方法
		Double amountIn = loanUserService.calInterest(1, 1);
		Double amountIn1 = loanUserService.calInterest(1, 2);
		Double amountIn2 = loanUserService.calInterest(1, 3);
		System.out.println("利息:"+amountIn+";"+amountIn1+";"+amountIn2);
		
		//验证mock方法是否被调用
		Mockito.verify(lnSubjectMapper, Mockito.atLeast(1)).selectByLoanId(1, PartnerEnum.ZAN.getCode(), 
				LoanSubjects.SUBJECT_CODE_INTEREST.getCode());
		
		//-----------------------利息-------------------end
				
		
		//违约金科目
		LnSubjectVO subjectVo = new LnSubjectVO();
		subjectVo.setApproveAmount(20000d);
		subjectVo.setTerm(3);
		subjectVo.setNumValue(20d);
		subjectVo.setLeftRule(LeftRuleEnum.FIRST.getCode());
		//计数保留规则-小数点后直接舍弃
		subjectVo.setReserveRule(LoanSubjectRulesEnum.ReserveRuleEnum.FLOOR.getCode());
		subjectVo.setLoanTime(DateUtil.parseDateTime("2016-9-16 10:10:10"));
		
		Mockito.doReturn(subjectVo).when(lnSubjectMapper).selectByLoanId(1, PartnerEnum.ZAN.getCode(), 
				LoanSubjects.SUBJECT_CODE_LATE_FEE.getCode());
		
		
		List<LnRepaySchedule> repayScheduleList = new ArrayList<>();
		Mockito.doReturn(repayScheduleList).when(lnRepayScheduleMapper).selectByExample(Mockito.any(LnRepayScheduleExample.class));
		
		//执行方法
		Double amount11 = loanUserService.calLateFee(1, 1);
		Double amount12 = loanUserService.calLateFee(1, 2);
		Double amount13 = loanUserService.calLateFee(1, 3);
		System.out.println(amount11+";"+amount12+";"+amount13);
					
		//验证mock方法是否被调用
		Mockito.verify(lnSubjectMapper, Mockito.atLeast(1)).selectByLoanId(1, PartnerEnum.ZAN.getCode(), 
				LoanSubjects.SUBJECT_CODE_LATE_FEE.getCode());
		Mockito.verify(lnRepayScheduleMapper, Mockito.atLeast(1)).selectByExample(Mockito.any(LnRepayScheduleExample.class));
	
	}
}
