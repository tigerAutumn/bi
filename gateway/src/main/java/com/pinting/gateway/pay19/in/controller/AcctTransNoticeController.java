/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.gateway.pay19.in.controller;

import com.pinting.core.util.ConstantUtil;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.business.out.service.SendBusinessService;
import com.pinting.gateway.enums.PTMessageEnum;
import com.pinting.gateway.hessian.message.pay19.G2BReqMsg_Pay19AcctTrans_AcctTransResultNotice;
import com.pinting.gateway.hessian.message.pay19.G2BResMsg_Pay19AcctTrans_AcctTransResultNotice;
import com.pinting.gateway.hessian.message.pay19.enums.AcctTransTradeResult;
import com.pinting.gateway.pay19.in.model.req.AcctTransNoticeReq;
import com.pinting.gateway.pay19.in.util.Pay19NoticeMessageUtil;
import com.pinting.gateway.pay19.out.enums.AcctTransRespCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 19付商户钱包转账
 * @author Baby shark love blowing wind
 * @version $Id: AcctTransNoticeController.java, v 0.1 2015-8-7 下午2:24:28
 *          BabyShark Exp $
 */
@Controller
@RequestMapping("pay19/acctTrans")
public class AcctTransNoticeController {
	private static Logger log = LoggerFactory
			.getLogger(AcctTransNoticeController.class);

	@Autowired
	private SendBusinessService sendBusinessService;
	
	@RequestMapping("notice")
	public @ResponseBody
	String notice(HttpServletRequest request, HttpServletResponse response,
			AcctTransNoticeReq req) {
		Map<String, String> paramsMap = Pay19NoticeMessageUtil
				.transBeanMap(req);
		log.info("收到通知>>>>>>" + req);
		// 验签
		if (!Pay19NoticeMessageUtil.checkVerifyString(paramsMap)) {
			log.info("消息摘要校验失败>>>>>>"
					+ PTMessageEnum.RETURN_MSG_HASH_ERROR.getMessage());
			//throw new PTMessageException(PTMessageEnum.RETURN_MSG_HASH_ERROR);
		}
		//测试
		/*G2BReqMsg_Pay19AcctTrans_AcctTransResultNotice resultReq = new G2BReqMsg_Pay19AcctTrans_AcctTransResultNotice();
		resultReq.setFee(0.0);
		resultReq.setFinTime(DateUtil.parse("20151125211355",
				"yyyyMMddHHmmss"));
		resultReq.setMerchantId("175244");
		resultReq.setMpOrderId("MP11111111111111111111");
		resultReq.setOrderAmount(5d);
		resultReq.setOrderId("TS20151125205500037068788760");
		resultReq.setRetCode("00000");
		resultReq.setTradeResult(AcctTransTradeResult.Y);
		resultReq.setVerifyString("");
		resultReq.setVersionId("");
		resultReq.setErrorMsg("处理成功");*/
		
		
		G2BReqMsg_Pay19AcctTrans_AcctTransResultNotice resultReq = new G2BReqMsg_Pay19AcctTrans_AcctTransResultNotice();
		resultReq.setFee(StringUtil.isEmpty(req.getFee()) ? 0 : Double
				.valueOf(req.getFee()));
		resultReq.setFinTime(DateUtil.parse(req.getFinTime(),
				"yyyyMMddHHmmss"));
		resultReq.setMerchantId(req.getMerchantId());
		resultReq.setMpOrderId(req.getMpOrderId());
		resultReq.setOrderAmount(StringUtil.isEmpty(req.getOrderAmount()) ? 0 : Double
				.valueOf(req.getOrderAmount()));
		resultReq.setOrderId(req.getOrderId());
		resultReq.setRetCode(req.getRetCode());
		resultReq.setTradeResult(AcctTransTradeResult.find(req.getTradeResult()));
		resultReq.setVerifyString(req.getVerifyString());
		resultReq.setVersionId(req.getVersionId());
		resultReq.setErrorMsg(AcctTransRespCode.find(req.getRetCode())!=null?
				AcctTransRespCode.find(req.getRetCode()).getDescription():req.getRetCode());
		
		G2BResMsg_Pay19AcctTrans_AcctTransResultNotice result = sendBusinessService.sendAcctTransResultNotice(resultReq);
		
		if (!ConstantUtil.BSRESCODE_SUCCESS.equals(result.getResCode())) {
			log.info("["+req.getOrderId()+"]处理失败，返回响应值>>>>>>N");
			return "N";
		}
		log.info("["+req.getOrderId()+"]处理成功，返回响应值>>>>>>Y");
		return "Y";

	}
}
