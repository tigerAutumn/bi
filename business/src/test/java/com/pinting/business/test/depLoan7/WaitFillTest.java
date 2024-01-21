package com.pinting.business.test.depLoan7;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.pinting.business.test.TestBase;
import com.pinting.gateway.hessian.message.loan7.B2GReqMsg_DepLoan7Notice_WaitFill;
import com.pinting.gateway.out.service.loan7.DepLoan7NoticeService;

/**
 * 
 * @author zhangpeng
 * 测试"待补账申请"接口
 */

public class WaitFillTest extends TestBase  {

	@Autowired
	private  DepLoan7NoticeService depLoan7NoticeService;

  	@Test
    public void waitFillTest() {
  		B2GReqMsg_DepLoan7Notice_WaitFill req = new B2GReqMsg_DepLoan7Notice_WaitFill();
  		req.setOrderNo("1234");
  		req.setFillDate(new Date());
  		req.setFillType("INTEREST");
  		req.setAmount(1000.0);
  		req.setFileUrl("");
  		depLoan7NoticeService.noticeWaitFill(req);
  	}
}
