package com.pinting.gateway.business.in.facade;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.GlobEnvUtil;
import com.pinting.core.util.MoneyUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.baofoo.out.enums.BaoFooTxnType;
import com.pinting.gateway.baofoo.out.enums.BaofooAgreementPayTxnType;
import com.pinting.gateway.baofoo.out.enums.CutPaymentRespCode;
import com.pinting.gateway.baofoo.out.enums.Pay4AnotherRespCode;
import com.pinting.gateway.baofoo.out.model.req.CutpaymentQueryReq;
import com.pinting.gateway.baofoo.out.model.req.CutpaymentReq;
import com.pinting.gateway.baofoo.out.model.req.Pay4AnotherOnlineTransReq;
import com.pinting.gateway.baofoo.out.model.req.QueryOrderReq;
import com.pinting.gateway.baofoo.out.model.req.SinglePayReq;
import com.pinting.gateway.baofoo.out.model.resp.CutpaymentQueryResp;
import com.pinting.gateway.baofoo.out.model.resp.CutpaymentResp;
import com.pinting.gateway.baofoo.out.model.resp.Pay4AnotherOnlineTransResp;
import com.pinting.gateway.baofoo.out.model.resp.Pay4AnotherResp;
import com.pinting.gateway.baofoo.out.model.resp.Pay4AnotherRespData;
import com.pinting.gateway.baofoo.out.model.resp.QueryOrderResp;
import com.pinting.gateway.baofoo.out.model.resp.SinglePayResp;
import com.pinting.gateway.baofoo.out.service.Pay4AnotherService;
import com.pinting.gateway.baofoo.out.service.SinglePayService;
import com.pinting.gateway.baofoo.out.util.BaoFooHttpUtil;
import com.pinting.gateway.baofoo.out.util.PaymentChannelInfo;
import com.pinting.gateway.baofoo.out.util.PaymentChannelUtil;
import com.pinting.gateway.hessian.message.baofoo.B2GReqMsg_BaoFooAgreementPay_DirectAgreementPay;
import com.pinting.gateway.hessian.message.baofoo.B2GReqMsg_BaoFooAgreementPay_QueryOrder;
import com.pinting.gateway.hessian.message.baofoo.B2GReqMsg_BaoFooCutpayment_Cutpayment;
import com.pinting.gateway.hessian.message.baofoo.B2GReqMsg_BaoFooCutpayment_CutpaymentStatusQuery;
import com.pinting.gateway.hessian.message.baofoo.B2GResMsg_BaoFooAgreementPay_DirectAgreementPay;
import com.pinting.gateway.hessian.message.baofoo.B2GResMsg_BaoFooAgreementPay_QueryOrder;
import com.pinting.gateway.hessian.message.baofoo.B2GResMsg_BaoFooCutpayment_Cutpayment;
import com.pinting.gateway.hessian.message.baofoo.B2GResMsg_BaoFooCutpayment_CutpaymentStatusQuery;

/**
 * 宝付协议支付
 * @project gateway
 * @title BaoFooAgreementPayFacade.java
 * @author Dragon & cat
 * @date 2018-4-1
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description
 */
@Component("BaoFooAgreementPay")
public class BaoFooAgreementPayFacade {
	@Autowired
	private  SinglePayService singlePayService;
	@Autowired
	private Pay4AnotherService pay4AnotherService;
	
