package com.pinting.business.test.mockito.loan.loanRelation;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

import com.google.common.collect.Lists;
import com.pinting.business.accounting.finance.service.UserBonusGrantService;
import com.pinting.business.accounting.loan.model.SuperTransferInfo;
import com.pinting.business.accounting.loan.service.LoanAccountService;
import com.pinting.business.accounting.loan.service.LoanRelationshipService;
import com.pinting.business.dao.BsSubAccountMapper;
import com.pinting.business.dao.BsSubAccountPairMapper;
import com.pinting.business.dao.BsUserMapper;
import com.pinting.business.dao.LnCreditTransferMapper;
import com.pinting.business.dao.LnFinanceRepayScheduleMapper;
import com.pinting.business.dao.LnLoanRelationMapper;
import com.pinting.business.dao.LnRepayScheduleMapper;
import com.pinting.business.model.BsSubAccount;
import com.pinting.business.model.BsSubAccountPair;
import com.pinting.business.model.BsSubAccountPairExample;
import com.pinting.business.model.BsUser;
import com.pinting.business.model.LnCreditTransfer;
import com.pinting.business.model.LnFinanceRepaySchedule;
import com.pinting.business.model.LnFinanceRepayScheduleExample;
import com.pinting.business.model.LnLoanRelation;
import com.pinting.business.model.vo.BsCanMatch4ZanSubAccountVO;
import com.pinting.business.model.vo.LoanRelationVO;
import com.pinting.business.service.loan.AuthBalanaceQueryService;
import com.pinting.business.service.site.SubAccountService;
import com.pinting.business.test.TestBase;
import com.pinting.business.test.mockito.AopTargetUtils;
import com.pinting.business.util.Constants;
import com.pinting.core.util.DateUtil;
/**
 * 债权转让单元测试
 * @author bianyatian
 * @2016-9-11 上午11:10:04
 */
public class SuperTransferNormalTest extends TestBase {
	
