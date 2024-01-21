package com.pinting.business.test.qidian;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.pinting.business.test.TestBase;
import com.pinting.core.util.MoneyUtil;
import com.pinting.gateway.hessian.message.loan7.B2GReqMsg_DepLoan7Notice_WaitFill;
import com.pinting.gateway.hessian.message.qidian.B2GReqMsg_QiDianNotice_CustomerInfoSync;
import com.pinting.gateway.hessian.message.qidian.model.Customers;
import com.pinting.gateway.out.service.loan7.DepLoan7NoticeService;
import com.pinting.gateway.out.service.qidian.QiDianNoticeService;

/**
 * 
 * @project business
 * @title WaitFillTest.java
 * @author Dragon & cat
 * @date 2018-3-22
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description
 */
public class CustomerInfoSyncTest extends TestBase  {

	@Autowired
	private  QiDianNoticeService qiDianNoticeService;

  	@Test
    public void customerInfoSyncTest() {
  		B2GReqMsg_QiDianNotice_CustomerInfoSync req = new B2GReqMsg_QiDianNotice_CustomerInfoSync();

  		List<Customers> cList = new ArrayList<>();
  		Customers customers = new Customers();
  		customers.setFranchiseeId("1000012");
  		customers.setCustomerId("CustomerId007");
  		customers.setCustomerName("CustomerName龙猫");
  		customers.setCustomerMobile("15868157902");
  		customers.setCustomerLevel(1);
  		customers.setRealNameStatus("Y");
  		customers.setIdCardNo("370***********3714");
  		customers.setRemainInvest(100000d);
  		customers.setTotalInvest(500000d);
  		customers.setRegisterTime(new Date());
  		customers.setLoginTime(new Date());
  		cList.add(customers);
  		Customers customers2 = new Customers();
  		customers2.setFranchiseeId("1000012");
  		customers2.setCustomerId("CustomerId008");
  		customers2.setCustomerName("CustomerName龙猫2");
  		customers2.setCustomerMobile("15868157903");
  		customers2.setCustomerLevel(1);
  		customers2.setRealNameStatus("Y");
  		customers2.setIdCardNo("370***********3714");
  		customers2.setRemainInvest(100000d);
  		customers2.setTotalInvest(500000d);
  		customers2.setRegisterTime(new Date());
  		customers2.setLoginTime(new Date());
  		cList.add(customers2);
  		
  		req.setType(1);
  		req.setCustomers(cList);
  		
  		qiDianNoticeService.customerInfoSync(req);
  	}
}
