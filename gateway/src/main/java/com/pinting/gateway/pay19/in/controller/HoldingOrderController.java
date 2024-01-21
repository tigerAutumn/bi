package com.pinting.gateway.pay19.in.controller;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pinting.core.util.DateUtil;
import com.pinting.gateway.enums.PTMessageEnum;
import com.pinting.gateway.exception.PTMessageException;
import com.pinting.gateway.pay19.in.util.Pay19NoticeMessageUtil;
import com.pinting.gateway.pay19.out.model.req.HoldingOrderNotifyReq;
import com.pinting.gateway.pay19.out.model.req.HoldingOrderQueryReq;
import com.pinting.gateway.pay19.out.model.req.HoldingOrderReq;
import com.pinting.gateway.pay19.out.model.resp.QueryWithHoldingNewResp;
import com.pinting.gateway.pay19.out.model.resp.WithholdingOrderResp;
import com.pinting.gateway.pay19.out.util.Pay19HttpUtil;
import com.pinting.gateway.util.Pay19CipherUtil;

@Controller
@Scope("prototype")
@RequestMapping("pay19/holdingorder")
public class HoldingOrderController {

	/*@Resource(name="holdingOrderServiceClient")
	private HoldingOrderServiceClient holdingOrderService;*/
	
	/**
	 * 
	 * @Title: holdingOrder
	 * @Description: 代扣下单
	 * @param @param request
	 * @param @param response
	 * @param @return
	 * @return String
	 * @throws
	 */
	@RequestMapping("/holdingOrder")
	public String holdingOrder(HttpServletRequest request, HttpServletResponse response) {
    	HoldingOrderReq req = new HoldingOrderReq();
    	req.setMxOrderId("201511101409007");
    	req.setMxUserId("123456789");
    	req.setMxOrderDate(DateUtil.formatDateTime(new Date(), "yyyyMMddHHmmss"));
    	req.setPcId("CCB");
    	req.setBankCardNum(Pay19CipherUtil.encryptData("6217001540000904076", Pay19HttpUtil.merchant_key));
    	req.setCardHolder(Pay19CipherUtil.encryptData("朱媛", Pay19HttpUtil.merchant_key));
    	req.setIdCardNum(Pay19CipherUtil.encryptData("330781198910221128", Pay19HttpUtil.merchant_key));
    	req.setUserMobile(Pay19CipherUtil.encryptData("15158191349", Pay19HttpUtil.merchant_key));
    	req.setAmount("0.01");
    	req.setCurrency("RMB");
    	req.setNotifyUrl("http://121.43.116.175/remote/pay19/holdingorder/holdingOrderNotify");
    	req.setOrderDesc("19付代扣");
    	req.setTradeType("WITH");
    	req.setTradeDesc("19付代扣交易");
    	req.setMxGoodsName("测试:19付代扣");
    	req.setMxGoodsType("2");
    	/*WithholdingOrderResp resp = (WithholdingOrderResp) holdingOrderService.holdingOrder(req);
    	System.out.println(resp.getTradeStatus());*/
		
		return null;
	}
	
	/**
	 * 
	 * @Title: holdingOrderNotify
	 * @Description: 代扣下单-异步推送通知
	 * @param @param request
	 * @param @param req
	 * @param @return
	 * @return String
	 * @throws
	 */
	@ResponseBody
	@RequestMapping("/holdingOrderNotify")
	public String holdingOrderNotify(HttpServletRequest request, final HoldingOrderNotifyReq req) {
		final Map<String, String> paramsMap = Pay19NoticeMessageUtil.transBeanMap(req);
		if (!Pay19NoticeMessageUtil.checkVerifyString(paramsMap)) {
        	throw new PTMessageException(PTMessageEnum.RETURN_MSG_HASH_ERROR);
        }
		return "Y";
	}
	
	/**
	 * 
	 * @Title: holdingOrderQuery
	 * @Description: 代扣订单查询
	 * @param @param request
	 * @param @return
	 * @return String
	 * @throws
	 */
	@RequestMapping("/holdingOrderQuery")
	public String holdingOrderQuery(HttpServletRequest request, HttpServletResponse response) {
		HoldingOrderQueryReq req = new HoldingOrderQueryReq();
    	req.setMxOrderId("201511101409007");
    	req.setMxOrderDate("20151110200318");
    	req.setMxUserId("123456789");
    	req.setTs(DateUtil.formatDateTime(new Date(), "yyyyMMddHHmmss"));
    	/*QueryWithHoldingNewResp resp = (QueryWithHoldingNewResp) holdingOrderService.holdingOrderQuery(req);
    	System.err.println(resp.getRetCode());*/
		return null;
	}

}
