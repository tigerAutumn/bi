package com.pinting.business.accounting.service;

import com.pinting.gateway.hessian.message.pay19.G2BReqMsg_Pay19Pay4Another_Pay4AnotherResultNotice;
import com.pinting.gateway.hessian.message.pay19.G2BResMsg_Pay19Pay4Another_Pay4AnotherResultNotice;

/**
 * 代付通知处理中心，根据订单信息表中的交易码区分具体交易
 * @Project: business
 * @Title: Pay4AnotherNoticeCenterService.java
 * @author Zhou Changzai
 * @date 2015-11-25上午10:27:43
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public interface Pay4AnotherNoticeCenterService {
	/**
	 * 根据交易码分发代付通知，调起不同的业务
	 * @param req
	 * @param res
	 */
	public void dispatcher(G2BReqMsg_Pay19Pay4Another_Pay4AnotherResultNotice req, G2BResMsg_Pay19Pay4Another_Pay4AnotherResultNotice res);
}


