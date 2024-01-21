package com.pinting.gateway.pay19.in.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pinting.core.util.ConstantUtil;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.business.out.service.SendBusinessService;
import com.pinting.gateway.enums.PTMessageEnum;
import com.pinting.gateway.exception.PTMessageException;
import com.pinting.gateway.hessian.message.pay19.G2BReqMsg_Pay19NewCounter_NewCounterResultNotice;
import com.pinting.gateway.hessian.message.pay19.G2BResMsg_Pay19NewCounter_NewCounterResultNotice;
import com.pinting.gateway.hessian.message.pay19.enums.AcctAttr;
import com.pinting.gateway.hessian.message.pay19.enums.CardType;
import com.pinting.gateway.hessian.message.pay19.enums.Currency;
import com.pinting.gateway.hessian.message.pay19.enums.OrderStatus;
import com.pinting.gateway.hessian.message.pay19.enums.TradeType;
import com.pinting.gateway.pay19.in.enums.NoticeOrderStatus;
import com.pinting.gateway.pay19.in.util.Pay19NoticeMessageUtil;
import com.pinting.gateway.pay19.out.model.req.EBankNotifyReq;
import com.pinting.gateway.pay19.out.model.req.RefundNotifyReq;
import com.pinting.gateway.pay19.out.model.req.RefundReq;
import com.pinting.gateway.pay19.out.model.resp.RefundResp;

@Controller
@Scope("prototype")
@RequestMapping("pay19/newcounter")
public class NewCounterController {
	private static Logger log = LoggerFactory
			.getLogger(NewCounterController.class);

	@Autowired
	private SendBusinessService sendBusinessService;

	/**
	 * 
	 * @Title: eBankNotify
	 * @Description: 新网银支付接口-异步推送通知
	 * @param @param request
	 * @param @param req
	 * @param @return
	 * @return String
	 * @throws
	 */
	@ResponseBody
	@RequestMapping("/eBankNotify")
	public String eBankNotify(HttpServletRequest request,
			final EBankNotifyReq req) {
		final Map<String, String> paramsMap = Pay19NoticeMessageUtil
				.transBeanMap(req);
		log.info("收到通知>>>>>>" + req);
		// 验签
		if (!Pay19NoticeMessageUtil.checkVerifyString(paramsMap)) {
			throw new PTMessageException(PTMessageEnum.RETURN_MSG_HASH_ERROR);
		}
		
		G2BReqMsg_Pay19NewCounter_NewCounterResultNotice resultReq = new G2BReqMsg_Pay19NewCounter_NewCounterResultNotice();
		resultReq.setAcctAttr(AcctAttr.find(req.getAcctAttr()));
		resultReq.setAcctType(CardType.find(req.getAcctType()));
		resultReq.setAmount(StringUtil.isEmpty(req.getAmount()) ? 0 : Double
				.valueOf(req.getAmount()));
		resultReq.setBankId(req.getBankId());
		resultReq.setCommonRetrievedParam(req.getCommonRetrievedParam());
		resultReq.setCurrency(Currency.RMB);
		resultReq.setMerchantId(req.getMerchantId());
		resultReq.setMpOrderId(req.getMpOrderId());
		resultReq.setMxOrderDate(DateUtil.parse(req.getMxOrderDate(),
				"yyyyMMddHHmmss"));
		resultReq.setMxOrderId(req.getMxOrderId());
		resultReq.setPayDate(DateUtil.parse(req.getPayDate(),
				"yyyyMMddHHmmss"));
		resultReq.setPmId(req.getPmId());
		resultReq.setTradeType(TradeType.E_BANK);
		resultReq.setVerifystring(req.getVerifystring());
		resultReq.setVersion(req.getVersion());
		resultReq.setResult(NoticeOrderStatus.Y.getCode().equals(
				req.getResult()) ? OrderStatus.SUCCESS.getCode()
				: OrderStatus.FAIL.getCode());
		
		G2BResMsg_Pay19NewCounter_NewCounterResultNotice result = sendBusinessService.sendNewCounterResultNotice(resultReq);
		
		if (!ConstantUtil.BSRESCODE_SUCCESS.equals(result.getResCode())) {
			log.info("["+req.getMxOrderId()+"]处理失败，返回响应值>>>>>>N");
			return "N";
		}
		log.info("["+req.getMxOrderId()+"]处理成功，返回响应值>>>>>>Y");
		return "Y";
	}

}
