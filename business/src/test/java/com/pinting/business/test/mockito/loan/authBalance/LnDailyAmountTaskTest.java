package com.pinting.business.test.mockito.loan.authBalance;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import antlr.StringUtils;

import com.google.common.collect.Lists;
import com.pinting.business.accounting.loan.service.LoanRelationshipService;
import com.pinting.business.dao.BsSubAccountMapper;
import com.pinting.business.dao.LnDailyAmountMapper;
import com.pinting.business.dayend.task.LnDailyAmountTask;
import com.pinting.business.model.BsSysConfig;
import com.pinting.business.model.LnDailyAmount;
import com.pinting.business.model.LnDailyAmountExample;
import com.pinting.business.model.vo.DailyAmount4LoanVO;
import com.pinting.business.service.loan.AuthBalanaceQueryService;
import com.pinting.business.service.site.SysConfigService;
import com.pinting.business.test.TestBase;
import com.pinting.business.util.Constants;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.StringUtil;

/**
 * 用户站岗资金日终统计
 * @author bianyatian
 * @2016-9-13 上午9:32:56
 */
public class LnDailyAmountTaskTest extends TestBase {

	@InjectMocks
	private	LnDailyAmountTask lnDailyAmountTask;
	
	@Mock
	private BsSubAccountMapper bsSubAccountMapper;
	@Mock
	private SysConfigService sysConfigService;
	@Mock
	private LnDailyAmountMapper lnDailyAmountMapper;
	@Mock
	private AuthBalanaceQueryService authBalanaceQueryService;
	@Mock
	private LoanRelationshipService loanRelationshipService;
	
	
	@Before
	public void mockBefore() {
		lnDailyAmountTask = new LnDailyAmountTask();
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void mockTest() {
		//校验是否已存在当日数据
		List<LnDailyAmount> list = new ArrayList<LnDailyAmount>();
		/*LnDailyAmount lnDailyAmount = new LnDailyAmount();
		lnDailyAmount.setId(1);
		list.add(lnDailyAmount);*/
		Mockito.doReturn(list).when(lnDailyAmountMapper).selectByExample(Mockito.any(LnDailyAmountExample.class));
		
		BsSysConfig configDay = new BsSysConfig();
		int day = 2;
		configDay.setConfKey(Constants.DAY_4_WAIT_LOAN);
		configDay.setConfValue(String.valueOf(day));
		Mockito.doReturn(configDay).when(sysConfigService).findConfigByKey(Constants.DAY_4_WAIT_LOAN); //站岗资金保留天数
		
		//超级理财人userId列表
		List<Integer> superUserList = Lists.newArrayList(1,2,3);
		Mockito.doReturn(superUserList).when(loanRelationshipService).getSuperUserList();
		
		//超级理财人投资金额查询
		Mockito.doReturn(10000d).when(authBalanaceQueryService).getSuperAuthBalance(superUserList);
		//普通理财用户投资列表
		List<DailyAmount4LoanVO> normalUserList = new ArrayList<DailyAmount4LoanVO>();
		DailyAmount4LoanVO dailyAmount4LoanVO = new DailyAmount4LoanVO();
		dailyAmount4LoanVO.setTerm(3);
		dailyAmount4LoanVO.setSumAmount(6000d);
		normalUserList.add(dailyAmount4LoanVO);
		DailyAmount4LoanVO dailyAmount4LoanVO1 = new DailyAmount4LoanVO();
		dailyAmount4LoanVO1.setTerm(6);
		dailyAmount4LoanVO1.setSumAmount(5000d);
		normalUserList.add(dailyAmount4LoanVO1);
		DailyAmount4LoanVO dailyAmount4LoanVO2 = new DailyAmount4LoanVO();
		dailyAmount4LoanVO2.setTerm(12);
		dailyAmount4LoanVO2.setSumAmount(1000d);
		normalUserList.add(dailyAmount4LoanVO2);
		//起息日在此日期之前的
		Date now =new Date();
		String minInterestBeginDate =DateUtil.formatYYYYMMDD( DateUtil.addDays(now, -day));
		Mockito.doReturn(normalUserList).when(bsSubAccountMapper).getSumBalanceByProductType(Constants.PRODUCT_PROPERTY_SYMBOL_ZAN, 
					Constants.PRODUCT_TYPE_AUTH, superUserList, minInterestBeginDate,"no",Mockito.anyDouble(),null);
		Mockito.doReturn(1).when(lnDailyAmountMapper).insertSelective(Mockito.any(LnDailyAmount.class));
		
		lnDailyAmountTask.execute();
		
		
		Mockito.verify(lnDailyAmountMapper).selectByExample(Mockito.any(LnDailyAmountExample.class));
		Mockito.verify(sysConfigService).findConfigByKey(Constants.DAY_4_WAIT_LOAN);
		Mockito.verify(loanRelationshipService).getSuperUserList();
		Mockito.verify(authBalanaceQueryService).getSuperAuthBalance(superUserList);
		Mockito.verify(bsSubAccountMapper).getSumBalanceByProductType(Constants.PRODUCT_PROPERTY_SYMBOL_ZAN, 
				Constants.PRODUCT_TYPE_AUTH, superUserList, minInterestBeginDate,"no",Mockito.anyDouble(),null);
		Mockito.verify(lnDailyAmountMapper).insertSelective(Mockito.any(LnDailyAmount.class));
		
	}
}
