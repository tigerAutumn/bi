package com.pinting.gateway.in.facade;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinting.business.service.common.SmsPlatformsDealService;
import com.pinting.gateway.hessian.message.hfbank.G2BReqMsg_Sms_CL253ReportNotice;
import com.pinting.gateway.hessian.message.hfbank.G2BResMsg_Sms_CL253ReportNotice;

@Component("Sms")
public class SmsFacade {
	
	@Autowired
	private SmsPlatformsDealService smsPlatformsDealService;

	private Logger log = LoggerFactory.getLogger(SmsFacade.class);
	
	/**
	 * 创蓝报告接收
	 * @param req
	 * @param res
	 */
	public void cL253ReportNotice(G2BReqMsg_Sms_CL253ReportNotice req,G2BResMsg_Sms_CL253ReportNotice res){
		smsPlatformsDealService.chuanglan253Check(req.getMobile(), req.getMsgid(), req.getStatus(), req.getReportTime());
	}
}
