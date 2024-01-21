package com.pinting.business.test;

import java.util.Date;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.pinting.business.accounting.service.ReconciliationService;
import com.pinting.business.service.manage.BaofooBatchService;
import com.pinting.business.service.manage.BaofooService;
import com.pinting.gateway.hessian.message.dafy.model.InvestmentAmounts;

public class MyTestUtil extends TestBase {
	/*@Test
	public void testMsg(){
		SMSUtils.sendToMobiles("13588449807");
	}*/
	
	@Autowired
	ReconciliationService reconciliationService;
	@Test
	public void test4DafyCheckAccount(){
		List<InvestmentAmounts> list = reconciliationService.checkAccount2Dafy(new Date());
		for (InvestmentAmounts amount : list) {
			System.out.println(amount);
		}
	}
	
	@Autowired
	BaofooBatchService baofooService;
	@Test
	public void testBatchBindCard(String bindCardMobile){
    	String resString = baofooService.batchBindCard(bindCardMobile);
    	if ("success".equals(resString)) {
    		System.out.println("success");
		}else {
			System.out.println("fail");
		}	
	}

}
