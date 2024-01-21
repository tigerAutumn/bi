package com.pinting.business.test.depLoan7;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.pinting.business.test.TestBase;
import com.pinting.gateway.hessian.message.loan7.B2GReqMsg_DepLoan7Notice_QueryBill;
import com.pinting.gateway.out.service.loan7.DepLoan7NoticeService;

/**
 * 
 * @author zhangpeng
 * 测试"账单同步查询"接口
 */

public class QueryBillTest extends TestBase  {
		@Autowired
		private  DepLoan7NoticeService depLoan7NoticeService;
	
	  	@Test
	    public void queryBillTest() {
	  		B2GReqMsg_DepLoan7Notice_QueryBill req = new B2GReqMsg_DepLoan7Notice_QueryBill();
	  		req.setUserId("1000561");
	  		req.setLoanId("01111111");
	  		depLoan7NoticeService.queryBill(req);
	    }
}
