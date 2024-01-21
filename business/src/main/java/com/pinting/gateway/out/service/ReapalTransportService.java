package com.pinting.gateway.out.service;

import com.pinting.gateway.hessian.message.reapal.B2GReqMsg_ReapalAgentPay_AgentPayQuery;
import com.pinting.gateway.hessian.message.reapal.B2GReqMsg_ReapalAgentPay_AgentPaySubmit;
import com.pinting.gateway.hessian.message.reapal.B2GReqMsg_ReapalQuickPay_BindCardSign;
import com.pinting.gateway.hessian.message.reapal.B2GReqMsg_ReapalQuickPay_Certify;
import com.pinting.gateway.hessian.message.reapal.B2GReqMsg_ReapalQuickPay_MemoryCardSign;
import com.pinting.gateway.hessian.message.reapal.B2GReqMsg_ReapalQuickPay_QueryOrderResult;
import com.pinting.gateway.hessian.message.reapal.B2GReqMsg_ReapalQuickPay_ResendCode;
import com.pinting.gateway.hessian.message.reapal.B2GReqMsg_ReapalQuickPay_SubmitPay;
import com.pinting.gateway.hessian.message.reapal.B2GResMsg_ReapalAgentPay_AgentPayQuery;
import com.pinting.gateway.hessian.message.reapal.B2GResMsg_ReapalAgentPay_AgentPaySubmit;
import com.pinting.gateway.hessian.message.reapal.B2GResMsg_ReapalQuickPay_BindCardSign;
import com.pinting.gateway.hessian.message.reapal.B2GResMsg_ReapalQuickPay_Certify;
import com.pinting.gateway.hessian.message.reapal.B2GResMsg_ReapalQuickPay_MemoryCardSign;
import com.pinting.gateway.hessian.message.reapal.B2GResMsg_ReapalQuickPay_QueryOrderResult;
import com.pinting.gateway.hessian.message.reapal.B2GResMsg_ReapalQuickPay_ResendCode;
import com.pinting.gateway.hessian.message.reapal.B2GResMsg_ReapalQuickPay_SubmitPay;

/**
 * 融宝出金（代付）服务
 * @Project: business
 * @Title: ReapalTransportService.java
 * @author dingpf
 * @date 2016-1-7 下午1:48:27
 * @Copyright: 2016 BiGangWan.com Inc. All rights reserved.
 */
public interface ReapalTransportService {
	
	/**
	 * 融宝单笔出金（代付）提交
	 * @param req
	 * @return
	 */
	public B2GResMsg_ReapalAgentPay_AgentPaySubmit agentPaySubmit(B2GReqMsg_ReapalAgentPay_AgentPaySubmit req);
	
	/**
	 * 融宝出金（代付）查询
	 * @param req
	 * @return
	 */
	public B2GResMsg_ReapalAgentPay_AgentPayQuery agentPayQuery(B2GReqMsg_ReapalAgentPay_AgentPayQuery req);
	
	/**
	 * 
	 * @Title: memoryCardSign 
	 * @Description: 快捷支付储蓄卡签约接口
	 * @param req
	 * @return
	 * @throws
	 */
	public B2GResMsg_ReapalQuickPay_MemoryCardSign memoryCardSign(B2GReqMsg_ReapalQuickPay_MemoryCardSign req);
	
	/**
	 * 
	 * @Title: bindCardSign 
	 * @Description: 绑卡签约接口
	 * @param req
	 * @return
	 * @throws
	 */
	public B2GResMsg_ReapalQuickPay_BindCardSign bindCardSign(B2GReqMsg_ReapalQuickPay_BindCardSign req);

	/**
	 * 快捷支付单笔订单查询
	 * @param req
	 * @return
	 */
	public B2GResMsg_ReapalQuickPay_QueryOrderResult queryOrderResult(B2GReqMsg_ReapalQuickPay_QueryOrderResult req);
	
	/**
	 * 
	 * @Title: submitPay 
	 * @Description: 确认支付接口
	 * @param req
	 * @return
	 * @throws
	 */
	public B2GResMsg_ReapalQuickPay_SubmitPay submitPay(B2GReqMsg_ReapalQuickPay_SubmitPay req);
	
	/**
	 * 
	 * @Title: certify 
	 * @Description: 卡密鉴权接口
	 * @param req
	 * @return
	 * @throws
	 */
	public B2GResMsg_ReapalQuickPay_Certify certify(B2GReqMsg_ReapalQuickPay_Certify req);

	/**
	 * 
	 * @Title: resendCode 
	 * @Description: 融宝重发验证码
	 * @param req
	 * @return
	 * @throws
	 */
	public B2GResMsg_ReapalQuickPay_ResendCode resendCode(B2GReqMsg_ReapalQuickPay_ResendCode req);
}
