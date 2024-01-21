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
import com.pinting.gateway.hessian.message.qidian.B2GReqMsg_QiDianNotice_OrderInfoSync;
import com.pinting.gateway.hessian.message.qidian.model.Customers;
import com.pinting.gateway.hessian.message.qidian.model.OrderInfos;
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
public class OrderInfoSyncTest extends TestBase  {

	@Autowired
	private  QiDianNoticeService qiDianNoticeService;

  	@Test
    public void customerInfoSyncTest() {
  		B2GReqMsg_QiDianNotice_OrderInfoSync req = new B2GReqMsg_QiDianNotice_OrderInfoSync();

  		
  		List<OrderInfos> orderInfos = new ArrayList<OrderInfos>();
  		for (int i = 2; i < 4; i++) {
  	  		OrderInfos orderInfo = new OrderInfos();
  	  		orderInfo.setOrderNo("orderNo0001"+i);
  			orderInfo.setCustomerId("CustomerId007");
  			orderInfo.setPartnerName("云贷");
  			orderInfo.setProductId(1);
  			orderInfo.setProductName("港湾计划001");
  			orderInfo.setProductTerm(90);
  			orderInfo.setInvestStatus("FINISH");
  			orderInfo.setInvestStatusDesc("产品购买");
  			orderInfo.setReturnType("FINISH_RETURN_ALL");
  			orderInfo.setBaseRate(MoneyUtil.multiply(6.5d, 100).intValue());
  			orderInfo.setIsRaise("N");
  			orderInfo.setRaiseRate(0);
  			orderInfo.setOpenBalance(5000d);
  			orderInfo.setBalance(5000d);
  			orderInfo.setExpectYield(400d);
  			orderInfo.setOpenTime(new Date());
  			orderInfo.setInterestBeginDate(new Date());
  			orderInfo.setUpdateTime(new Date());
  			orderInfo.setPlanDate(new Date());
  			orderInfo.setYield(0d);
  			orderInfo.setReturnDate(new Date());
  			orderInfo.setIsDiscount("Y");
  			orderInfo.setDiscountAmount(5d);
  			orderInfos.add(orderInfo);
		}
		req.setType(2);
		req.setOrderInfos(orderInfos);
		
		qiDianNoticeService.orderInfoSync(req);
  	}
}
