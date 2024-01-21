package com.pinting.business.test.mockito.loan.loanRelation;

import com.google.common.collect.Lists;
import com.pinting.business.accounting.loan.service.LoanAccountService;
import com.pinting.business.accounting.loan.service.LoanRelationshipService;
import com.pinting.business.dao.BsSubAccountMapper;
import com.pinting.business.dao.BsSubAccountPairMapper;
import com.pinting.business.dao.LnLoanRelationMapper;
import com.pinting.business.model.BsSubAccountPair;
import com.pinting.business.model.BsSubAccountPairExample;
import com.pinting.business.model.LnLoanRelation;
import com.pinting.business.model.vo.BsCanMatch4ZanSubAccountVO;
import com.pinting.business.service.loan.AuthBalanaceQueryService;
import com.pinting.business.test.TestBase;
import com.pinting.business.test.mockito.AopTargetUtils;
import com.pinting.business.util.Constants;
import com.pinting.core.util.DateUtil;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 债权匹配单元测试
 * @author bianyatian
 * @2016-9-10 上午11:09:38
 */
public class LoanRelationshipMatchTest extends TestBase {
	
	@Autowired
	private LoanRelationshipService loanRelationshipService;
	@Mock
	private AuthBalanaceQueryService authBalanaceQueryService;
	@Mock
	private BsSubAccountMapper bsSubAccountMapper;
	@Mock
	private LnLoanRelationMapper lnLoanRelationMapper;
	@Mock
	private LoanAccountService loanAccountService;
	@Mock
	private BsSubAccountPairMapper bsSubAccountPairMapper;
	
	 @Before
	 public void mockBefore() {
		 MockitoAnnotations.initMocks(this);
		 ReflectionTestUtils.setField(AopTargetUtils.getTarget(loanRelationshipService), "authBalanaceQueryService",authBalanaceQueryService);
		 ReflectionTestUtils.setField(AopTargetUtils.getTarget(loanRelationshipService), "bsSubAccountMapper", bsSubAccountMapper);
		 ReflectionTestUtils.setField(AopTargetUtils.getTarget(loanRelationshipService), "lnLoanRelationMapper",lnLoanRelationMapper);
		 ReflectionTestUtils.setField(AopTargetUtils.getTarget(loanRelationshipService), "loanAccountService", loanAccountService);
		 ReflectionTestUtils.setField(AopTargetUtils.getTarget(loanRelationshipService), "bsSubAccountPairMapper", bsSubAccountPairMapper);
	 }

