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
 * 计算某笔借款某期应还本金，应还币港湾利息，应还监管费，应还信息服务费，应还账户管理费，运用的算法相同
 * 
 * 该测试类测试了某笔借款某期应还本金，应还币港湾利息，应还监管费，应还信息服务费，应还账户管理费，应提滞纳金，应还保费，应还总费用
 * @author bianyatian
 * @2016-9-18 下午5:42:40
 */
public class LoanUserCalSuperviseFeeTest extends TestBase {
	
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
	
	@Test
	public void selectTest() {
		
		Double principalAmount = 5000d;
		Integer term =12;
		
		//-----------------------本金-------------------start
		//对方法设定预期返回值
		LnSubjectVO subjectVo1 = new LnSubjectVO();
		subjectVo1.setSubjectCode(LoanSubjects.SUBJECT_CODE_PRINCIPAL.getCode());
		subjectVo1.setApproveAmount(principalAmount);
		subjectVo1.setTerm(term);
		//先按照费率进行计算具体金额然后再进行分期
		subjectVo1.setCalRule(LoanSubjectRulesEnum.CalRuleEnum.AFTER.getCode());
		///余数规则-本金分期后多余的金额加到最后一期
		subjectVo1.setLeftRule(LeftRuleEnum.LAST.getCode());
		//计数保留规则-四舍五入
		subjectVo1.setReserveRule(LoanSubjectRulesEnum.ReserveRuleEnum.FLOOR.getCode());
		subjectVo1.setRatetype(LoanSubjectRulesEnum.RateTypeEnum.RATE.getCode());
		subjectVo1.setNumValue(10000d);
		Mockito.doReturn(subjectVo1).when(lnSubjectMapper).selectByLoanId(1, PartnerEnum.ZAN.getCode(), 
				LoanSubjects.SUBJECT_CODE_PRINCIPAL.getCode());
		
		//执行方法
		Double amount1 = loanUserService.calPrincipal(1, 1);
		Double amount2 = loanUserService.calPrincipal(1, 2);
		Double amount3 = loanUserService.calPrincipal(1, 3);
		
		Double amount4 = loanUserService.calPrincipal(1, 6);
		Double amount5 = loanUserService.calPrincipal(1, 9);
		Double amount6 = loanUserService.calPrincipal(1, 12);
		
		System.out.println("本金:1、"+amount1+"；2、"+amount2+"；3、"+amount3+"；6、"+amount4+"；9、"+amount5+"；12、"+amount6);
		
		//验证mock方法是否被调用
		Mockito.verify(lnSubjectMapper, Mockito.atLeast(1)).selectByLoanId(1, PartnerEnum.ZAN.getCode(), 
				LoanSubjects.SUBJECT_CODE_PRINCIPAL.getCode());
		
		//-----------------------本金-------------------end
		
		
		//-----------------------利息-------------------start
		//对方法设定预期返回值
		LnSubjectVO subjectVo2 = new LnSubjectVO();
		subjectVo2.setSubjectCode(LoanSubjects.SUBJECT_CODE_INTEREST.getCode());
		subjectVo2.setApproveAmount(principalAmount);
		subjectVo2.setTerm(term);
		//先按照费率进行计算具体金额然后再进行分期
		//subjectVo2.setCalRule(LoanSubjectRulesEnum.CalRuleEnum.AFTER.getCode());
		subjectVo2.setCalRule(null);
		///余数规则-本金分期后多余的金额加到最后一期
		subjectVo2.setLeftRule(LeftRuleEnum.LAST.getCode());
		//计数保留规则-四舍五入
		subjectVo2.setReserveRule(LoanSubjectRulesEnum.ReserveRuleEnum.ROUND.getCode());
		subjectVo2.setRatetype(LoanSubjectRulesEnum.RateTypeEnum.RATE.getCode());
		subjectVo2.setNumValue(139d);
		Mockito.doReturn(subjectVo2).when(lnSubjectMapper).selectByLoanId(1, PartnerEnum.ZAN.getCode(), 
				LoanSubjects.SUBJECT_CODE_INTEREST.getCode());
		
		//执行方法
		Double amountIn1 = loanUserService.calInterest(1, 1);
		Double amountIn2 = loanUserService.calInterest(1, 2);
		Double amountIn3 = loanUserService.calInterest(1, 3);
		Double amountIn4 = loanUserService.calInterest(1, 6);
		Double amountIn5 = loanUserService.calInterest(1, 9);
		Double amountIn6 = loanUserService.calInterest(1, 12);
		
		System.out.println("利息金:1、"+amountIn1+"；2、"+amountIn2+"；3、"+amountIn3+"；6、"+amountIn4+"；9、"+amountIn5+"；12、"+amountIn6);
		
		//验证mock方法是否被调用
		Mockito.verify(lnSubjectMapper, Mockito.atLeast(1)).selectByLoanId(1, PartnerEnum.ZAN.getCode(), 
				LoanSubjects.SUBJECT_CODE_INTEREST.getCode());
		
		//-----------------------利息-------------------end
		
		
		//-----------------------监管费-------------------start
		//对方法设定预期返回值
		LnSubjectVO subjectVo3 = new LnSubjectVO();
		subjectVo3.setSubjectCode(LoanSubjects.SUBJECT_CODE_SUPERVISE_FEE.getCode());
		subjectVo3.setApproveAmount(principalAmount);
		subjectVo3.setTerm(term);
		//先按照费率进行计算具体金额然后再进行分期
		//subjectVo3.setCalRule(LoanSubjectRulesEnum.CalRuleEnum.AFTER.getCode());
		subjectVo3.setCalRule(null);
		///余数规则-本金分期后多余的金额加到最后一期
		subjectVo3.setLeftRule(LeftRuleEnum.LAST.getCode());
		//计数保留规则-四舍五入
		subjectVo3.setReserveRule(LoanSubjectRulesEnum.ReserveRuleEnum.ROUND.getCode());
		subjectVo3.setRatetype(LoanSubjectRulesEnum.RateTypeEnum.RATE.getCode());
		subjectVo3.setNumValue(25d);
		Mockito.doReturn(subjectVo3).when(lnSubjectMapper).selectByLoanId(1, PartnerEnum.ZAN.getCode(), 
				LoanSubjects.SUBJECT_CODE_SUPERVISE_FEE.getCode());
		
		//执行方法
		Double amountS1 = loanUserService.calSuperviseFee(1, 1);
		Double amountS2 = loanUserService.calSuperviseFee(1, 2);
		Double amountS3= loanUserService.calSuperviseFee(1, 3);
		Double amountS4 = loanUserService.calSuperviseFee(1, 6);
		Double amountS5 = loanUserService.calSuperviseFee(1, 9);
		Double amountS6 = loanUserService.calSuperviseFee(1, 12);
		
		System.out.println("监管费:1、"+amountS1+"；2、"+amountS2+"；3、"+amountS3+"；6、"+amountS4+"；9、"+amountS5+"；12、"+amountS6);
		
		
		//验证mock方法是否被调用
		Mockito.verify(lnSubjectMapper, Mockito.atLeast(1)).selectByLoanId(1, PartnerEnum.ZAN.getCode(), 
				LoanSubjects.SUBJECT_CODE_SUPERVISE_FEE.getCode());
		
		//-----------------------监管费-------------------end
		
		
		//-----------------------信息服务费-------------------start
		//对方法设定预期返回值
		LnSubjectVO subjectVo4 = new LnSubjectVO();
		subjectVo4.setSubjectCode(LoanSubjects.SUBJECT_CODE_INFO_SERVICE_FEE.getCode());
		subjectVo4.setApproveAmount(principalAmount);
		subjectVo4.setTerm(term);
		//先按照费率进行计算具体金额然后再进行分期
		//subjectVo4.setCalRule(LoanSubjectRulesEnum.CalRuleEnum.AFTER.getCode());
		subjectVo4.setCalRule(null);
		///余数规则-本金分期后多余的金额加到最后一期
		subjectVo4.setLeftRule(LeftRuleEnum.LAST.getCode());
		//计数保留规则-四舍五入
		subjectVo4.setReserveRule(LoanSubjectRulesEnum.ReserveRuleEnum.ROUND.getCode());
		subjectVo4.setRatetype(LoanSubjectRulesEnum.RateTypeEnum.RATE.getCode());
		subjectVo4.setNumValue(35d);
		Mockito.doReturn(subjectVo4).when(lnSubjectMapper).selectByLoanId(1, PartnerEnum.ZAN.getCode(), 
				LoanSubjects.SUBJECT_CODE_INFO_SERVICE_FEE.getCode());
		
		//执行方法
		Double amountf1 = loanUserService.calInfoServiceFee(1, 1);
		Double amountf2 = loanUserService.calInfoServiceFee(1, 2);
		Double amountf3 = loanUserService.calInfoServiceFee(1, 3);
		Double amountf4 = loanUserService.calInfoServiceFee(1, 6);
		Double amountf5 = loanUserService.calInfoServiceFee(1, 9);
		Double amountf6 = loanUserService.calInfoServiceFee(1, 12);
		
		System.out.println("信息服务费:1、"+amountf1+"；2、"+amountf2+"；3、"+amountf3+"；6、"+amountf4+"；9、"+amountf5+"；12、"+amountf6);
		
		
		//验证mock方法是否被调用
		Mockito.verify(lnSubjectMapper, Mockito.atLeast(1)).selectByLoanId(1, PartnerEnum.ZAN.getCode(), 
				LoanSubjects.SUBJECT_CODE_INFO_SERVICE_FEE.getCode());
		
		//-----------------------信息服务费-------------------end

		
		//-----------------------账户管理费-------------------start
		//对方法设定预期返回值
		LnSubjectVO subjectVo5 = new LnSubjectVO();
		subjectVo5.setSubjectCode(LoanSubjects.SUBJECT_CODE_ACCOUNT_MANAGE_FEE.getCode());
		subjectVo5.setApproveAmount(principalAmount);
		subjectVo5.setTerm(term);
		//先按照费率进行计算具体金额然后再进行分期
		//subjectVo5.setCalRule(LoanSubjectRulesEnum.CalRuleEnum.AFTER.getCode());
		
		subjectVo5.setCalRule(null);
		///余数规则-本金分期后多余的金额加到最后一期
		subjectVo5.setLeftRule(LeftRuleEnum.LAST.getCode());
		//计数保留规则-四舍五入
		subjectVo5.setReserveRule(LoanSubjectRulesEnum.ReserveRuleEnum.ROUND.getCode());
		subjectVo5.setRatetype(LoanSubjectRulesEnum.RateTypeEnum.RATE.getCode());
		subjectVo5.setNumValue(0d);
		Mockito.doReturn(subjectVo5).when(lnSubjectMapper).selectByLoanId(1, PartnerEnum.ZAN.getCode(), 
				LoanSubjects.SUBJECT_CODE_ACCOUNT_MANAGE_FEE.getCode());
		
		//执行方法
		Double amountc1 = loanUserService.calAccountManageFee(1, 1);
		Double amountc2 = loanUserService.calAccountManageFee(1, 2);
		Double amountc3= loanUserService.calAccountManageFee(1, 3);
		Double amountc4 = loanUserService.calInfoServiceFee(1, 6);
		Double amountc5 = loanUserService.calInfoServiceFee(1, 9);
		Double amountc6 = loanUserService.calInfoServiceFee(1, 12);
		
		System.out.println("账户管理费:1、"+amountc1+"；2、"+amountc2+"；3、"+amountc3+"；6、"+amountc4+"；9、"+amountc5+"；12、"+amountc6);
		
		
		//验证mock方法是否被调用
		Mockito.verify(lnSubjectMapper, Mockito.atLeast(1)).selectByLoanId(1, PartnerEnum.ZAN.getCode(), 
				LoanSubjects.SUBJECT_CODE_ACCOUNT_MANAGE_FEE.getCode());
		
		//-----------------------账户管理费-------------------end	
		
		
		
		
		//-----------------------违约金科目-------------------start	
		LnSubjectVO subjectVo6 = new LnSubjectVO();
		subjectVo6.setApproveAmount(principalAmount);
		subjectVo6.setTerm(term);
		subjectVo6.setNumValue(20d);
		subjectVo6.setLeftRule(LeftRuleEnum.FIRST.getCode());
		//计数保留规则-小数点后直接舍弃
		subjectVo6.setReserveRule(LoanSubjectRulesEnum.ReserveRuleEnum.FLOOR.getCode());
		subjectVo6.setLoanTime(DateUtil.parseDateTime("2016-9-16 10:10:10"));
		
		Mockito.doReturn(subjectVo6).when(lnSubjectMapper).selectByLoanId(1, PartnerEnum.ZAN.getCode(), 
				LoanSubjects.SUBJECT_CODE_LATE_FEE.getCode());
		
		
		List<LnRepaySchedule> repayScheduleList = new ArrayList<>();
		Mockito.doReturn(repayScheduleList).when(lnRepayScheduleMapper).selectByExample(Mockito.any(LnRepayScheduleExample.class));
		
		//执行方法
		Double amountla1 = loanUserService.calLateFee(1, 1);
		Double amountla2 = loanUserService.calLateFee(1, 2);
		Double amountla3 = loanUserService.calLateFee(1, 3);
		Double amountla4 = loanUserService.calLateFee(1, 6);
		Double amountla5 = loanUserService.calLateFee(1, 9);
		Double amountla6 = loanUserService.calLateFee(1, 12);
		
		System.out.println("违约金:1、"+amountla1+"；2、"+amountla2+"；3、"+amountla3+"；6、"+amountla4+"；9、"+amountla5+"；12、"+amountla6);
					
		//验证mock方法是否被调用
		Mockito.verify(lnSubjectMapper, Mockito.atLeast(1)).selectByLoanId(1, PartnerEnum.ZAN.getCode(), 
				LoanSubjects.SUBJECT_CODE_LATE_FEE.getCode());
		Mockito.verify(lnRepayScheduleMapper, Mockito.atLeast(1)).selectByExample(Mockito.any(LnRepayScheduleExample.class));
		
		//-----------------------违约金科目-------------------end	
		
		//-----------------------保费-------------------start
		//对方法设定预期返回值
		LnSubjectVO subjectVo7 = new LnSubjectVO();
		subjectVo7.setApproveAmount(principalAmount);
		subjectVo7.setTerm(term);
		subjectVo7.setLeftRule(LeftRuleEnum.LAST.getCode());
		subjectVo7.setReserveRule(LoanSubjectRulesEnum.ReserveRuleEnum.FLOOR.getCode());
		Mockito.doReturn(subjectVo7).when(lnSubjectMapper).selectByLoanId(1, PartnerEnum.ZAN.getCode(), 
				LoanSubjects.SUBJECT_CODE_PREMIUM.getCode());
		
		//执行方法
		Double amountP1 = loanUserService.calPremium(1, 1);
		Double amountP2 = loanUserService.calPremium(1, 2);
		Double amountP3 = loanUserService.calPremium(1, 3);
		Double amountP4 = loanUserService.calPremium(1, 6);
		Double amountP5 = loanUserService.calPremium(1, 9);
		Double amountP6 = loanUserService.calPremium(1, 12);
		System.out.println("保费:1、"+amountP1+"；2、"+amountP2+"；3、"+amountP3+"；6、"+amountP4+"；9、"+amountP5+"；12、"+amountP6);
		
		//验证mock方法是否被调用
		Mockito.verify(lnSubjectMapper, Mockito.atLeast(1)).selectByLoanId(1, PartnerEnum.ZAN.getCode(), 
				LoanSubjects.SUBJECT_CODE_PREMIUM.getCode());
		
		//-----------------------保费-------------------end	
		
		
		//-----------------------应还总费用-------------------start
		Double sumAmount1 = loanUserService.calTotalRepay(1, 1);
		Double sumAmount2 = loanUserService.calTotalRepay(1, 2);
		Double sumAmount3 = loanUserService.calTotalRepay(1, 3);
		Double sumAmount4 = loanUserService.calTotalRepay(1, 6);
		Double sumAmount5 = loanUserService.calTotalRepay(1, 9);
		Double sumAmount6 = loanUserService.calTotalRepay(1, 12);
		System.out.println("应还总费用:1、"+sumAmount1+"；2、"+sumAmount2+"；3、"+sumAmount3+"；6、"+sumAmount4+"；9、"+sumAmount5+"；12、"+sumAmount6);
		//-----------------------应还总费用-------------------end
	}
}
