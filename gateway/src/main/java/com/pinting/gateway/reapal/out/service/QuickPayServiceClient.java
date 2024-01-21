package com.pinting.gateway.reapal.out.service;

import com.pinting.gateway.reapal.out.model.req.BindCardSignReq;
import com.pinting.gateway.reapal.out.model.req.CancelCardReq;
import com.pinting.gateway.reapal.out.model.req.MemoryCardSignReq;
import com.pinting.gateway.reapal.out.model.req.QueryOrderResultReq;
import com.pinting.gateway.reapal.out.model.req.ResendCodeReq;
import com.pinting.gateway.reapal.out.model.req.SubmitPayReq;
import com.pinting.gateway.reapal.out.model.resp.BindCardSignResp;
import com.pinting.gateway.reapal.out.model.resp.CancelCardResp;
import com.pinting.gateway.reapal.out.model.resp.MemoryCardSignResp;
import com.pinting.gateway.reapal.out.model.resp.QueryOrderResultResp;
import com.pinting.gateway.reapal.out.model.resp.ResendCodeResp;
import com.pinting.gateway.reapal.out.model.resp.SubmitPayResp;

public interface QuickPayServiceClient {

	/**
	 * 
	 * @Title: memoryCardSignPreOrder 
	 * @Description: 储蓄卡签约接口(未绑卡用户预下单)
	 * @param req
	 * @return
	 * @throws
	 */
	public MemoryCardSignResp memoryCardSignPreOrder(MemoryCardSignReq req);
	
	/**
	 * 
	 * @Title: bindCardSignPreOrder 
	 * @Description: 绑卡签约接口(绑卡用户预下单)
	 * @param req
	 * @return
	 * @throws
	 */
	public BindCardSignResp bindCardSignPreOrder(BindCardSignReq req);
	
	/**
	 * 
	 * @Title: submitPay 
	 * @Description: 确认支付接口
	 * @param req
	 * @return
	 * @throws
	 */
	public SubmitPayResp submitPay(SubmitPayReq req);
	
	/**
	 * 
	 * @Title: cancalCard 
	 * @Description: 解绑卡
	 * @param req
	 * @return
	 * @throws
	 */
	public CancelCardResp cancelCard(CancelCardReq req);
	
	/**
	 * 
	 * @Title: queryOrderRusult 
	 * @Description: 支付结果查询
	 * @param req
	 * @return
	 * @throws
	 */
	public QueryOrderResultResp queryOrderRusult(QueryOrderResultReq req);
	
	/**
	 * 
	 * @Title: resendCode 
	 * @Description: 重发验证码
	 * @param req
	 * @return
	 * @throws
	 */
	public ResendCodeResp resendCode(ResendCodeReq req);
	
	
}
