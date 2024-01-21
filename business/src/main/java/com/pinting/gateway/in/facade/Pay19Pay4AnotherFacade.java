package com.pinting.gateway.in.facade;

import com.pinting.business.accounting.service.Pay4AnotherNoticeCenterService;
import com.pinting.gateway.hessian.message.pay19.G2BReqMsg_Pay19Pay4Another_Pay4AnotherResultNotice;
import com.pinting.gateway.hessian.message.pay19.G2BResMsg_Pay19Pay4Another_Pay4AnotherResultNotice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 
 * @Project: business
 * @Title: Pay19Pay4AnotherFacade.java
 * @author Zhou Changzai
 * @date 2015-11-25下午1:06:02
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
@Component("Pay19Pay4Another")
public class Pay19Pay4AnotherFacade {
	@Autowired
	private Pay4AnotherNoticeCenterService pay4AnotherNoticeCenterService;
	
	public void pay4AnotherResultNotice(G2BReqMsg_Pay19Pay4Another_Pay4AnotherResultNotice req, G2BResMsg_Pay19Pay4Another_Pay4AnotherResultNotice res){
		pay4AnotherNoticeCenterService.dispatcher(req, res);
	}
}


