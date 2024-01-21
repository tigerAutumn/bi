package com.pinting.business.test.mockito.loan.authBalance;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.google.common.collect.Lists;
import com.pinting.business.accounting.loan.model.BaseAccount;
import com.pinting.business.accounting.loan.service.LoanAccountService;
import com.pinting.business.accounting.loan.service.LoanRelationshipService;
import com.pinting.business.dao.BsSubAccountMapper;
import com.pinting.business.dayend.task.SuperUserJsh2AuthTask;
import com.pinting.business.model.BsSubAccount;
import com.pinting.business.model.BsSysConfig;
import com.pinting.business.model.vo.BsSubAccountVO;
import com.pinting.business.service.site.SysConfigService;
import com.pinting.business.test.TestBase;
import com.pinting.business.util.Constants;

/**
 * 超级理财人当日余额转站岗任务
 * @author bianyatian
 * @2016-9-13 下午1:32:34
 */
public class SuperUserJsh2AuthTaskTest extends TestBase {

	@InjectMocks
	private SuperUserJsh2AuthTask superUserJsh2AuthTask;
	@Mock
	private LoanAccountService loanAccountService;
	@Mock
	private BsSubAccountMapper bsSubAccountMapper;
	@Mock
	private SysConfigService sysConfigService;
	@Mock
	private LoanRelationshipService loanRelationshipService;
	
	@Before
	public void mockBefore() {
		superUserJsh2AuthTask = new SuperUserJsh2AuthTask();
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void mockTest() {
		//超级理财人userId列表
		List<Integer> superUserList = Lists.newArrayList(1,2,3);
		Mockito.doReturn(superUserList).when(loanRelationshipService).getSuperUserList();
		//超级产品id
		BsSysConfig configPro = new BsSysConfig();
		configPro.setConfKey(Constants.SUPER_PRODUCT_ID);
		configPro.setConfValue("111");
		Mockito.doReturn(configPro).when(sysConfigService).findConfigByKey(Constants.SUPER_PRODUCT_ID);
		
		//查询超级理财人结算户金额大于0的列表
		List<BsSubAccountVO> subAccountList = new ArrayList<BsSubAccountVO>();
		BsSubAccountVO record = new BsSubAccountVO();
		record.setUserId(1);
		record.setBalance(1000d);
		subAccountList.add(record);
		Mockito.doReturn(subAccountList).when(bsSubAccountMapper).selectJSHBySuperUser(superUserList);
		//余额转站岗
		Mockito.doReturn(22).when(loanAccountService).chargeAuthActAdd(Mockito.any(BaseAccount.class), Mockito.anyInt());
		superUserJsh2AuthTask.execute();
		
		
		Mockito.verify(loanRelationshipService).getSuperUserList();
		Mockito.verify(sysConfigService).findConfigByKey(Constants.SUPER_PRODUCT_ID);
		Mockito.verify(bsSubAccountMapper).selectJSHBySuperUser(superUserList);
		Mockito.verify(loanAccountService).chargeAuthActAdd(Mockito.any(BaseAccount.class), Mockito.anyInt());
		
	}
}
