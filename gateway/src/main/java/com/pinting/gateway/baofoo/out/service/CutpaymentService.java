package com.pinting.gateway.baofoo.out.service;

import com.pinting.gateway.baofoo.out.model.req.CutpaymentQueryReq;
import com.pinting.gateway.baofoo.out.model.req.CutpaymentReq;
import com.pinting.gateway.baofoo.out.model.resp.CutpaymentQueryResp;
import com.pinting.gateway.baofoo.out.model.resp.CutpaymentResp;
import com.pinting.gateway.baofoo.out.util.PaymentChannelInfo;

/**
 * 代扣支付接口
 * @author bianyatian
 * @2016-11-25 上午11:18:24
 */
public interface CutpaymentService {
	
	/**
	 * 宝付无绑卡的代扣支付交易
	 * @param req
	 * @param channel 支付渠道信息
	 * @return
	 * @throws Exception
	 */
	CutpaymentResp cutpayment(CutpaymentReq req,PaymentChannelInfo channel) throws Exception;
	
	/**
	 * 宝付无绑卡的代扣结果查询
	 * @param req
	 * @return
	 * @throws Exception
	 */
	CutpaymentQueryResp cutpaymentQuery(CutpaymentQueryReq req, PaymentChannelInfo channel) throws Exception;

}
