package com.pinting.business.accounting.finance.service.impl.process;

import com.pinting.business.accounting.finance.service.SysProductBuyService;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_Payment_SysBatchBuyProduct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 系统购买产品发起
 * @Project: business
 * @author dingpf
 * @date 2015-11-20 下午5:39:21
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public class SysBuyProductSendProcess implements Runnable {
	private Logger log = LoggerFactory.getLogger(SysBuyProductSendProcess.class);
	private B2GReqMsg_Payment_SysBatchBuyProduct req;
	private SysProductBuyService sysProductBuyService;
	
	@Override
	public void run() {
		//购买产品发起开始
		sysProductBuyService.buyProduct(req);
	}
	public void setReq(B2GReqMsg_Payment_SysBatchBuyProduct req) {
		this.req = req;
	}

	public void setSysProductBuyService(SysProductBuyService sysProductBuyService) {
		this.sysProductBuyService = sysProductBuyService;
	}
}
