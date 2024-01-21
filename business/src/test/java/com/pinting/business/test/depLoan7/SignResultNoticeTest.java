package com.pinting.business.test.depLoan7;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.pinting.business.test.TestBase;
import com.pinting.gateway.hessian.message.loan7.B2GReqMsg_DepLoan7Notice_SignResultNotice;
import com.pinting.gateway.out.service.loan7.DepLoan7NoticeService;

/**
 * 
 * @author zhangpeng
 * 测试"借款协议签章结果通知"接口
 */

public class SignResultNoticeTest extends TestBase {

	@Autowired
	private  DepLoan7NoticeService depLoan7NoticeService;

  	@Test
    public void signResultNoticeTest() {
  		B2GReqMsg_DepLoan7Notice_SignResultNotice req = new B2GReqMsg_DepLoan7Notice_SignResultNotice();
  		req.setAgreementNo("2018020100000208");
  		req.setLoanId("2018020100000208");
  		req.setSignResult("SUCC");
  		req.setAgreementUrl("");
  		depLoan7NoticeService.signResultNotice(req);
    }
}
