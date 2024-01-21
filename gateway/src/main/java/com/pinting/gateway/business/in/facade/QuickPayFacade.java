package com.pinting.gateway.business.in.facade;

import java.text.DecimalFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinting.core.util.DateUtil;
import com.pinting.core.util.GlobEnvUtil;
import com.pinting.gateway.hessian.message.pay19.B2GReqMsg_QuickPay_ConfirmOrder;
import com.pinting.gateway.hessian.message.pay19.B2GReqMsg_QuickPay_PreOrder;
import com.pinting.gateway.hessian.message.pay19.B2GReqMsg_QuickPay_QueryMOrder;
import com.pinting.gateway.hessian.message.pay19.B2GReqMsg_QuickPay_RSendSms;
import com.pinting.gateway.hessian.message.pay19.B2GResMsg_QuickPay_ConfirmOrder;
import com.pinting.gateway.hessian.message.pay19.B2GResMsg_QuickPay_PreOrder;
import com.pinting.gateway.hessian.message.pay19.B2GResMsg_QuickPay_QueryMOrder;
import com.pinting.gateway.hessian.message.pay19.B2GResMsg_QuickPay_RSendSms;
import com.pinting.gateway.hessian.message.pay19.enums.OrderStatus;
import com.pinting.gateway.hessian.message.pay19.enums.TradeResult;
import com.pinting.gateway.pay19.out.enums.QuickPayRespCode;
import com.pinting.gateway.pay19.out.enums.QuickPayRespStatus;
import com.pinting.gateway.pay19.out.model.req.ConfirmOrderReq;
import com.pinting.gateway.pay19.out.model.req.PreOrderReq;
import com.pinting.gateway.pay19.out.model.req.QueryMOrderReq;
import com.pinting.gateway.pay19.out.model.req.RSendSmsReq;
import com.pinting.gateway.pay19.out.model.resp.ConfirmOrderResp;
import com.pinting.gateway.pay19.out.model.resp.PreOrderResp;
import com.pinting.gateway.pay19.out.model.resp.QueryMOrderResp;
import com.pinting.gateway.pay19.out.model.resp.RSendSmsResp;
import com.pinting.gateway.pay19.out.service.QuickPayServiceClient;
import com.pinting.gateway.pay19.out.util.Pay19HttpUtil;
import com.pinting.gateway.util.Pay19CipherUtil;
import com.pinting.gateway.util.Pay19KeyedDigestMD5;
import com.pinting.gateway.util.Util;


