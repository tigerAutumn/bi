package com.pinting.gateway.business.in.facade;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.pinting.core.exception.PTException;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.MoneyUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.enums.PTMessageEnum;
import com.pinting.gateway.exception.PTMessageException;
import com.pinting.gateway.hessian.message.reapal.B2GReqMsg_ReapalQuickPay_BindCardSign;
import com.pinting.gateway.hessian.message.reapal.B2GReqMsg_ReapalQuickPay_Certify;
import com.pinting.gateway.hessian.message.reapal.B2GReqMsg_ReapalQuickPay_MemoryCardSign;
import com.pinting.gateway.hessian.message.reapal.B2GReqMsg_ReapalQuickPay_QueryOrderResult;
import com.pinting.gateway.hessian.message.reapal.B2GReqMsg_ReapalQuickPay_ResendCode;
import com.pinting.gateway.hessian.message.reapal.B2GReqMsg_ReapalQuickPay_SubmitPay;
import com.pinting.gateway.hessian.message.reapal.B2GResMsg_ReapalQuickPay_BindCardSign;
import com.pinting.gateway.hessian.message.reapal.B2GResMsg_ReapalQuickPay_Certify;
import com.pinting.gateway.hessian.message.reapal.B2GResMsg_ReapalQuickPay_MemoryCardSign;
import com.pinting.gateway.hessian.message.reapal.B2GResMsg_ReapalQuickPay_QueryOrderResult;
import com.pinting.gateway.hessian.message.reapal.B2GResMsg_ReapalQuickPay_ResendCode;
import com.pinting.gateway.hessian.message.reapal.B2GResMsg_ReapalQuickPay_SubmitPay;
import com.pinting.gateway.reapal.out.config.ReapalConfig;
import com.pinting.gateway.reapal.out.enums.ReapalQuickPayRespCode;
import com.pinting.gateway.reapal.out.model.req.BindCardSignReq;
import com.pinting.gateway.reapal.out.model.req.MemoryCardSignReq;
import com.pinting.gateway.reapal.out.model.req.QueryOrderResultReq;
import com.pinting.gateway.reapal.out.model.req.ResendCodeReq;
import com.pinting.gateway.reapal.out.model.req.SubmitPayReq;
import com.pinting.gateway.reapal.out.model.resp.BindCardSignResp;
import com.pinting.gateway.reapal.out.model.resp.MemoryCardSignResp;
import com.pinting.gateway.reapal.out.model.resp.QueryOrderResultResp;
import com.pinting.gateway.reapal.out.model.resp.ResendCodeResp;
import com.pinting.gateway.reapal.out.model.resp.SubmitPayResp;
import com.pinting.gateway.reapal.out.service.QuickPayServiceClient;
import com.pinting.gateway.reapal.out.util.ReapalForH5;
import com.pinting.gateway.reapal.out.util.ReapalUtil;

@Component("ReapalQuickPay")
public class ReapalQuickPayFacade {

	@Autowired
	@Qualifier("reapalQuickPayServiceClient")
	private QuickPayServiceClient quickPayServiceClient;
	
	/**
	 * 
	 * @Title: memoryCardSign 
	 * @Description: 融宝储蓄卡签约接口(未绑定用户预下单)
	 * @param req
	 * @param res
	 * @throws
	 */
	public void memoryCardSign(B2GReqMsg_ReapalQuickPay_MemoryCardSign req, B2GResMsg_ReapalQuickPay_MemoryCardSign resp) {
		MemoryCardSignReq memoryReq = new MemoryCardSignReq();
		memoryReq.setMerchant_id(ReapalConfig.merchant_id);
		memoryReq.setCard_no(req.getCardNo());
		memoryReq.setOwner(req.getOwner());
		memoryReq.setCert_type("01");
		memoryReq.setCert_no(req.getCertNo());
		memoryReq.setPhone(req.getPhone());
		memoryReq.setOrder_no(req.getOrderNo());
		memoryReq.setTranstime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		memoryReq.setCurrency("156");
		memoryReq.setTitle("储蓄卡签约");
		memoryReq.setBody("储蓄卡签约");
		memoryReq.setMember_id(req.getUserId());
		memoryReq.setTerminal_type("web");
		memoryReq.setTerminal_info("null_"+req.getUserMacAddr()+"_null");
		memoryReq.setMember_ip(req.getUserIpAddr());
		memoryReq.setSeller_email(ReapalConfig.seller_email);
		memoryReq.setToken_id(ReapalUtil.getUUID());
		memoryReq.setTotal_fee(MoneyUtil.multiply(req.getAmount(), 100).setScale(0, BigDecimal.ROUND_HALF_UP).intValue());
		memoryReq.setNotify_url(ReapalConfig.notify_url);
		memoryReq.setVersion("3.1.3");
		MemoryCardSignResp memoryResp = quickPayServiceClient.memoryCardSignPreOrder(memoryReq);
		if(ReapalQuickPayRespCode.SUCCESS_CODE_0000.getCode().equals(memoryResp.getResult_code())) {
			resp.setBindId(memoryResp.getBind_id());
			resp.setOrderNo(memoryResp.getOrder_no());
			resp.setBankName(memoryResp.getBank_name());
			resp.setBankCode(memoryResp.getBank_code());
			resp.setCertificate(memoryResp.getCertificate());
		}
		else {
			ReapalQuickPayRespCode code = ReapalQuickPayRespCode.find(memoryResp.getResult_code());
			if(code != null) {
				throw new PTException(code.getCode(), StringUtil.left(code.getDescription(), 30));
			}
			else {
				throw new PTException(memoryResp.getResult_code(), memoryResp.getResult_msg()==null?"":StringUtil.left(memoryResp.getResult_msg(), 30));
			}
		}
	}
	
