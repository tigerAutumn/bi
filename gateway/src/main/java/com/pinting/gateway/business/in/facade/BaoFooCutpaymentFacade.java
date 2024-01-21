package com.pinting.gateway.business.in.facade;

import java.util.Date;

import com.pinting.core.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.pinting.gateway.baofoo.out.enums.BaoFooTxnType;
import com.pinting.gateway.baofoo.out.enums.CutPaymentRespCode;
import com.pinting.gateway.baofoo.out.enums.Pay4AnotherRespCode;
import com.pinting.gateway.baofoo.out.model.req.CutpaymentQueryReq;
import com.pinting.gateway.baofoo.out.model.req.CutpaymentReq;
import com.pinting.gateway.baofoo.out.model.req.Pay4AnotherOnlineTransReq;
import com.pinting.gateway.baofoo.out.model.resp.CutpaymentQueryResp;
import com.pinting.gateway.baofoo.out.model.resp.CutpaymentResp;
import com.pinting.gateway.baofoo.out.model.resp.Pay4AnotherOnlineTransResp;
import com.pinting.gateway.baofoo.out.model.resp.Pay4AnotherResp;
import com.pinting.gateway.baofoo.out.model.resp.Pay4AnotherRespData;
import com.pinting.gateway.baofoo.out.service.CutpaymentService;
import com.pinting.gateway.baofoo.out.service.Pay4AnotherService;
import com.pinting.gateway.baofoo.out.util.BaoFooHttpUtil;
import com.pinting.gateway.baofoo.out.util.PaymentChannelInfo;
import com.pinting.gateway.baofoo.out.util.PaymentChannelUtil;
import com.pinting.gateway.hessian.message.baofoo.B2GReqMsg_BaoFooCutpayment_Cutpayment;
import com.pinting.gateway.hessian.message.baofoo.B2GReqMsg_BaoFooCutpayment_CutpaymentStatusQuery;
import com.pinting.gateway.hessian.message.baofoo.B2GResMsg_BaoFooCutpayment_Cutpayment;
import com.pinting.gateway.hessian.message.baofoo.B2GResMsg_BaoFooCutpayment_CutpaymentStatusQuery;

/**
 * 宝付代扣
 * @author bianyatian
 * @2016-11-24 上午10:08:22
 */
@Component("BaoFooCutpayment")
public class BaoFooCutpaymentFacade {
	@Autowired
	private CutpaymentService cutpaymentService;
	@Autowired
	private Pay4AnotherService pay4AnotherService;
	
	//不需要绑卡的代扣
	public void cutpayment(B2GReqMsg_BaoFooCutpayment_Cutpayment req_b, B2GResMsg_BaoFooCutpayment_Cutpayment res_b) throws Exception{
		CutpaymentReq req = new CutpaymentReq();
		req.setPay_code(req_b.getPay_code());
		
		req.setAcc_no(req_b.getAcc_no());
		req.setId_card_type(BaoFooTxnType.ID_CARD_TYPE);
		req.setId_card(req_b.getId_card());
		req.setId_holder(req_b.getId_holder());
		req.setMobile(req_b.getMobile());
		req.setTrans_id(req_b.getTrans_id());
		req.setTxn_amt(req_b.getTxnAmt());
		req.setTrade_date(DateUtil.formatDateTime(new Date(), "yyyyMMddHHmmss"));
		req.setAdditional_info(req_b.getAdditional_info()); //附加字段
		req.setReq_reserved(""); //请求方保留域
		req.setTrans_serial_no(req_b.getTrans_serial_no());
		req.setPay_code(req_b.getPay_code());
		PaymentChannelInfo channel = PaymentChannelUtil.channelInfoMap.get(req_b.getMerchantNo());
		
		CutpaymentResp res = cutpaymentService.cutpayment(req,channel);
		if(CutPaymentRespCode.SUCCESS_CODE_0000.getCode().equals(res.getResp_code()) && req_b.getIsMain() != null && req_b.getIsMain() != 1){
			try {
				//代扣成功，且为辅助通道，发起钱报转账(代扣接口金额为分，转账接口金额为元)
				String transNo = "DKTS"+req_b.getTrans_id();
				Pay4AnotherOnlineTransReq transReq = new Pay4AnotherOnlineTransReq();
		        transReq.setTo_acc_no(GlobEnvUtil.get("baofoo.member.acc.no"));
		        transReq.setTo_acc_name(GlobEnvUtil.get("baofoo.member.acc.name"));
		        transReq.setTo_member_id(BaoFooHttpUtil.memberId);
		        transReq.setTrans_money(MoneyUtil.divide(Double.valueOf(req_b.getTxnAmt()), 100d).toString());
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
		res_b.setTrans_no(res.getTrans_no());
		res_b.setSucc_amt(res.getSucc_amt());
		
	}
	
	public void cutpaymentStatusQuery(B2GReqMsg_BaoFooCutpayment_CutpaymentStatusQuery req_b, B2GResMsg_BaoFooCutpayment_CutpaymentStatusQuery res_b) throws Exception{
		PaymentChannelInfo channel = null;
		CutpaymentQueryReq req = new CutpaymentQueryReq();
		req.setOrig_trans_id(req_b.getOrig_trans_id());
		req.setAdditional_info("");
		req.setReq_reserved("");
		req.setTrade_date(DateUtil.formatDateTime(new Date(), "yyyyMMddHHmmss"));
		req.setTrans_serial_no(req_b.getTrans_serial_no());
		if(StringUtil.isNotBlank(req_b.getMerchantNo())){
			channel = PaymentChannelUtil.channelInfoMap.get(req_b.getMerchantNo());
		}
		CutpaymentQueryResp res = cutpaymentService.cutpaymentQuery(req,channel);
		res_b.setTrans_id(res.getTrans_id());
		res_b.setSucc_amt(res.getSucc_amt());
		res_b.setTrans_no(res.getTrans_no());
		res_b.setResMsg(res.getResp_msg());
	}

}
