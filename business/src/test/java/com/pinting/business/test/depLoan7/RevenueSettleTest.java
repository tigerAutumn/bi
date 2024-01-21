package com.pinting.business.test.depLoan7;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.pinting.business.test.TestBase;
import com.pinting.gateway.hessian.message.loan7.B2GReqMsg_DepLoan7Notice_RevenueSettle;
import com.pinting.gateway.out.service.loan7.DepLoan7NoticeService;

/**
 * 
 * @author zhangpeng
 * 测试"营收结算通知"接口
 */

public class RevenueSettleTest extends TestBase {

	@Autowired
	private  DepLoan7NoticeService depLoan7NoticeService;

  	@Test
    public void revenueSettleTest() {
  		B2GReqMsg_DepLoan7Notice_RevenueSettle req = new B2GReqMsg_DepLoan7Notice_RevenueSettle();
  		req.setOrderNo("1234");
  		req.setApplyTime(new Date());
  		req.setFinishTime(new Date());
  		req.setSettleType("LOAN_FEE");
  		req.setAmount(1000.0);
  		req.setFileUrl("");
  		depLoan7NoticeService.noticeRevenueSettle(req);
    }
}
