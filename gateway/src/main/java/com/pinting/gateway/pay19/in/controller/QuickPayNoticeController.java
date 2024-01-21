/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.gateway.pay19.in.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pinting.core.util.ConstantUtil;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.business.out.service.SendBusinessService;
import com.pinting.gateway.enums.PTMessageEnum;
import com.pinting.gateway.exception.PTMessageException;
import com.pinting.gateway.hessian.message.pay19.G2BReqMsg_Pay19QuickPay_PayResultNotice;
import com.pinting.gateway.hessian.message.pay19.G2BResMsg_Pay19QuickPay_PayResultNotice;
import com.pinting.gateway.hessian.message.pay19.enums.OrderStatus;
import com.pinting.gateway.pay19.in.enums.NoticeOrderStatus;
import com.pinting.gateway.pay19.in.model.req.PayNoticeReq;
import com.pinting.gateway.pay19.in.util.Pay19NoticeMessageUtil;
import com.pinting.gateway.pay19.out.enums.QuickPayRespCode;

/**
 * 
 * @author Baby shark love blowing wind
 * @version $Id: QuickPayNoticeController.java, v 0.1 2015-8-7 下午2:24:28
 *          BabyShark Exp $
 */
@Controller
@RequestMapping("pay19/quickPay")
public class QuickPayNoticeController {
	private static Logger log = LoggerFactory
			.getLogger(QuickPayNoticeController.class);

	@Autowired
	private SendBusinessService sendBusinessService;

	@RequestMapping("notice")
	public @ResponseBody
	String notice(HttpServletRequest request, HttpServletResponse response,
			PayNoticeReq req) {
		Map<String, String> paramsMap = Pay19NoticeMessageUtil
				.transBeanMap(req);
		log.info("收到通知>>>>>>" + req);
		// 验签
		if (!Pay19NoticeMessageUtil.checkVerifyString(paramsMap)) {
			throw new PTMessageException(PTMessageEnum.RETURN_MSG_HASH_ERROR);
		}

		G2BReqMsg_Pay19QuickPay_PayResultNotice resultReq = new G2BReqMsg_Pay19QuickPay_PayResultNotice();
		resultReq.setAmount(StringUtil.isEmpty(req.getAmount()) ? 0 : Double
				.valueOf(req.getAmount()));
		resultReq.setCommonRetrieveParam(req.getCommon_retrieve_param());
		resultReq.setCurrency(req.getCurrency());
		resultReq.setErrorCode(req.getError_code());
		QuickPayRespCode codeMsg = QuickPayRespCode.find(req.getError_code());
		codeMsg = codeMsg != null ? codeMsg : QuickPayRespCode.find("C"
				+ req.getError_code());

		String errorMsg = codeMsg != null ? codeMsg.getDescription() : req
				.getError_code();
		resultReq.setErrorMsg(errorMsg);
		resultReq.setMpOrderId(req.getMp_orderid());
		resultReq.setOrderDate(DateUtil.parse(req.getOrder_date(),
				"yyyyMMddHHmmss"));
		resultReq.setOrderFinTime(DateUtil.parse(req.getOrder_fin_time(),
				"yyyyMMddHHmmss"));
		resultReq.setOrderId(req.getOrder_id());
		resultReq.setOrderPName(req.getOrder_pname());
		resultReq.setOrderRemarkDesc(req.getOrder_remark_desc());
		resultReq.setOrderSubTime(DateUtil.parse(req.getOrder_sub_time(),
				"yyyyMMddHHmmss"));
		resultReq.setStatus(NoticeOrderStatus.Y.getCode().equals(
				req.getStatus()) ? OrderStatus.SUCCESS.getCode()
				: OrderStatus.FAIL.getCode());
		resultReq.setTradeType(req.getTrade_type());
		resultReq.setUserId(req.getMx_userid());
		resultReq.setVerifyString(req.getVerifystring());

		G2BResMsg_Pay19QuickPay_PayResultNotice result = sendBusinessService
				.sendPayResultNotice(resultReq);
		if (!ConstantUtil.BSRESCODE_SUCCESS.equals(result.getResCode())) {
			log.info("["+req.getOrder_id()+"]处理失败，返回响应值>>>>>>N");
			return "N";
		}
		log.info("["+req.getOrder_id()+"]处理成功，返回响应值>>>>>>Y");
		return "Y";
	}
}
