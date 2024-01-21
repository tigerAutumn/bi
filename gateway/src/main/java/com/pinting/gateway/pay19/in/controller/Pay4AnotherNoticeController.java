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
import com.pinting.gateway.hessian.message.pay19.G2BReqMsg_Pay19Pay4Another_Pay4AnotherResultNotice;
import com.pinting.gateway.hessian.message.pay19.G2BResMsg_Pay19Pay4Another_Pay4AnotherResultNotice;
import com.pinting.gateway.pay19.in.model.req.NewMerchantDFReq;
import com.pinting.gateway.pay19.in.util.Pay19NoticeMessageUtil;
import com.pinting.gateway.pay19.out.enums.Pay4AnotherRespCode;

/**
 * 
 * @author Baby shark love blowing wind
 * @version $Id: AcctTransNoticeController.java, v 0.1 2015-8-7 下午2:24:28
 *          BabyShark Exp $
 */
@Controller
@RequestMapping("pay19/pay4another")
public class Pay4AnotherNoticeController {
	private static Logger log = LoggerFactory
			.getLogger(Pay4AnotherNoticeController.class);

	@Autowired
	private SendBusinessService sendBusinessService;

	@RequestMapping("notice")
	public @ResponseBody
	String notice(HttpServletRequest request, HttpServletResponse response,
			final NewMerchantDFReq req) {
		Map<String, String> paramsMap = Pay19NoticeMessageUtil
				.transBeanMap(req);
		log.info("收到通知>>>>>>" + req);
		// 验签
		if (!Pay19NoticeMessageUtil.checkVerifyString(paramsMap)) {
			log.info("消息摘要校验失败>>>>>>"
					+ PTMessageEnum.RETURN_MSG_HASH_ERROR.getMessage());
			req.setExtend1(PTMessageEnum.RETURN_MSG_HASH_ERROR.getMessage());
			// throw new
			// PTMessageException(PTMessageEnum.RETURN_MSG_HASH_ERROR);
		}

		G2BReqMsg_Pay19Pay4Another_Pay4AnotherResultNotice resultReq = new G2BReqMsg_Pay19Pay4Another_Pay4AnotherResultNotice();
		resultReq.setAmount(StringUtil.isEmpty(req.getAmount()) ? 0 : Double
				.valueOf(req.getAmount()));
		resultReq.setExtend1(req.getExtend1());
		resultReq.setExtend2(req.getExtend2());
		resultReq.setExtend3(req.getExtend3());
		resultReq.setFinishTime(DateUtil.parse(req.getFinishTime(),
				"yyyyMMddHHmmss"));
		resultReq.setHmac(req.getHmac());
		resultReq.setMxOrderId(req.getMxOrderId());
		resultReq.setOrderStatus(req.getOrderStatus());
		resultReq.setRetCode(req.getRetCode());
		Pay4AnotherRespCode codeMsg = Pay4AnotherRespCode
				.find(req.getRetCode());
		String errorMsg = codeMsg != null ? codeMsg.getDescription() : req
				.getRetCode();
		resultReq.setErrorMsg(errorMsg);
		resultReq.setSysOrderId(req.getSysOrderId());

		G2BResMsg_Pay19Pay4Another_Pay4AnotherResultNotice result = sendBusinessService
				.sendPay4AnotherResultNotice(resultReq);

		if (!ConstantUtil.BSRESCODE_SUCCESS.equals(result.getResCode())) {
			log.info("["+req.getMxOrderId()+"]处理失败，返回响应值>>>>>>N");
			return "N";
		}
		log.info("["+req.getMxOrderId()+"]处理成功，返回响应值>>>>>>Y");
		return "Y";
	}
}