	/**
	 * 
	 * @Title: queryOrderResult 
	 * @Description: 查询支付结果
	 * @param req
	 * @param resp
	 * @throws
	 */
	public void queryOrderResult(B2GReqMsg_ReapalQuickPay_QueryOrderResult req, B2GResMsg_ReapalQuickPay_QueryOrderResult resp){
		
		QueryOrderResultReq queryReq = new QueryOrderResultReq();
		queryReq.setMerchant_id(ReapalConfig.merchant_id);
		queryReq.setVersion(ReapalConfig.version);
		queryReq.setOrder_no(req.getOrderNo());
		
		QueryOrderResultResp queryResp = quickPayServiceClient.queryOrderRusult(queryReq);
			
		resp.setOrderNo(queryResp.getOrder_no());
		resp.setStatus(queryResp.getStatus());
		String amount = StringUtil.isEmpty(queryResp.getTotal_fee())?"0":queryResp.getTotal_fee();
		resp.setAmount(Double.valueOf(amount));
		resp.setTradeNo(queryResp.getTrade_no());
		resp.setTradeDate(StringUtil.isEmpty(queryResp.getTimestamp())?null:DateUtil.parseDateTime(queryResp.getTimestamp()));
		String code = queryResp.getResult_code();
		String errorMsg = queryResp.getResult_msg();
		resp.setResultCode(code);
		resp.setResultMsg(errorMsg);
			
	}
	
	/**
	 * 
	 * @Title: bindCardSign 
	 * @Description: 融宝绑卡签约接口(绑定用户预下单)
	 * @param req
	 * @param res
	 * @throws
	 */
	public void bindCardSign(B2GReqMsg_ReapalQuickPay_BindCardSign req, B2GResMsg_ReapalQuickPay_BindCardSign res) {
		BindCardSignReq bindReq = new BindCardSignReq();
		bindReq.setMerchant_id(ReapalConfig.merchant_id);
		bindReq.setMember_id(req.getUserId());
		bindReq.setBind_id(req.getBindId());
		bindReq.setOrder_no(req.getOrderNo());
		bindReq.setTranstime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		bindReq.setCurrency("156");
		bindReq.setTotal_fee(MoneyUtil.multiply(req.getAmount(), 100).setScale(0, BigDecimal.ROUND_HALF_UP).intValue());
		bindReq.setTitle("绑定签约");
		bindReq.setBody("绑定签约");
		bindReq.setTerminal_type("web");
		bindReq.setTerminal_info("null_"+req.getUserMacAddr()+"_null");
		bindReq.setMember_ip(req.getUserIpAddr());
		bindReq.setSeller_email(ReapalConfig.seller_email);
		bindReq.setToken_id(ReapalUtil.getUUID());
		bindReq.setNotify_url(ReapalConfig.notify_url);
		BindCardSignResp bindResp = quickPayServiceClient.bindCardSignPreOrder(bindReq);
		if(ReapalQuickPayRespCode.SUCCESS_CODE_0000.getCode().equals(bindResp.getResult_code())) {
			res.setBankCode(bindResp.getBank_code());
			res.setBankName(bindResp.getBank_name());
			res.setOrderNo(bindResp.getOrder_no());
		}
		else {
			ReapalQuickPayRespCode code = ReapalQuickPayRespCode.find(bindResp.getResult_code());
			if(code != null) {
				throw new PTException(code.getCode(), StringUtil.left(code.getDescription(), 30));
			}
			else {
				throw new PTException(bindResp.getResult_code(), bindResp.getResult_msg()==null?"":StringUtil.left(bindResp.getResult_msg(), 30));
			}
		}
	}
	
