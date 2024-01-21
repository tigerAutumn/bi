package com.pinting.business.test.depLoan7;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.pinting.business.test.TestBase;
import com.pinting.gateway.hessian.message.loan7.B2GReqMsg_DepLoan7Notice_RepayResultNotice;
import com.pinting.gateway.out.service.loan7.DepLoan7NoticeService;

/**
 * 
 * @author zhangpeng
 * 测试"还款结果通知"接口
 */

public class RepayResultNoticeTest extends TestBase {

	@Autowired
	private  DepLoan7NoticeService depLoan7NoticeService;

  	@Test
    public void repayResultNoticeTest() {
  		B2GReqMsg_DepLoan7Notice_RepayResultNotice req = new B2GReqMsg_DepLoan7Notice_RepayResultNotice();
		req.setBgwOrderNo("BDK20180123153257353039362180");
  		req.setOrderNo("2018012300000001016");
  		req.setFinishTime(new Date());
  		req.setResultMsg("还款成功");
		req.setChannel("BAOFOO");
		req.setChannelLoan("BAOFOO");
		req.setResultCode("SUCCESS");
		depLoan7NoticeService.noticeRepay(req);
  	}
	
}
