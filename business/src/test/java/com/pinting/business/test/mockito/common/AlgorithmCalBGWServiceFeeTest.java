package com.pinting.business.test.mockito.common;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

import com.pinting.business.dao.BsSysConfigMapper;
import com.pinting.business.dao.LnFinanceRepayScheduleMapper;
import com.pinting.business.dao.LnLoanMapper;
import com.pinting.business.model.BsSysConfig;
import com.pinting.business.model.LnLoan;
import com.pinting.business.service.common.AlgorithmService;
import com.pinting.business.test.TestBase;
import com.pinting.business.test.mockito.AopTargetUtils;
import com.pinting.business.util.Constants;
/**
 * 计算某笔借款某期应提币港湾服务费
 * @author bianyatian
 * @2016-9-14 下午5:23:23
 */
public class AlgorithmCalBGWServiceFeeTest extends TestBase {

	@Autowired
	private AlgorithmService algorithmService;
	
	@Mock
	private LnLoanMapper lnLoanMapper;
	@Mock
	private BsSysConfigMapper bsSysConfigMapper;
	@Mock
	private LnFinanceRepayScheduleMapper lnFinanceRepayScheduleMapper;

	@Before
    public void mockBefore() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(AopTargetUtils.getTarget(algorithmService), "lnLoanMapper", lnLoanMapper);
        ReflectionTestUtils.setField(AopTargetUtils.getTarget(algorithmService), "bsSysConfigMapper", bsSysConfigMapper);
        ReflectionTestUtils.setField(AopTargetUtils.getTarget(algorithmService), "lnFinanceRepayScheduleMapper", lnFinanceRepayScheduleMapper);
	}
	
	@Test
	public void selectTest() {
		LnLoan loan = new LnLoan();
		loan.setApproveAmount(36000d);
		loan.setPeriod(3);
		Mockito.doReturn(loan).when(lnLoanMapper).selectByPrimaryKey(1);
		BsSysConfig config = new BsSysConfig();
		config.setConfKey(Constants.ZAN_YEAR_RATE_3);
		config.setConfValue("12");
		Mockito.doReturn(config).when(bsSysConfigMapper).selectByPrimaryKey(Constants.ZAN_YEAR_RATE_3);
		Mockito.doReturn(11010d).when(lnFinanceRepayScheduleMapper).sumAmountByLoanIdSerialId(1, 1);
		
		Double amount = algorithmService.calBGWServiceFee(1, 1);
		System.out.println(amount);
		Mockito.verify(lnLoanMapper).selectByPrimaryKey(1);
		Mockito.verify(bsSysConfigMapper).selectByPrimaryKey(Constants.ZAN_YEAR_RATE_3);
		Mockito.verify(lnFinanceRepayScheduleMapper).sumAmountByLoanIdSerialId(1, 1);
	}
	
}
