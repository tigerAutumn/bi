package com.pinting.business.test.zsd;

import java.util.Date;

import com.pinting.business.test.TestBase;
import com.pinting.core.util.DateUtil;
import com.pinting.gateway.hessian.message.zsd.B2GReqMsg_ZsdNotice_NoticeLoan;
import com.pinting.gateway.hessian.message.zsd.B2GReqMsg_ZsdNotice_NoticeRepay;
import com.pinting.gateway.hessian.message.zsd.B2GResMsg_ZsdNotice_NoticeLoan;
import com.pinting.gateway.hessian.message.zsd.B2GResMsg_ZsdNotice_NoticeRepay;
import com.pinting.gateway.out.service.zsd.ZsdNoticeService;

import net.sf.json.JSONObject;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 借款结果通知
 * @project business
 * @title TestLoanNotice.java
 * @author Dragon & cat
 * @date 2017-9-1
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description
 */
public class TestLoanNotice extends TestBase {

    private Logger logger = LoggerFactory.getLogger(TestLoanNotice.class);
     
    @Autowired 
    private ZsdNoticeService zsdNoticeService;

    
	 /* @Test
	  public void loanNoticeTest() {
		  B2GReqMsg_ZsdNotice_NoticeLoan req = new B2GReqMsg_ZsdNotice_NoticeLoan();
		  req.setOrderNo("8f720d49465749f9acb55e3725c4c01d");
		  req.setLoanId("8f720d49465749f9acb55e3725c4c01d");
		  req.setChannel("19PAY");
		  req.setLoanResultCode("SUCCESS");
		  req.setLoanResultMsg("借款成功");
		  req.setLoanTime(DateUtil.formatYYYYMMDD(new Date()));
		  B2GResMsg_ZsdNotice_NoticeLoan res = zsdNoticeService.noticeLoan(req);
		  logger.info("结果：{}", JSONObject.fromObject(res));
	  }*/
	  
	  @Test
	  public void repayNoticeTest() {
		  B2GReqMsg_ZsdNotice_NoticeRepay req = new B2GReqMsg_ZsdNotice_NoticeRepay();
		  req.setOrderNo("8c7785c1b07b49f8a52b2b3c17d547e2");
		  req.setLoanId("3746");
		  req.setChannel("19PAY");
		  req.setRepayResultCode("SUCCESS");
		  req.setRepayResultMsg("还款成功");
		  req.setSendTime(DateUtil.formatYYYYMMDD(new Date()));
		  B2GResMsg_ZsdNotice_NoticeRepay res = zsdNoticeService.noticeRepay(req);
		  logger.info("结果：{}", JSONObject.fromObject(res));
	  }
   
}