	 @Test
	 public void selectTest() {
		 //超级理财人userId列表
		 List<Integer> superUserList = Lists.newArrayList(1,2,3);
		 //获取当前可匹配的总金额
		 Mockito.doReturn(20000d).when(authBalanaceQueryService).getNormalAuthBalance(3,2,superUserList,null);
		 Mockito.doReturn(20000d).when(authBalanaceQueryService).getSuperAuthBalance(superUserList);
		 Integer term = 3;
		 //某一日普通理财用户站岗户列表
		 List<BsCanMatch4ZanSubAccountVO> canMatchList = new ArrayList<BsCanMatch4ZanSubAccountVO>();
		 BsCanMatch4ZanSubAccountVO canMatch = new BsCanMatch4ZanSubAccountVO();
		 canMatch.setId(1);
		 canMatch.setAccountId(1);
		 canMatch.setProductId(1);
		 canMatch.setProductType(Constants.PRODUCT_TYPE_AUTH);
		 canMatch.setProductRate(8.0);
		 canMatch.setOpenBalance(10000d);
		 canMatch.setBalance(10000d);
		 canMatch.setAvailableBalance(10000d);
		 canMatch.setStatus(2);
		 canMatch.setInterestBeginDate(DateUtil.parseDate("2016-10-08"));
		 canMatch.setAgentId(15);
		 canMatch.setTerm(term);
		 canMatchList.add(canMatch);
		 List<BsCanMatch4ZanSubAccountVO> canMatchList1 = new ArrayList<BsCanMatch4ZanSubAccountVO>();
		 BsCanMatch4ZanSubAccountVO canMatch1 = new BsCanMatch4ZanSubAccountVO();
		 canMatch1.setId(1);
		 canMatch1.setAccountId(1);
		 canMatch1.setProductId(1);
		 canMatch1.setProductType(Constants.PRODUCT_TYPE_AUTH);
		 canMatch1.setProductRate(8.0);
		 canMatch1.setOpenBalance(18d);
		 canMatch1.setBalance(18d);
		 canMatch1.setAvailableBalance(18d);
		 canMatch1.setStatus(2);
		 canMatch1.setInterestBeginDate(DateUtil.parseDate("2016-10-09"));
		 canMatch1.setAgentId(null);
		 canMatch1.setTerm(term);
		 canMatchList1.add(canMatch1);
		 
		 Mockito.doReturn(canMatchList).when(bsSubAccountMapper).canMatch4ZanList(Constants.PRODUCT_PROPERTY_SYMBOL_ZAN, 
				 Constants.PRODUCT_TYPE_AUTH, superUserList, "2016-10-08", term,"no",Mockito.anyDouble());
		 Mockito.doReturn(canMatchList1).when(bsSubAccountMapper).canMatch4ZanList(Constants.PRODUCT_PROPERTY_SYMBOL_ZAN, 
				 Constants.PRODUCT_TYPE_AUTH, superUserList, "2016-10-09", term,"no",Mockito.anyDouble());
		 
		 //冻结相应AUTH户的金额--数据不确定，不做验证mock方法是否被调用
		 //Mockito.doNothing().when(loanAccountService).chargeLoanFreeze(100d, 1);
		 
		 //新增借贷关系数据--数据不确定，不做验证mock方法是否被调用
		 LnLoanRelation lnLoanRelationRecord = new LnLoanRelation(); 
		 //Mockito.doNothing().when(lnLoanRelationMapper).insertSelective(lnLoanRelationRecord);
		 
		 //超级理财人子账户列表
		 List<BsCanMatch4ZanSubAccountVO> canMatchList2 = new ArrayList<BsCanMatch4ZanSubAccountVO>();
		 BsCanMatch4ZanSubAccountVO canMatch2 = new BsCanMatch4ZanSubAccountVO();
		 canMatch2.setId(2);
		 canMatch2.setAccountId(2);
		 canMatch2.setProductId(2);
		 canMatch2.setProductType(Constants.PRODUCT_TYPE_AUTH);
		 canMatch2.setProductRate(6.0);
		 canMatch2.setOpenBalance(20000d);
		 canMatch2.setBalance(20000d);
		 canMatch2.setStatus(2);
		 canMatch2.setInterestBeginDate(DateUtil.parseDate("2016-10-10"));
		 canMatch2.setAgentId(null);
		 canMatch2.setTerm(null);
		 canMatchList2.add(canMatch2);
		 Mockito.doReturn(canMatchList2).when(bsSubAccountMapper).canMatch4ZanList(Constants.PRODUCT_PROPERTY_SYMBOL_ZAN, 
				 Constants.PRODUCT_TYPE_AUTH, superUserList, null, null,"yes",Mockito.anyDouble());
		 //AUTH子账户对应REG_D
		 List<BsSubAccountPair> pairs = new ArrayList<BsSubAccountPair>();
		 BsSubAccountPair pair = new BsSubAccountPair();
		 pair.setAuthAccountId(1);
		 pair.setId(1);
		 pair.setRegDAccountId(100);
		 pairs.add(pair);
		 //Mockito.doReturn(pairs).when(bsSubAccountPairMapper).selectByExample(pairExample);
		 Mockito.doReturn(pairs).when(bsSubAccountPairMapper).selectByExample(Mockito.any(BsSubAccountPairExample.class));
		 
		 /*List<BsSubAccountPair> pairs1 = new ArrayList<BsSubAccountPair>();
		 BsSubAccountPair pair1 = new BsSubAccountPair();
		 pair1.setAuthAccountId(2);
		 pair1.setId(1);
		 pair1.setRegDAccountId(200);
		 pairs1.add(pair1);
		 BsSubAccountPairExample pairExample1 = new BsSubAccountPairExample();
			pairExample1.createCriteria().andAuthAccountIdEqualTo(2);
		 Mockito.doReturn(pairs1).when(bsSubAccountPairMapper).selectByExample(pairExample1);
		 */
		 //执行方法
		 List<LnLoanRelation> list = loanRelationshipService.confirmLoanRelation4Loan(111, 112, 113, 20000d, term);
		 System.out.println(list.size());
		 for (LnLoanRelation lnLoanRelation : list) {
			System.out.println(lnLoanRelation.getTotalAmount());
		}
		 
		 //验证mock方法是否被调用
		 Mockito.verify(authBalanaceQueryService).getNormalAuthBalance(term,2,superUserList,null);
		 Mockito.verify(authBalanaceQueryService).getSuperAuthBalance(superUserList);
		 
		 
		 //当一个方法需要执行多次的时候
		 Mockito.verify(bsSubAccountMapper, Mockito.atMost(100)).canMatch4ZanList(Constants.PRODUCT_PROPERTY_SYMBOL_ZAN, 
				 Constants.PRODUCT_TYPE_AUTH, superUserList, "2016-10-08", term,"no",Mockito.anyDouble());
		 Mockito.verify(bsSubAccountMapper, Mockito.atMost(100)).canMatch4ZanList(Constants.PRODUCT_PROPERTY_SYMBOL_ZAN, 
				 Constants.PRODUCT_TYPE_AUTH, superUserList, "2016-10-09", term,"no",Mockito.anyDouble());
		 Mockito.verify(bsSubAccountMapper, Mockito.atMost(100)).canMatch4ZanList(Constants.PRODUCT_PROPERTY_SYMBOL_ZAN, 
				 Constants.PRODUCT_TYPE_AUTH, superUserList, "2016-10-10", term,"yes",Mockito.anyDouble());
		 
		 //数据不确定，不做验证mock方法是否被调用
		 //Mockito.verify(lnLoanRelationMapper).insertSelective(lnLoanRelationRecord);
		 //Mockito.verify(loanAccountService).chargeLoanFreeze(100d, 1);
		 
		 Mockito.doReturn(pairs).when(bsSubAccountPairMapper).selectByExample(Mockito.any(BsSubAccountPairExample.class));
		 
	    }
}
