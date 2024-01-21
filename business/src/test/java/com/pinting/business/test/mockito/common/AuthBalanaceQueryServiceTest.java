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

import com.google.common.collect.Lists;
import com.pinting.business.dao.BsSubAccountMapper;
import com.pinting.business.model.vo.DailyAmount4LoanVO;
import com.pinting.business.service.loan.AuthBalanaceQueryService;
import com.pinting.business.test.TestBase;
import com.pinting.business.test.mockito.AopTargetUtils;
import com.pinting.business.util.Constants;

/**
 * 查询所有非超级理财人的AUTH账户余额
 * @author bianyatian
 * @2016-9-14 下午3:38:10
 */
public class AuthBalanaceQueryServiceTest extends TestBase {
	@Autowired
	private AuthBalanaceQueryService authBalanaceQueryService;
	
	@Mock
	private BsSubAccountMapper bsSubAccountMapper;

	@Before
	public void mockBefore() {
		MockitoAnnotations.initMocks(this);
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(authBalanaceQueryService),"bsSubAccountMapper",bsSubAccountMapper);
	}
	
	
	@Test
	public void selectTest() {
		//超级理财人userId列表
		List<Integer> superUserList = Lists.newArrayList(1,2,3);
		List<DailyAmount4LoanVO> normalUserList = new ArrayList<DailyAmount4LoanVO>();
		DailyAmount4LoanVO normal = new DailyAmount4LoanVO();
		normal.setSumAmount(5000d);
		normal.setTerm(3);
		normalUserList.add(normal);
		
		Mockito.doReturn(normalUserList).when(bsSubAccountMapper).getSumBalanceByProductType(Mockito.anyString(), 
				Mockito.anyString(), Mockito.anyList(), Mockito.anyString(), Mockito.anyString(),Mockito.anyDouble(),null);
	
		Double normalAmount = authBalanaceQueryService.getNormalAuthBalance(3, 5, superUserList,null);
		System.out.println(normalAmount);
		Mockito.verify(bsSubAccountMapper).getSumBalanceByProductType(Mockito.anyString(), 
				Mockito.anyString(), Mockito.anyList(), Mockito.anyString(), Mockito.anyString(),Mockito.anyDouble(),null);
	}
}
