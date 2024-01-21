package com.pinting.gateway.in.facade;

import com.pinting.business.accounting.finance.model.SysActTransResultInfo;
import com.pinting.business.accounting.finance.service.SysProductBuyService;
import com.pinting.gateway.hessian.message.pay19.G2BReqMsg_Pay19AcctTrans_AcctTransResultNotice;
import com.pinting.gateway.hessian.message.pay19.G2BResMsg_Pay19AcctTrans_AcctTransResultNotice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 系统钱包转账结果通知（打款给19付达飞账户 结果通知）
 * @Project: business
 * @Title: Pay19AcctTransFacade.java
 * @author dingpf
 * @date 2015-11-20 下午2:36:38
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
@Component("Pay19AcctTrans")
public class Pay19AcctTransFacade {
	private Logger log = LoggerFactory.getLogger(Pay19AcctTransFacade.class);
	@Autowired
	private SysProductBuyService sysProductBuyService;
	
	public void acctTransResultNotice(G2BReqMsg_Pay19AcctTrans_AcctTransResultNotice req,
			G2BResMsg_Pay19AcctTrans_AcctTransResultNotice res){
		
		log.info("====================>【打款给达飞结果通知】Business平台已收到打款给达飞结果通知"+req+"，开始进行业务处理");
		SysActTransResultInfo resultInfo = new SysActTransResultInfo();
		resultInfo.setErrorMsg(req.getErrorMsg());
		resultInfo.setFee(req.getFee());
		resultInfo.setFinTime(req.getFinTime());
		resultInfo.setMpOrderId(req.getMpOrderId());
		resultInfo.setOrderAmount(req.getOrderAmount());
		resultInfo.setOrderId(req.getOrderId());
		resultInfo.setRetCode(req.getRetCode());
		resultInfo.setTradeResult(req.getTradeResult());
		sysProductBuyService.notifyTrans2PartnerResult(resultInfo);
		
	}
}
