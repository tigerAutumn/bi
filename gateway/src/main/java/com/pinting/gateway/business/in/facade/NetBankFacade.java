package com.pinting.gateway.business.in.facade;

import java.text.DecimalFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinting.core.util.DateUtil;
import com.pinting.core.util.GlobEnvUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.hessian.message.pay19.B2GReqMsg_AcctTrans_AcctTrans;
import com.pinting.gateway.hessian.message.pay19.B2GReqMsg_NetBank_EBank;
import com.pinting.gateway.hessian.message.pay19.B2GReqMsg_RealName_RealNameAuth;
import com.pinting.gateway.hessian.message.pay19.B2GResMsg_AcctTrans_AcctTrans;
import com.pinting.gateway.hessian.message.pay19.B2GResMsg_NetBank_EBank;
import com.pinting.gateway.hessian.message.pay19.B2GResMsg_RealName_RealNameAuth;
import com.pinting.gateway.hessian.message.pay19.enums.Currency;
import com.pinting.gateway.hessian.message.pay19.enums.RealNameTradeStatus;
import com.pinting.gateway.hessian.message.pay19.enums.TradeType;
import com.pinting.gateway.pay19.out.model.req.AcctTransReq;
import com.pinting.gateway.pay19.out.model.req.EBankReq;
import com.pinting.gateway.pay19.out.model.req.RealNameVerifyReq;
import com.pinting.gateway.pay19.out.model.resp.AcctTransResp;
import com.pinting.gateway.pay19.out.model.resp.RealNameVerifyResp;
import com.pinting.gateway.pay19.out.service.AcctTransServiceClient;
import com.pinting.gateway.pay19.out.service.NewCounterServiceClient;
import com.pinting.gateway.pay19.out.service.RealNameServiceClient;
import com.pinting.gateway.pay19.out.util.Pay19HttpUtil;
import com.pinting.gateway.util.Pay19CipherUtil;
import com.pinting.gateway.util.Util;

/**
 * 19付网银支付请求
 * 
 * @Project: gateway
 * @Title: NetBankFacade.java
 * @author dingpf
 * @date 2015-11-17 下午6:17:43
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
@Component("NetBank")
public class NetBankFacade {
	@Autowired
	private NewCounterServiceClient newCounterServiceClient;
	
	public void eBank(B2GReqMsg_NetBank_EBank req,
			B2GResMsg_NetBank_EBank res) {
		
		EBankReq eBankReq = new EBankReq();
		eBankReq.setMxOrderDate(DateUtil.formatDateTime(req.getOrderDate(),
        		"yyyyMMddHHmmss"));
		eBankReq.setMxOrderId(req.getOrderId());
		String amount = String.format("%.2f", req.getAmount());
		eBankReq.setAmount(amount);
		eBankReq.setCurrency(Currency.RMB.getCode());
		eBankReq.setMxUserIp(req.getMxUserIp());
		if (req.getFlag() != null && "qianbao178NetBankBuy".equals(req.getFlag())) {
			eBankReq.setRetUrl(GlobEnvUtil.get("buy.success.url.gen178"));
			eBankReq.setMxHomePage(GlobEnvUtil.get("buy.fail.url.gen178"));
		}else {
			eBankReq.setRetUrl(GlobEnvUtil.get("buy.success.url.gen"));
			eBankReq.setMxHomePage(GlobEnvUtil.get("buy.fail.url.gen"));
		}
		eBankReq.setNotifyUrl(GlobEnvUtil.get("19pay.notice.newcounter"));
		eBankReq.setOrderPname(req.getOrderPName());
		eBankReq.setOrderPdesc(req.getOrderPDesc());
		eBankReq.setUserMobile(req.getUserMobile());
		eBankReq.setInnerFlag("OUTER");
		eBankReq.setOrderDesc(req.getOrderDesc());
		eBankReq.setTradeType(TradeType.D_BANK.getCode());
		eBankReq.setTradeDesc(req.getTradeDesc());
		eBankReq.setMerchantId(Pay19HttpUtil.merchant_id);
		eBankReq.setBankId(req.getBankId());
		eBankReq.setAcctType("DEBIT");
		eBankReq.setAcctAttr("PRIVATE");
		
		String htmlStr = newCounterServiceClient.eBank(eBankReq);
		
		res.setHtmlStr(htmlStr);
		
	}
	

}