	@Autowired
	private LoanRelationshipService loanRelationshipService;
	@Mock
	private LnLoanRelationMapper lnLoanRelationMapper;
	@Mock
	private LnRepayScheduleMapper lnRepayScheduleMapper;
	@Mock 
	private AuthBalanaceQueryService authBalanaceQueryService;
	@Mock
	private BsSubAccountMapper bsSubAccountMapper;
	@Mock
	private LnCreditTransferMapper lnCreditTransferMapper;
	@Mock
	private LnFinanceRepayScheduleMapper lnFinanceRepayScheduleMapper;
	@Mock 
	private LoanAccountService loanAccountService;
	@Mock 
	private BsSubAccountPairMapper bsSubAccountPairMapper;
	@Mock 
	private BsUserMapper userMapper;
	@Mock
	private UserBonusGrantService userBonusGrantService;
	@Mock
	private SubAccountService subAccountService;
	@Before
	public void mockBefore() {
		MockitoAnnotations.initMocks(this);
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(loanRelationshipService), "lnLoanRelationMapper", lnLoanRelationMapper);
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(loanRelationshipService), "lnRepayScheduleMapper", lnRepayScheduleMapper);
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(loanRelationshipService), "authBalanaceQueryService", authBalanaceQueryService);
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(loanRelationshipService), "bsSubAccountMapper", bsSubAccountMapper);
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(loanRelationshipService), "lnCreditTransferMapper", lnCreditTransferMapper);
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(loanRelationshipService), "lnFinanceRepayScheduleMapper", lnFinanceRepayScheduleMapper);
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(loanRelationshipService), "loanAccountService", loanAccountService);
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(loanRelationshipService), "bsSubAccountPairMapper", bsSubAccountPairMapper);
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(loanRelationshipService), "userMapper", userMapper);
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(loanRelationshipService), "userBonusGrantService", userBonusGrantService);
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(loanRelationshipService), "subAccountService", subAccountService);
	}
	
	
	@Test
	public void selectTest() {
		//超级理财人userId列表
		List<Integer> userIdList = Lists.newArrayList(1,2,3);
		//查询可以借贷关系表理财用户是超级理财用户且剩余金额大于0的列表A
		List<LoanRelationVO> superLoanRelationList = new ArrayList<LoanRelationVO>();
		LoanRelationVO loanRelationVO = new LoanRelationVO();
		loanRelationVO.setId(123);
		loanRelationVO.setLoanId(1);
		loanRelationVO.setBsUserId(1);
		loanRelationVO.setBsSubAccountId(1);
		loanRelationVO.setLnUserId(1);
		loanRelationVO.setLnSubAccountId(1);
		loanRelationVO.setTotalAmount(10000d);
		loanRelationVO.setLeftAmount(10000d);
		loanRelationVO.setStatus("");
		loanRelationVO.setLoanTime(DateUtil.parseDateTime("2016-09-11 11:00:00"));
		loanRelationVO.setPeriod(3);
		superLoanRelationList.add(loanRelationVO);
		
		Mockito.doReturn(superLoanRelationList).when(lnLoanRelationMapper).getSuperLnLoanRelationList(userIdList);
		
		//查询还款计划表中最早一期的记录，判断状态，是否已还款，若已还款，则不能将债权转让
		
		Mockito.doReturn(0).when(lnRepayScheduleMapper).countByLoanIdNotInit(loanRelationVO.getLoanId());
		
		
		//获取当前同周期和固定天数内可匹配的总金额
		Mockito.doReturn(10000d).when(authBalanaceQueryService).getNormalAuthBalance(loanRelationVO.getPeriod(),2,userIdList,null);
		
		//某一日普通理财用户站岗户列表
		 List<BsCanMatch4ZanSubAccountVO> canMatchList = new ArrayList<BsCanMatch4ZanSubAccountVO>();
		 BsCanMatch4ZanSubAccountVO canMatch = new BsCanMatch4ZanSubAccountVO();
		 canMatch.setId(1);
		 canMatch.setAccountId(1);
		 canMatch.setProductId(1);
		 canMatch.setUserId(1);
		 canMatch.setProductType(Constants.PRODUCT_TYPE_AUTH);
		 canMatch.setProductRate(8.0);
		 canMatch.setOpenBalance(20000d);
		 canMatch.setBalance(20000d);
		 canMatch.setStatus(2);
		 canMatch.setInterestBeginDate(DateUtil.parseDate("2016-9-11"));
		 canMatch.setAgentId(15);
		 canMatch.setTerm(loanRelationVO.getPeriod());
		 canMatchList.add(canMatch);
		 Mockito.doReturn(canMatchList).when(bsSubAccountMapper).canMatch4ZanList(Constants.PRODUCT_PROPERTY_SYMBOL_ZAN, 
				 Constants.PRODUCT_TYPE_AUTH, userIdList, "2016-09-11", loanRelationVO.getPeriod(),"no",Mockito.anyDouble());
	     
		 //修改超级用户借贷关系
		 Mockito.doReturn(1).when(lnLoanRelationMapper).updateByPrimaryKeySelective(Mockito.any(LnLoanRelation.class));
		 //新增借贷关系
		 Mockito.doReturn(1).when(lnLoanRelationMapper).insertSelective(Mockito.any(LnLoanRelation.class));
		 ////新增债权转让记录
		 Mockito.doReturn(1).when(lnCreditTransferMapper).insertSelective(Mockito.any(LnCreditTransfer.class));
		//新增理财人还款计划
		 Mockito.doReturn(1).when(lnFinanceRepayScheduleMapper).insertSelective(Mockito.any(LnFinanceRepaySchedule.class));
		 //删除超级用户原来的理财人还款计划
		 List<LnFinanceRepaySchedule> financeList = new ArrayList<LnFinanceRepaySchedule>();
		 Mockito.doReturn(financeList).when(lnFinanceRepayScheduleMapper).selectByExample(Mockito.any(LnFinanceRepayScheduleExample.class));
		 //Mockito.doReturn(1).when(lnFinanceRepayScheduleMapper).deleteByExample(Mockito.any(LnFinanceRepayScheduleExample.class));
		
		 //SF转让记账
		 Mockito.doNothing().when(loanAccountService).chargeSuperTransfer(Mockito.any(SuperTransferInfo.class));
		//查询对应REG_D户
		 List<BsSubAccountPair> pairs = new ArrayList<BsSubAccountPair>();
		 BsSubAccountPair pair = new BsSubAccountPair();
		 pair.setAuthAccountId(1);
		 pair.setId(1);
		 pair.setRegDAccountId(100);
		 pairs.add(pair);
		 Mockito.doReturn(pairs).when(bsSubAccountPairMapper).selectByExample(Mockito.any(BsSubAccountPairExample.class));
		 
		 //查询用户信息，获取
		 BsUser bsUser = new BsUser();
		 bsUser.setRecommendId(1);
		 Mockito.doReturn(bsUser).when(userMapper).selectByPrimaryKey(canMatch.getUserId());
		 //奖励金处理
		 Mockito.doReturn(Constants.BONUS_GRANT_TYPE_ALL).when(userBonusGrantService).getBonusGrantTypeByUserId(canMatch.getUserId());
		 
		 Mockito.doNothing().when(userBonusGrantService).entrustReferrerTakeAll(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyDouble());
		/* Mockito.doNothing().when(userBonusGrantService).entrustEachTakePart(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyDouble());
		 Mockito.doNothing().when(userBonusGrantService).entrustSelfTakePart(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyDouble());
		 Mockito.doNothing().when(userBonusGrantService).referrerTakeAll(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyDouble());
		 Mockito.doNothing().when(userBonusGrantService).eachTakePart(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyDouble());
		 Mockito.doNothing().when(userBonusGrantService).selfTakePart(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyDouble());*/
		 
		//超级用户 新增理财人还款计划 --查询subAccountId
		 BsSubAccount bsSubAccount = new BsSubAccount();
		 bsSubAccount.setProductRate(6.0);
		 
		 Mockito.doReturn(bsSubAccount).when(subAccountService).findSubAccountById(loanRelationVO.getBsSubAccountId());;
		
		 //执行方法
		 boolean flag = loanRelationshipService.superTransferNormal();
		 System.out.println(flag);
		 
		 //验证mock方法是否被调用
		 Mockito.verify(lnLoanRelationMapper).getSuperLnLoanRelationList(userIdList);
		 Mockito.verify(lnRepayScheduleMapper).countByLoanIdNotInit(loanRelationVO.getLoanId());
		 Mockito.verify(authBalanaceQueryService).getNormalAuthBalance(loanRelationVO.getPeriod(),2,userIdList,null);
		 Mockito.verify(bsSubAccountMapper, Mockito.atLeastOnce()).canMatch4ZanList(Constants.PRODUCT_PROPERTY_SYMBOL_ZAN, 
				 Constants.PRODUCT_TYPE_AUTH, userIdList, "2016-09-11", loanRelationVO.getPeriod(),"no",Mockito.anyDouble());
		 Mockito.verify(lnLoanRelationMapper, Mockito.atLeastOnce()).updateByPrimaryKeySelective(Mockito.any(LnLoanRelation.class));
		 //新增借贷关系
		 Mockito.verify(lnLoanRelationMapper, Mockito.atLeastOnce()).insertSelective(Mockito.any(LnLoanRelation.class));
		 ////新增债权转让记录
		 Mockito.verify(lnCreditTransferMapper, Mockito.atLeastOnce()).insertSelective(Mockito.any(LnCreditTransfer.class));
		//新增理财人还款计划
		 Mockito.verify(lnFinanceRepayScheduleMapper, Mockito.atLeastOnce()).insertSelective(Mockito.any(LnFinanceRepaySchedule.class));
		 //删除超级用户原来的理财人还款计划
		 Mockito.verify(lnFinanceRepayScheduleMapper, Mockito.atLeastOnce()).selectByExample(Mockito.any(LnFinanceRepayScheduleExample.class));
		 //Mockito.verify(lnFinanceRepayScheduleMapper, Mockito.atLeastOnce()).deleteByExample(Mockito.any(LnFinanceRepayScheduleExample.class));
		
		 //SF转让记账
		 Mockito.verify(loanAccountService, Mockito.atLeastOnce()).chargeSuperTransfer(Mockito.any(SuperTransferInfo.class));
		
		 //子账户对查询
		 Mockito.verify(bsSubAccountPairMapper, Mockito.atLeastOnce()).selectByExample(Mockito.any(BsSubAccountPairExample.class));
		 
		 //查询用户信息，获取
		 Mockito.verify(userMapper, Mockito.atLeastOnce()).selectByPrimaryKey(canMatch.getUserId());
		 //奖励金处理
		 Mockito.verify(userBonusGrantService, Mockito.atLeastOnce()).getBonusGrantTypeByUserId(canMatch.getUserId());
		 Mockito.verify(userBonusGrantService, Mockito.atLeastOnce()).entrustReferrerTakeAll(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyDouble());
/*		 Mockito.verify(userBonusGrantService, Mockito.atLeastOnce()).entrustEachTakePart(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyDouble());
		 Mockito.verify(userBonusGrantService, Mockito.atLeastOnce()).entrustSelfTakePart(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyDouble());
		 Mockito.verify(userBonusGrantService, Mockito.atLeastOnce()).referrerTakeAll(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyDouble());
		 Mockito.verify(userBonusGrantService, Mockito.atLeastOnce()).eachTakePart(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyDouble());
		 Mockito.verify(userBonusGrantService, Mockito.atLeastOnce()).selfTakePart(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyDouble());*/
		 
		 //超级用户 新增理财人还款计划 --查询subAccountId
		 Mockito.verify(subAccountService,Mockito.atMost(1)).findSubAccountById(loanRelationVO.getBsSubAccountId());
	}
	
}