	//不需要绑卡的代扣
	public void directAgreementPay(B2GReqMsg_BaoFooAgreementPay_DirectAgreementPay req_b, B2GResMsg_BaoFooAgreementPay_DirectAgreementPay res_b) throws Exception{
		SinglePayReq req = new SinglePayReq();
		req.setSend_time(DateUtil.format(req_b.getSend_time()));
		//req.setMsg_id(req_b.getMsg_id());
		//req.setVersion(req_b.getVersion());
		//req.setTxn_type(BaofooAgreementPayTxnType.SINGLE_PAY.getCode());
		req.setMember_id(req_b.getMember_id());
		req.setTrans_id(req_b.getTrans_id());
		//req.setDgtl_envlp(req_b.getDgtl_envlp());
		req.setUser_id(req_b.getUser_id());
		req.setProtocol_no(req_b.getProtocol_no());//签约协议号（确认支付返回）
		req.setTxn_amt(req_b.getTxn_amt());
		//req.setCard_info(req_b.getCard_info());
		req.setRisk_item(req_b.getRisk_item());
		//req.setReturn_url(req_b.getReturn_url());
		req.setReq_reserved1(req_b.getReq_reserved1());
		req.setReq_reserved2(req_b.getReq_reserved2());
		req.setAdditional_info1(req_b.getAdditional_info1());
		req.setAdditional_info2(req_b.getAdditional_info2());
		req.setSignature(req_b.getSignature());
		req.setRiskItems(req_b.getRiskItems());
		PaymentChannelInfo channel = PaymentChannelUtil.channelInfoMap.get(req_b.getMember_id());
		
		SinglePayResp res = singlePayService.singlePay(req);
		if("S".equals(res.getResp_code()) && req_b.getIsMain() != null && req_b.getIsMain() != 1){
			try {
				//代扣成功，且为辅助通道，发起钱报转账(代扣接口金额为分，转账接口金额为元)
				String transNo = "DKTS"+req_b.getTrans_id();
				Pay4AnotherOnlineTransReq transReq = new Pay4AnotherOnlineTransReq();
		        transReq.setTo_acc_no(GlobEnvUtil.get("baofoo.member.acc.no"));
		        transReq.setTo_acc_name(GlobEnvUtil.get("baofoo.member.acc.name"));
		        transReq.setTo_member_id(BaoFooHttpUtil.memberId);
		        transReq.setTrans_money(MoneyUtil.divide(Double.valueOf(res.getSucc_amt()), 100d).toString());
		        transReq.setTrans_no(transNo);
		        transReq.setTrans_summary("代扣还款转账");

		        Pay4AnotherResp<Pay4AnotherOnlineTransResp> resp = pay4AnotherService.onlineTrans4DiffChannel(transReq,channel);

		        if (Pay4AnotherRespCode.SUCCESS_CODE_00000.getCode().equals(resp.getTrans_content().getTrans_head().getReturn_code())) {
		        	Pay4AnotherOnlineTransResp transResp = JSON.parseObject(((Pay4AnotherRespData) resp.getTrans_content().getTrans_reqDatas().get(0)).getTrans_reqData().toString(), Pay4AnotherOnlineTransResp.class);
		        	if("1".equals(transResp.getState()) || "0".equals(transResp.getState())){
		        		//0：转账中；1：转账成功；
		        		res_b.setPay4OnlineOrderNo(transNo);
		        	}
		        }
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		res_b.setTrans_id(res.getTrans_id());
		res_b.setOrder_id(res.getOrder_id());
		res_b.setSucc_amt(res.getSucc_amt());
		
	}
	
	
	public void queryOrder(B2GReqMsg_BaoFooAgreementPay_QueryOrder req_b, B2GResMsg_BaoFooAgreementPay_QueryOrder res_b) throws Exception{
		QueryOrderReq req = new QueryOrderReq();
		req.setMember_id(req_b.getMember_id());
		req.setOrig_trans_id(req_b.getOrig_trans_id());
		req.setOrig_trade_date(DateUtil.format(req_b.getOrig_trade_date()));
		req.setReq_reserved1(req_b.getReq_reserved1());
		req.setReq_reserved2(req_b.getReq_reserved2());
		req.setAdditional_info1(req_b.getAdditional_info1());
		req.setAdditional_info2(req_b.getAdditional_info2());
		QueryOrderResp res = singlePayService.queryOrder(req);
		res_b.setTrans_id(req_b.getOrig_trans_id());
		res_b.setSucc_amt(Long.valueOf(res.getSucc_amt()));
		res_b.setOrder_id(res.getOrder_id());
		res_b.setResMsg(res.getBiz_resp_msg());
	}
	
}
