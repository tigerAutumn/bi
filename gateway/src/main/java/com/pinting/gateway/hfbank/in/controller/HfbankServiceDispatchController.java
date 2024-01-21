package com.pinting.gateway.hfbank.in.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.pinting.gateway.hfbank.in.model.*;
import com.pinting.gateway.hfbank.in.service.HfbankInService;
import com.pinting.gateway.hfbank.in.util.HfbankInConstant;
import com.pinting.gateway.hfbank.in.util.HfbankInMsgUtil;

/**
 * 恒丰银行资金存管
 * 根据报文交易码，进行服务分发
 * 
 * @Project: gateway
 * @Title: HfbankServiceDispatchController.java
 * @author Dragon & cat
 * @date  2016-12-19
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
@Controller
public class HfbankServiceDispatchController {

	private Logger log = LoggerFactory
			.getLogger(HfbankServiceDispatchController.class);
	
	
	@Autowired
	private	HfbankInService	hfbankInService;
	
	/**
	 *标的出账通知
	 * @param request
	 * @param response
	 * @param req
     * @return
     */
	@RequestMapping(value = "/hfbank/business/outOfAccount", method = RequestMethod.POST)
	public @ResponseBody
	String serviceDispatch(HttpServletRequest request,
			HttpServletResponse response, OutOfAccountReqModel reqModel) {
		Map<String, String[]> paramMap = request.getParameterMap();
		log.info("标的出账通知request参数:["+paramMap+"]");
		// 待返回报文密文
		OutOfAccountResModel outOfAccountResModel = new OutOfAccountResModel();
		String resp = null;
		if (reqModel == null ) {
			outOfAccountResModel.setRecode(HfbankInConstant.RETURN_CODE_PARAMETER_EMPTY_ERROR);
			resp = JSON.toJSONString(outOfAccountResModel);
			return resp;
		}
		log.info("标的出账回调通知Body参数:" + JSONObject.fromObject(reqModel));
		boolean checkSignData = HfbankInMsgUtil.checkSignData(reqModel);

		if (!checkSignData) {
			outOfAccountResModel.setRecode(HfbankInConstant.RETURN_CODE_SIGN_DATA_ERROR);
			resp = JSON.toJSONString(outOfAccountResModel);
		    log.info("标的出账回调通知验签结果{verify signature fail!}");
			return resp;
		}
		log.info("标的出账回调通知验签结果{verify signature succ!}");
		outOfAccountResModel = hfbankInService.outOfAccount((OutOfAccountReqModel) reqModel);
		resp = JSON.toJSONString(outOfAccountResModel);
		log.info("标的出账回调通知返回:" + resp);
		return resp;
	}

	/**
	 * 网关充值通知
	 * @param request
	 * @param response
	 * @param req
     * @return
     */
	@RequestMapping(value = "/hfbank/business/gatewayRechargeNotice", method = RequestMethod.POST)
	public @ResponseBody
	String gatewayRechargeNotice(HttpServletRequest request,
						   HttpServletResponse response, GatewayRechargeNoticeReqModel req) {
		Map<String, String[]> paramMap = request.getParameterMap();
		log.info("网关充值通知request参数:["+paramMap+"]");
		// 待返回报文密文
		GatewayRechargeNoticeResModel res = new GatewayRechargeNoticeResModel();
		String resp;
		if (req == null) {
			res.setRecode(HfbankInConstant.RETURN_CODE_PARAMETER_EMPTY_ERROR);
			resp = JSON.toJSONString(res);
			return resp;
		}
		log.info("网关充值回调通知Body入参:" + JSONObject.fromObject(req));
		boolean checkSignData = HfbankInMsgUtil.checkSignData(req);
		if (!checkSignData) {
			res.setRecode(HfbankInConstant.RETURN_CODE_SIGN_DATA_ERROR);
			resp = JSON.toJSONString(res);
			log.info("网关充值回调通知验签结果{verify signature fail!}");
			return resp;
		}
		log.info("网关充值回调通知验签结果{verify signature succ!}");
		res = hfbankInService.gatewayRechargeNotice(req);
		resp = JSON.toJSONString(res);
		log.info("网关充值回调通知返回:" + resp);
		return resp;
	}


	/**
	 * 批量提现通知
	 * @param request
	 * @param response
	 * @param req
     * @return
     */
	@RequestMapping(value = "/hfbank/business/userBatchWithdrawNotice", method = RequestMethod.POST)
	public @ResponseBody
	String userBatchWithdrawNotice(HttpServletRequest request,
								 HttpServletResponse response,  BatchWithdrawExtNoticeReqModel req) {
		Map<String, String[]> paramMap = request.getParameterMap();
		log.info("批量提现通知request参数:["+paramMap+"]");
		// 待返回报文密文
		BatchWithdrawExtNoticeResModel res = new BatchWithdrawExtNoticeResModel();
		String resp;
		if (req == null) {
			res.setRecode(HfbankInConstant.RETURN_CODE_PARAMETER_EMPTY_ERROR);
			resp = JSON.toJSONString(res);
			return resp;
		}
		log.info("批量提现回调通知Body入参:" + JSONObject.fromObject(req));
		boolean checkSignData = HfbankInMsgUtil.checkSignData(req);
		if (!checkSignData) {
			res.setRecode(HfbankInConstant.RETURN_CODE_SIGN_DATA_ERROR);
			resp = JSON.toJSONString(res);
			log.info("批量提现回调通知验签结果:{verify signature fail!}");
			return resp;
		}
		log.info("批量提现回调通知验签结果:{verify signature succ!}");
		res = hfbankInService.userBatchWithdrawNotice(req);
		resp = JSON.toJSONString(res);
		log.info("批量提现回调通知返回:" + resp);
		return resp;
	}


	/**
	 * 借款人扣款还款通知请求信息
	 * @param request
	 * @param response
	 * @param req
     * @return
     */
	@RequestMapping(value = "/hfbank/business/borrowCutRepayNotice", method = RequestMethod.POST)
	public @ResponseBody
	String borrowCutRepayNotice(HttpServletRequest request,
								 HttpServletResponse response) {
		Map<String, String[]> paramMap = request.getParameterMap();
		log.info("借款人扣款还款通知request参数:["+paramMap+"]");
		BorrowCutRepayNoticeReqModel req = new BorrowCutRepayNoticeReqModel();
		String listString = request.getParameter("platcustList");
		List<BorrowCutRepayPlatcustReqModel> list = new ArrayList<>();
		JSONArray jsonArray = JSONArray.fromObject(listString);//把String转换为json 
		list = (List) JSONArray.toCollection(jsonArray, BorrowCutRepayPlatcustReqModel.class);
		req.setPlatcustList(list);
		req.setPlat_no(request.getParameter("plat_no"));
		req.setOrder_no(request.getParameter("order_no"));
		req.setAmt(request.getParameter("amt"));
		req.setBank_no(request.getParameter("bank_no"));
		req.setCode(request.getParameter("code"));
		req.setSigndata(request.getParameter("signdata"));
		
		// 待返回报文密文
		BorrowCutRepayNoticeResModel res = new BorrowCutRepayNoticeResModel();
		String resp;
		if (req == null) {
			res.setRecode(HfbankInConstant.RETURN_CODE_PARAMETER_EMPTY_ERROR);
			resp = JSON.toJSONString(res);
			return resp;
		}
		log.info("借款人扣款还款回调通知Body入参:" + JSONObject.fromObject(req));
		boolean checkSignData = HfbankInMsgUtil.checkSignData(req);
		if (!checkSignData) {
			res.setRecode(HfbankInConstant.RETURN_CODE_SIGN_DATA_ERROR);
			resp = JSON.toJSONString(res);
			log.info("借款人扣款还款回调通知验签结果:{verify signature fail!}");
			return resp;
		}
		log.info("借款人扣款还款回调通知验签结果:{verify signature succ!}");
		res = hfbankInService.borrowCutRepayNotice(req);
		resp = JSON.toJSONString(res);
		log.info("借款人扣款还款回调通知验签结果:" + resp);
		return resp;
	}

	/**
	 * 平台充值通知
	 * @param request
	 * @param response
	 * @param req
     * @return
     */
	@RequestMapping(value = "/hfbank/business/platTopUpNotice", method = RequestMethod.POST)
	public @ResponseBody
	String platTopUpNotice(HttpServletRequest request,
						   HttpServletResponse response,PlatTopUpNoticeReqModel req) {
		Map<String, String[]> paramMap = request.getParameterMap();
		log.info("平台充值通知request参数:["+paramMap+"]");
		// 待返回报文密文
		PlatTopUpNoticeResModel res = new PlatTopUpNoticeResModel();
		String resp;
		if (req == null) {
			res.setRecode(HfbankInConstant.RETURN_CODE_PARAMETER_EMPTY_ERROR);
			resp = JSON.toJSONString(res);
			return resp;
		}
		log.info("平台充值回调通知Body入参:" + JSONObject.fromObject(req));
		boolean checkSignData = HfbankInMsgUtil.checkSignData(req);
		if (!checkSignData) {
			res.setRecode(HfbankInConstant.RETURN_CODE_SIGN_DATA_ERROR);
			resp = JSON.toJSONString(res);
			log.info("平台充值回调通知验签结果{verify signature fail!}");
			return resp;
		}
		log.info("平台充值回调通知验签结果{verify signature succ!}");
		res = hfbankInService.platTopUpNotice(req);
		resp = JSON.toJSONString(res);
		log.info("平台充值回调通知返回:" + resp);
		return resp;
	}

	/**
	 * 平台提现通知
	 * @param request
	 * @param response
	 * @param req
     * @return
     */
	@RequestMapping(value = "/hfbank/business/platWithdrawNotice", method = RequestMethod.POST)
	public @ResponseBody
	String platTopUpNotice(HttpServletRequest request,
						   HttpServletResponse response, PlatWithdrawNoticeReqModel req) {
		Map<String, String[]> paramMap = request.getParameterMap();
		log.info("平台提现通知request参数:["+paramMap+"]");
		// 待返回报文密文
		PlatWithdrawNoticeResModel res = new PlatWithdrawNoticeResModel();
		String resp;
		if (req == null) {
			res.setRecode(HfbankInConstant.RETURN_CODE_PARAMETER_EMPTY_ERROR);
			resp = JSON.toJSONString(res);
			return resp;
		}
		log.info("平台提现回调通知Body入参:" + JSONObject.fromObject(req));
		boolean checkSignData = HfbankInMsgUtil.checkSignData(req);
		if (!checkSignData) {
			res.setRecode(HfbankInConstant.RETURN_CODE_SIGN_DATA_ERROR);
			resp = JSON.toJSONString(res);
			log.info("平台提现回调通知验签结果:{verify signature fail!}");
			return resp;
		}
		log.info("平台提现回调通知验签结果:{verify signature succ!}");
		res = hfbankInService.platWithdrawNotice(req);
		resp = JSON.toJSONString(res);
		log.info("平台提现回调通知返回:" + resp);
		return resp;
	}
	
	
	
	/**
	 * 清算成功通知接口
	 * @param request
	 * @param response
	 * @param req
     * @return
     */
	@RequestMapping(value = "/hfbank/business/liquidationNotice", method = RequestMethod.GET)
	public @ResponseBody
	String liquidationNotice(HttpServletRequest request,
						   HttpServletResponse response, LiquidationNoticeReqModel req) {
		Map<String, String[]> paramMap = request.getParameterMap();
		log.info("清算人工通知request参数:["+paramMap+"]");
		
		JSONObject notify_info	= JSONObject.fromObject(  request.getParameter("notify_info"));
		String liquidation_flag = (String) notify_info.get("liquidation_flag");
		String plat_no = (String) notify_info.get("plat_no");
		String notify_code = (String) request.getParameter("notify_code");
		String notify_name = (String) request.getParameter("notify_name");
		String notify_url = (String) request.getParameter("notify_url");
		
		req.setLiquidation_flag(liquidation_flag);
		req.setPlat_no(plat_no);
		req.setNotify_code(notify_code);
		req.setNotify_name(notify_name);
		req.setNotify_url(notify_url);
		
		// 待返回报文密文
		LiquidationNoticeResModel res = new LiquidationNoticeResModel();
		String resp;
		if (req == null) {
			res.setRecode(HfbankInConstant.RETURN_CODE_PARAMETER_EMPTY_ERROR);
			resp = JSON.toJSONString(res);
			return resp;
		}
		log.info("清算人工通知Body入参:" + JSONObject.fromObject(req));
//		boolean checkSignData = HfbankInMsgUtil.checkSignData(req); 
//		if (!checkSignData) {
//			res.setRecode(HfbankInConstant.RETURN_CODE_SIGN_DATA_ERROR);
//			resp = JSON.toJSONString(res);
//			log.info("清算人工成功通知{verify signature fail!}");
//			return resp;
//		}
//		log.info("清算人工成功通知{verify signature succ!}");
		res = hfbankInService.liquidationNotice(req);
		resp = JSON.toJSONString(res);
		log.info("清算成功通知返回请求  LiquidationNoticeResModel:" + resp);
		return resp;
	}
	
}
