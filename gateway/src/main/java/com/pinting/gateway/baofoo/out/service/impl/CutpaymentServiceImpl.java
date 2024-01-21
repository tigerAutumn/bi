package com.pinting.gateway.baofoo.out.service.impl;

import java.util.HashMap;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.pinting.gateway.baofoo.out.enums.BaoFooTxnType;
import com.pinting.gateway.baofoo.out.enums.CutPaymentRespCode;
import com.pinting.gateway.baofoo.out.enums.QuickPayRespCode;
import com.pinting.gateway.baofoo.out.model.req.CutpaymentQueryReq;
import com.pinting.gateway.baofoo.out.model.req.CutpaymentReq;
import com.pinting.gateway.baofoo.out.model.resp.CutpaymentQueryResp;
import com.pinting.gateway.baofoo.out.model.resp.CutpaymentResp;
import com.pinting.gateway.baofoo.out.service.CutpaymentService;
import com.pinting.gateway.baofoo.out.util.BaoFooHttpUtil;
import com.pinting.gateway.baofoo.out.util.BaoFooMessageUtil;
import com.pinting.gateway.baofoo.out.util.PaymentChannelInfo;
import com.pinting.gateway.enums.PTMessageEnum;
import com.pinting.gateway.exception.PTMessageException;

/**
 * 代扣支付接口实现
 * @author bianyatian
 * @2016-11-25 上午11:18:19
 */
@Service
public class CutpaymentServiceImpl implements CutpaymentService {

	@Override
	public CutpaymentResp cutpayment(CutpaymentReq req,PaymentChannelInfo channel) throws Exception {
		 HashMap<String, String> reqParamMap = new HashMap<>();
		 //私有参数
		 reqParamMap.put("txn_sub_type", BaoFooTxnType.TXN_SUB_TYPE_CUT_PAYMENT);
		 //向宝付发送请求
		 String resp = BaoFooHttpUtil.cutPaymentForm(BaoFooHttpUtil.baofooCutPaymentUrl, req,reqParamMap,channel);
		 if (StringUtils.isBlank(resp)) {
			 throw new PTMessageException(PTMessageEnum.BUS_PROCESSING);
		 }
		 
		 CutpaymentResp res = (CutpaymentResp)BaoFooMessageUtil.parseResp4CutPayment(resp, "CutpaymentResp",channel);
		 //成功
		 if (res.getResp_code().equals(CutPaymentRespCode.SUCCESS_CODE_0000.getCode())) {
			 return res;
		 } else {
			 //失败
			 if (CutPaymentRespCode.find(res.getResp_code())!=null && CutPaymentRespCode.find(res.getResp_code()).getStatus().contains("FAIL")) {
				 throw new PTMessageException(PTMessageEnum.RPC_EXCEPTION, res.getResp_code() + "," + res.getResp_msg());
			 }
			 //处理中
			 else {
				 throw new PTMessageException(PTMessageEnum.BUS_PROCESSING);
			 }
		 }

	}

	@Override
	public CutpaymentQueryResp cutpaymentQuery(CutpaymentQueryReq req,PaymentChannelInfo channel)
			throws Exception {
		HashMap<String, String> reqParamMap = new HashMap<>();
		//私有参数
		reqParamMap.put("txn_sub_type", BaoFooTxnType.TXN_SUB_TYPE_QUICK_PAY_STATUS_QUERY);
		//向宝付发送请求
		String resp = BaoFooHttpUtil.cutPaymentForm(BaoFooHttpUtil.baofooCutPaymentUrl, req,reqParamMap, channel);
		if (StringUtils.isBlank(resp)) {
			throw new PTMessageException(PTMessageEnum.BUS_PROCESSING);
		}
		CutpaymentQueryResp res =(CutpaymentQueryResp)BaoFooMessageUtil.parseResp4CutPayment(resp, "CutpaymentQueryResp", channel);
		//成功
        if (res.getResp_code().equals(CutPaymentRespCode.SUCCESS_CODE_0000.getCode())) {
        	return res;
        }else {
            //失败
            if(CutPaymentRespCode.find(res.getResp_code())!=null && CutPaymentRespCode.find(res.getResp_code()).getStatus().equals("BIZ_FAIL")){
                throw new PTMessageException(PTMessageEnum.BIZ_PAY_FAIL, ":" + res.getResp_code() + "," + res.getResp_msg());
            }else {
                throw new PTMessageException(PTMessageEnum.BUS_PROCESSING);
            }

        }
	}

}
