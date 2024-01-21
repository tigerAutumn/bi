package com.pinting.gateway.business.in.facade;

import java.text.DecimalFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinting.core.util.DateUtil;
import com.pinting.core.util.GlobEnvUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.hessian.message.pay19.B2GReqMsg_AcctTrans_AcctTrans;
import com.pinting.gateway.hessian.message.pay19.B2GReqMsg_AcctTrans_QueryRecvAcctTrans;
import com.pinting.gateway.hessian.message.pay19.B2GReqMsg_RealName_RealNameAuth;
import com.pinting.gateway.hessian.message.pay19.B2GResMsg_AcctTrans_AcctTrans;
import com.pinting.gateway.hessian.message.pay19.B2GResMsg_AcctTrans_QueryRecvAcctTrans;
import com.pinting.gateway.hessian.message.pay19.B2GResMsg_RealName_RealNameAuth;
import com.pinting.gateway.hessian.message.pay19.enums.RealNameTradeStatus;
import com.pinting.gateway.pay19.out.model.req.AcctTransReq;
import com.pinting.gateway.pay19.out.model.req.QueryRecvAcctTransReq;
import com.pinting.gateway.pay19.out.model.req.RealNameVerifyReq;
import com.pinting.gateway.pay19.out.model.resp.AcctTransResp;
import com.pinting.gateway.pay19.out.model.resp.QueryRecvAcctTransResp;
import com.pinting.gateway.pay19.out.model.resp.RealNameVerifyResp;
import com.pinting.gateway.pay19.out.service.AcctTransServiceClient;
import com.pinting.gateway.pay19.out.service.RealNameServiceClient;
import com.pinting.gateway.pay19.out.util.Pay19HttpUtil;
import com.pinting.gateway.util.Pay19CipherUtil;
import com.pinting.gateway.util.Util;

/**
 * 19付钱包转账
 * 
 * @Project: gateway
 * @Title: AcctTransFacade.java
 * @author dingpf
 * @date 2015-11-17 下午6:17:43
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
@Component("AcctTrans")
public class AcctTransFacade {
	@Autowired
	private AcctTransServiceClient acctTransServiceClient;
	
	/**
	 * 钱包转账
	 * @param req
	 * @param res
	 */
	public void acctTrans(B2GReqMsg_AcctTrans_AcctTrans req,
			B2GResMsg_AcctTrans_AcctTrans res) {
		
		AcctTransReq acctTransReq = new AcctTransReq();
		acctTransReq.setAccountFrom(req.getAccountFrom());
		acctTransReq.setAccountTo(req.getAccountTo());
		acctTransReq.setNotifyUrl(GlobEnvUtil.get("19pay.notice.accttrans"));
		acctTransReq.setOrderAmount(new DecimalFormat("0.00").format(req.getOrderAmount()));
		acctTransReq.setOrderDate(DateUtil.formatDateTime(req.getOrderDate(),
        		"yyyyMMddHHmmss"));
		acctTransReq.setOrderId(req.getOrderId());
		acctTransReq.setRemarks(req.getRemarks());
		acctTransReq.setToAcctName(req.getToAcctName());
		acctTransReq.setToAcctType(req.getToAcctType());
		acctTransReq.setTradeDesc(req.getTradeDesc());
		acctTransReq.setTradeType(req.getTradeType());
		
		AcctTransResp resp = acctTransServiceClient.acctTrans(acctTransReq);
		
		res.setFee(resp.getFee());
		res.setFinTime(resp.getFinTime());
		res.setMerchantId(resp.getMerchantId());
		res.setMpOrderId(resp.getMpOrderId());
		res.setOrderAmount(resp.getOrderAmount());
		res.setOrderId(resp.getOrderId());
		res.setRetCode(resp.getRetCode());
		res.setTradeResult(resp.getTradeResult());
		
	}
	
	/**
	 * 收款方转账订单查询
	 * @param req
	 * @param res
	 */
	public void queryRecvAcctTrans(B2GReqMsg_AcctTrans_QueryRecvAcctTrans req,
			B2GResMsg_AcctTrans_QueryRecvAcctTrans res) {
		
		QueryRecvAcctTransReq transReq = new QueryRecvAcctTransReq();
		transReq.setOriOutMxId(req.getOriOutMxId());
		transReq.setOriOutOrderDate(DateUtil.formatDateTime(req.getOriOutOrderDate(), "yyyyMMdd"));
		transReq.setOriOutOrderId(req.getOriOutOrderId());
		transReq.setTradeType("TRANSFER");
		transReq.setTs(DateUtil.formatDateTime(req.getTs(), "yyyyMMddHHmmss"));
		
		QueryRecvAcctTransResp resp = acctTransServiceClient.queryRecvAcctTrans(transReq);
		
		res.setFee(resp.getFee());
		res.setFinTime(resp.getFinTime());
		res.setMerchantId(resp.getMerchantId());
		res.setMpOrderId(resp.getMpOrderId());
		res.setOrderAmount(resp.getOrderAmount());
		res.setOriOutMxId(resp.getOriOutMxId());
		res.setOriOutOrderId(resp.getReqStatus());
		res.setTradeResult(resp.getTradeResult());
		
	}
	

}