/**
 * 快捷支付
 * 
 * @Project: gateway
 * @Title: QuickPayFacade.java
 * @author dingpf
 * @date 2015-11-17 下午6:17:43
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
@Component("QuickPay")
public class QuickPayFacade {
	@Autowired
	private QuickPayServiceClient quickPayServiceClient;
	
	public void preOrder(B2GReqMsg_QuickPay_PreOrder req,
			B2GResMsg_QuickPay_PreOrder res) {
        PreOrderReq preOrderReq = new PreOrderReq();
        preOrderReq.setMx_userid(req.getUserId());
        preOrderReq.setBind_sno(req.getBindSNo());
        preOrderReq.setOrder_id(req.getOrderId());
        preOrderReq.setOrder_date(DateUtil.formatDateTime(req.getOrderDate(),
        		"yyyyMMddHHmmss"));
        DecimalFormat format = new DecimalFormat("0.00");
        preOrderReq.setAmount(format.format(req.getAmount()));
        preOrderReq.setOrder_pname(req.getOrderPName());
        preOrderReq.setOrder_pdesc(req.getOrderPDesc());
        preOrderReq.setPc_id(req.getPcId());
        preOrderReq.setBank_card_num(Pay19CipherUtil.encryptData(req.getBankCardNum(),
            Pay19HttpUtil.merchant_key));
        preOrderReq.setCvv2(Pay19CipherUtil.encryptData(req.getCvv2(), Pay19HttpUtil.merchant_key));
        preOrderReq.setValid_date(Pay19CipherUtil.encryptData(req.getValidDate(),
            Pay19HttpUtil.merchant_key));
        preOrderReq.setCard_holder(Pay19CipherUtil.encryptData(req.getCardHolder(),
            Pay19HttpUtil.merchant_key));
        preOrderReq.setId_type(req.getIdType() != null ? req.getIdType().getCode() : null);
        preOrderReq.setId_cardnum(Pay19CipherUtil.encryptData(
            Util.formatIdNo2Upper(req.getIdCardnum()), Pay19HttpUtil.merchant_key));
        preOrderReq.setMobile(Pay19CipherUtil.encryptData(req.getMobile(),
            Pay19HttpUtil.merchant_key));
        preOrderReq.setCurrency(req.getCurrency() != null ? req.getCurrency().getCode() : null);
        preOrderReq.setAcct_type(req.getAcctType() != null ? req.getAcctType().getCode() : null);
        preOrderReq.setAcct_attr(req.getAcctAttr() != null ? req.getAcctAttr().getCode() : null);
        preOrderReq.setTrade_type(req.getTradeType().getCode());
        preOrderReq.setTrade_desc(req.getTradeDesc());
        //preOrderReq.setPage_notify_url(req.getPageNotifyUrl());
        //preOrderReq.setNotify_url(req.getNotifyUrl());
        preOrderReq.setPage_notify_url(GlobEnvUtil.get("19pay.notice.quickpay.page"));
        preOrderReq.setNotify_url(GlobEnvUtil.get("19pay.notice.quickpay"));
        preOrderReq.setIs_bind(req.getIsBind().getCode());
        preOrderReq.setOrder_desc(req.getOrderDesc());
        preOrderReq.setOrder_remark_desc(req.getOrderRemarkDesc());
        preOrderReq.setIsFixBindInfo(req.getIsFixBindInfo() != null ? req.getIsFixBindInfo()
            .getCode() : null);
        preOrderReq.setOvertime_interval("4320");
        preOrderReq.setMx_goods_type(req.getMxGoodsType().getCode());

        PreOrderResp resp = quickPayServiceClient.preOrder(preOrderReq);

        if (QuickPayRespStatus.SUCCESS.getCode().equals(resp.getStatus())) {
            res.setMpOrderId(resp.getMp_orderid());
            res.setOrderDate(DateUtil.parse(resp.getOrder_date(), "yyyyMMddHHmmss"));
            res.setOrderId(resp.getOrder_id());
            res.setUserId(resp.getMx_userid());
            res.setErrorCode(resp.getError_code());
        } else if (QuickPayRespStatus.FAIL.getCode().equals(resp.getStatus())
                   && QuickPayRespCode.ERROR_CODE_31017.getCode().equals(resp.getError_code())) {
            res.setSignUrl(resp.getSignUrl());
            StringBuffer text = new StringBuffer();
            text.append("mxId=").append(resp.getMerchant_id()).append("&signUrl=")
                .append(resp.getSignUrl()).append("&versionId=").append(resp.getVersion_id());
            String verifyString = Pay19KeyedDigestMD5.getKeyedDigest(text.toString(),
                Pay19HttpUtil.merchant_key);
            text.append("&verifyString=").append(verifyString);
            res.setSignParam(text.toString());
            res.setErrorCode(resp.getError_code());

        }

	}
	
	public void confirmOrder(B2GReqMsg_QuickPay_ConfirmOrder req,
			B2GResMsg_QuickPay_ConfirmOrder res) {
        ConfirmOrderReq confirmOrderReq = new ConfirmOrderReq();
        confirmOrderReq.setMx_userid(req.getUserId());
        confirmOrderReq.setOrder_id(req.getOrderId());
        confirmOrderReq.setOrder_date(DateUtil.formatDateTime(req.getOrderDate(),
        		"yyyyMMddHHmmss"));
        DecimalFormat format = new DecimalFormat("0.00");
        confirmOrderReq.setAmount(format.format(req.getAmount()));
        confirmOrderReq.setMp_orderid(req.getMpOrderId());
        confirmOrderReq.setVerifyCode(req.getVerifyCode());

        ConfirmOrderResp resp = quickPayServiceClient.confirmOrder(confirmOrderReq);

        res.setFinTime(DateUtil.parse(resp.getFin_time(), "yyyyMMddHHmmss"));
        res.setMpOrderId(resp.getMp_orderid());
        res.setOrderDate(DateUtil.parse(resp.getOrder_date(), "yyyyMMddHHmmss"));
        res.setOrderId(resp.getOrder_id());
        res.setTradeResult(TradeResult.find(resp.getTrade_result()));
        res.setUserId(resp.getMx_userid());
        QuickPayRespCode code = QuickPayRespCode.find("C" + resp.getError_code());
        String errMsg = code != null ? code.getDescription() : "支付平台返回码：C" + resp.getError_code();
        res.setErrorMsg(errMsg);
        res.setErrorCode("C" + resp.getError_code());
    }
	
	public void rSendSms(B2GReqMsg_QuickPay_RSendSms req,
			B2GResMsg_QuickPay_RSendSms res) {
        RSendSmsReq rSendSmsReq = new RSendSmsReq();
        rSendSmsReq.setMx_userid(req.getUserId());
        rSendSmsReq.setOrder_id(req.getOrderId());
        rSendSmsReq.setOrder_date(DateUtil.formatDateTime(req.getOrderDate(),
        		"yyyyMMddHHmmss"));
        rSendSmsReq.setMp_orderid(req.getMpOrderId());
        rSendSmsReq.setVerifycode_sendflag(req.getVerifyCodeSendFlag().getCode());

        RSendSmsResp resp = quickPayServiceClient.rSendSms(rSendSmsReq);

        res.setMpOrderId(resp.getMp_orderid());
        res.setOrderDate(DateUtil.parse(resp.getOrder_date(), "yyyyMMddHHmmss"));
        res.setOrderId(resp.getOrder_id());
	}
	
	
	public void queryMOrder(B2GReqMsg_QuickPay_QueryMOrder req,
			B2GResMsg_QuickPay_QueryMOrder res) {
        QueryMOrderReq queryMOrderReq = new QueryMOrderReq();
        queryMOrderReq.setMx_userid(req.getUserId());
        queryMOrderReq.setOrder_id(req.getOrderId());
        queryMOrderReq.setOrder_date(DateUtil.formatDateTime(req.getOrderDate(),
        		"yyyyMMddHHmmss"));
        queryMOrderReq.setTs(DateUtil.formatDateTime(req.getTs(), "yyyyMMddHHmmss"));

        QueryMOrderResp resp = quickPayServiceClient.queryMOrder(queryMOrderReq);
        res.setFinishTime(DateUtil.parse(resp.getFinish_time(), "yyyyMMddHHmmss"));
        res.setMpOrderId(resp.getMp_orderid());
        res.setOrderDate(DateUtil.parse(resp.getOrder_date(), "yyyyMMddHHmmss"));
        res.setOrderId(resp.getOrder_id());
        res.setUserId(resp.getMx_userid());
        res.setOrderStatus(OrderStatus.find(resp.getOrder_status()));
	}
	
	
}
