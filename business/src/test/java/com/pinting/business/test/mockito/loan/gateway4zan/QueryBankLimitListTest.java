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

import com.pinting.business.dao.Bs19payBankMapper;
import com.pinting.business.model.vo.Bs19payBankVO;
import com.pinting.business.service.site.BankCardService;
import com.pinting.business.test.TestBase;
import com.pinting.business.test.mockito.AopTargetUtils;
import com.pinting.business.util.Constants;
import com.pinting.gateway.hessian.message.loan.model.BankLimit;
/**
 * 获取当前时间，银行限额列表
 * @author bianyatian
 * @2016-9-20 下午3:11:05
 */
public class QueryBankLimitListTest extends TestBase {
	
	@Autowired
	private BankCardService bankCardService;
	
	@Mock
	private Bs19payBankMapper pay19BankMapper;
	
	@Before
	public void mockBefore() {
		MockitoAnnotations.initMocks(this);
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(bankCardService), "pay19BankMapper", pay19BankMapper);
	}
	
	@Test
	public void selectTest() {
		//对方法设定预期返回值
		List<Bs19payBankVO> quickList = new ArrayList<Bs19payBankVO>();
		Bs19payBankVO quickRecord1 = new Bs19payBankVO();
		quickRecord1.setOneTop(10000d);
		quickRecord1.setDayTop(20000d);
		quickRecord1.setPay19BankCode("ICBC");
		quickRecord1.setName("工商银行");
		quickList.add(quickRecord1);
		Bs19payBankVO quickRecord2 = new Bs19payBankVO();
		quickRecord2.setOneTop(12000d);
		quickRecord2.setDayTop(22000d);
		quickRecord2.setPay19BankCode("ABC");
		quickRecord2.setName("农业银行");
		quickList.add(quickRecord2);
		Mockito.doReturn(quickList).when(pay19BankMapper).bankLimitListByPayType(Constants.PAY_TYPE_QUICK);
		
		
		List<Bs19payBankVO> dfList = new ArrayList<Bs19payBankVO>();
		Bs19payBankVO dfRecord1 = new Bs19payBankVO();
		dfRecord1.setOneTop(11000d);
		dfRecord1.setDayTop(21000d);
		dfRecord1.setPay19BankCode("CMB");
		dfRecord1.setName("招商银行");
		dfList.add(dfRecord1);
		Bs19payBankVO dfRecord2 = new Bs19payBankVO();
		dfRecord2.setOneTop(13000d);
		dfRecord2.setDayTop(24000d);
		dfRecord2.setPay19BankCode("ABC");
		dfRecord2.setName("农业银行");
		dfList.add(dfRecord2);
		Mockito.doReturn(dfList).when(pay19BankMapper).bankLimitListByPayType(Constants.PAY_TYPE_PAYMENT);
		
		
		List<BankLimit> list = bankCardService.selectBankLimitList();
		for (BankLimit bankLimit : list) {
			System.out.println(bankLimit.getPayType());
			System.out.println(bankLimit.getBankLimits().size());
		}
		
		Mockito.verify(pay19BankMapper).bankLimitListByPayType(Constants.PAY_TYPE_QUICK);
		Mockito.verify(pay19BankMapper).bankLimitListByPayType(Constants.PAY_TYPE_PAYMENT);
	}

}
