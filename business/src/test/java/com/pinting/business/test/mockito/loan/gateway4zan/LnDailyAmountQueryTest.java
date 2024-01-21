package com.pinting.business.test.mockito.loan.gateway4zan;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

import com.pinting.business.dao.LnDailyAmountMapper;
import com.pinting.business.model.LnDailyAmount;
import com.pinting.business.model.LnDailyAmountExample;
import com.pinting.business.service.loan.LoanQueryService;
import com.pinting.business.test.TestBase;
import com.pinting.business.test.mockito.AopTargetUtils;
import com.pinting.gateway.hessian.message.loan.G2BReqMsg_Loan_DailyAmount;
import com.pinting.gateway.hessian.message.loan.G2BResMsg_Loan_DailyAmount;

/**
 * 蜂鸟查询币港湾当日可用额度
 * @author bianyatian
 * @2016-9-20 下午2:47:42
 */
public class LnDailyAmountQueryTest extends TestBase {
	@Autowired
	private LoanQueryService loanQueryService;
	@Mock
	private LnDailyAmountMapper lnDailyAmountMapper;
	
	@Before
	public void mockBefore() {
		MockitoAnnotations.initMocks(this);
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(loanQueryService), "lnDailyAmountMapper", lnDailyAmountMapper);
	}
	@Test
	public void selectTest() {
		//对方法设定预期返回值
		List<LnDailyAmount> list = new ArrayList<LnDailyAmount>();
		LnDailyAmount dailyAmount = new LnDailyAmount();
		dailyAmount.setTerm3Amount(30000d);
		dailyAmount.setTerm6Amount(60000d);
		dailyAmount.setTerm9Amount(90000d);
		dailyAmount.setTerm12Amount(120000d);
		dailyAmount.setTermxAmount(11110000d);
		list.add(dailyAmount);
		Mockito.doReturn(list).when(lnDailyAmountMapper).selectByExample(Mockito.any(LnDailyAmountExample.class));
		G2BReqMsg_Loan_DailyAmount req = new G2BReqMsg_Loan_DailyAmount();
		G2BResMsg_Loan_DailyAmount res = new G2BResMsg_Loan_DailyAmount();
		
		try {
			loanQueryService.getDailyAmount(req, res);
			System.out.println("3："+res.getAmount3()+"；6："+res.getAmount6()
					+"；9："+res.getAmount9()+"；12："+res.getAmount12()
					+"；super："+res.getAmountNoLimit());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Mockito.verify(lnDailyAmountMapper).selectByExample(Mockito.any(LnDailyAmountExample.class));
		
	}
}