	/**
	 * 
	 * @Title: submitPay 
	 * @Description: 确认支付
	 * @param req
	 * @param res
	 * @throws
	 */
	public void submitPay(B2GReqMsg_ReapalQuickPay_SubmitPay req, B2GResMsg_ReapalQuickPay_SubmitPay res) {
		SubmitPayReq payReq = new SubmitPayReq();
		payReq.setCheck_code(req.getCheckCode());
		payReq.setOrder_no(req.getOrderNo());
		payReq.setMerchant_id(ReapalConfig.merchant_id);
		SubmitPayResp payResp = quickPayServiceClient.submitPay(payReq);
		if(ReapalQuickPayRespCode.SUCCESS_CODE_0000.getCode().equals(payResp.getResult_code()) || ReapalQuickPayRespCode.ERROR_CODE_3081.getCode().equals(payResp.getResult_code())) {
			res.setBankCardType(payResp.getBank_card_type());
			res.setBankCode(payResp.getBank_code());
			res.setBankName(payResp.getBank_name());
			res.setPhone(payResp.getPhone());
			res.setOrderNo(payResp.getOrder_no());
			res.setTradeNo(payResp.getTrade_no());
		}
		else {
			ReapalQuickPayRespCode code = ReapalQuickPayRespCode.find(payResp.getResult_code());
			if(code != null) {
				throw new PTException(code.getCode(), StringUtil.left(code.getDescription(), 30));
			}
			else {
				throw new PTException(payResp.getResult_code(), payResp.getResult_msg()==null?"":StringUtil.left(payResp.getResult_msg(), 30));
			}
		}
	}
	
	/**
	 * 
	 * @Title: certify 
	 * @Description: 卡密鉴权接口
	 * @param req
	 * @param res
	 * @throws
	 */
	public void certify(B2GReqMsg_ReapalQuickPay_Certify req, B2GResMsg_ReapalQuickPay_Certify res) {
		try {
			String terminalType = null;
			String returnUrl = null;
			switch(req.getSource()) {
			case 1: //h5
				returnUrl = ReapalConfig.certify_h5_return_url;
				terminalType = "mobile";
				break;
			case 2: //pc
				returnUrl = ReapalConfig.certify_pc_return_url;
				terminalType = "web";
				break;
			case 3: //android
				returnUrl = ReapalConfig.certify_android_return_url;
				terminalType = "mobile";
				break;
			case 4: //ios
				returnUrl = ReapalConfig.certify_ios_return_url;
				terminalType = "mobile";
				break;
			}
			if(req.getPid() != null) {
				returnUrl += "?pid="+req.getPid();
			}
			String html = ReapalForH5.BuildFormH5(ReapalConfig.merchant_id, req.getOrderNo(), req.getBindId(), String.valueOf(req.getUserId()), terminalType, returnUrl);
			res.setHtml(html);
		}catch(Exception e) {
			e.printStackTrace();
			throw new PTMessageException(PTMessageEnum.RPC_EXCEPTION, "调用Reapal卡密鉴权接口异常");
		}
	}
	
	/**
	 * 
	 * @Title: resendCode 
	 * @Description: 融宝重发验证码
	 * @param req
	 * @param res
	 * @throws
	 */
	public void resendCode(B2GReqMsg_ReapalQuickPay_ResendCode req, B2GResMsg_ReapalQuickPay_ResendCode res) {
		ResendCodeReq resendReq = new ResendCodeReq();
		resendReq.setMerchant_id(ReapalConfig.merchant_id);
		resendReq.setOrder_no(resendReq.getOrder_no());
		ResendCodeResp resendResp = quickPayServiceClient.resendCode(resendReq);
		if(!ReapalQuickPayRespCode.SUCCESS_CODE_0000.getCode().equals(resendResp.getResult_code())) {
			ReapalQuickPayRespCode code = ReapalQuickPayRespCode.find(resendResp.getResult_code());
			if(code != null) {
				throw new PTException(code.getCode(), StringUtil.left(code.getDescription(), 30));
			}
			else {
				throw new PTException(resendResp.getResult_code(), resendResp.getResult_msg()==null?"":StringUtil.left(resendResp.getResult_msg(), 30));
			}
		}
	}
}
