package com.pinting.gateway.qb178.in.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.enums.PTMessageEnum;
import com.pinting.gateway.qb178.in.model.OrderListReqModel;
import com.pinting.gateway.qb178.in.model.OrderListResModel;
import com.pinting.gateway.qb178.in.model.PositionBalanceReqModel;
import com.pinting.gateway.qb178.in.model.PositionBalanceResModel;
import com.pinting.gateway.qb178.in.model.ProductListReqModel;
import com.pinting.gateway.qb178.in.model.ProductListResModel;
import com.pinting.gateway.qb178.in.model.QueryBalanceJnlReqModel;
import com.pinting.gateway.qb178.in.model.QueryBalanceJnlResModel;
import com.pinting.gateway.qb178.in.model.QueryBalanceReqModel;
import com.pinting.gateway.qb178.in.model.QueryBalanceResModel;
import com.pinting.gateway.qb178.in.model.QueryRepayPlanReqModel;
import com.pinting.gateway.qb178.in.model.QueryRepayPlanResModel;
import com.pinting.gateway.qb178.in.model.QueryUserDetailsReqModel;
import com.pinting.gateway.qb178.in.model.QueryUserDetailsResModel;
import com.pinting.gateway.qb178.in.service.Qb178InService;
import com.pinting.gateway.qb178.in.util.Qb178InMsgUtil;
import com.pinting.gateway.qb178.in.util.StandardClientUtil;
import com.pinting.gateway.util.JsonLibUtil;

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
public class Qb178ServiceDispatchController {

	private Logger log = LoggerFactory
			.getLogger(Qb178ServiceDispatchController.class);
	

	@Autowired
	private  Qb178InService qb178InService;
	
	/**
	 * 查询产品列表
	 * @param request
	 * @param response
	 * @param req
     * @return
     */
	@RequestMapping(value = "/qb178/business/productList", method = RequestMethod.POST)
	public @ResponseBody
	String productList(HttpServletRequest request,
			HttpServletResponse response, ProductListReqModel reqModel) {
		Map<String, String[]> paramMap = request.getParameterMap();
		log.info("查询产品列表request参数:["+paramMap+"]");
		// 待返回报文密文
		ProductListResModel productListResModel = new ProductListResModel();
		String resp = null;
		if (reqModel == null ) {
			productListResModel.setError_no(PTMessageEnum.QB178_PARAMETER_EMPTY_ERROR.getCode());
			productListResModel.setError_info(PTMessageEnum.QB178_PARAMETER_EMPTY_ERROR.getMessage());
			//签名
			String signString = JsonLibUtil.bean2Json(productListResModel);
			Map<String, String> map = StandardClientUtil.parseResponse(signString);
			String sign = Qb178InMsgUtil.getSignDataRes(map);
			productListResModel.setCert_sign(sign);
			resp = JsonLibUtil.bean2Json(productListResModel);
			resp = Qb178InMsgUtil.sortResponse(resp);
			return resp;
		}
		log.info("查询产品列表Body参数:" + JSONObject.fromObject(reqModel));
		boolean checkSignData = Qb178InMsgUtil.checkSignData(reqModel);
		if (!checkSignData) {
			productListResModel.setError_no(PTMessageEnum.QB178_SIGN_DATA_ERROR.getCode());
			productListResModel.setError_info(PTMessageEnum.QB178_SIGN_DATA_ERROR.getMessage());
			//签名
			String signString = JsonLibUtil.bean2Json(productListResModel);
			Map<String, String> map = StandardClientUtil.parseResponse(signString);
			String sign = Qb178InMsgUtil.getSignDataRes(map);
			productListResModel.setCert_sign(sign);
			resp = JsonLibUtil.bean2Json(productListResModel);
			resp = Qb178InMsgUtil.sortResponse(resp);
		    log.info("查询产品列表验签结果{verify signature fail!}");
			return resp;
		}
		log.info("查询产品列表验签结果{verify signature succ!}");
		
		productListResModel = qb178InService.queryProductList((ProductListReqModel) reqModel);
//		productListResModel.setCurrent_page(productListResModel.getCurrent_page() == null ? 0 : productListResModel.getCurrent_page());
//		productListResModel.setTotal_num(productListResModel.getTotal_num() == null ? 0 : productListResModel.getTotal_num());
//		productListResModel.setCert_sign(Qb178InMsgUtil.getSignData(productListResModel));
		//签名
		String signString = JsonLibUtil.bean2Json(productListResModel);
		Map<String, String> map = StandardClientUtil.parseResponse(signString);
		String sign = Qb178InMsgUtil.getSignDataRes(map);
		productListResModel.setCert_sign(sign);
		resp = JsonLibUtil.bean2Json(productListResModel);
		resp = Qb178InMsgUtil.sortResponse(resp);
		log.info("查询产品列表返回:" + resp);
		return resp;
	}
	
	
	
	/**
	 * 查询订单列表
	 * @param request
	 * @param response
	 * @param req
     * @return
     */
	@RequestMapping(value = "/qb178/business/orderList", method = RequestMethod.POST)
	public @ResponseBody
	String orderList(HttpServletRequest request,
			HttpServletResponse response, OrderListReqModel reqModel) {
		Map<String, String[]> paramMap = request.getParameterMap();
		log.info("查询订单列表request参数:["+paramMap+"]");
		// 待返回报文密文
		OrderListResModel orderListResModel = new OrderListResModel();
		String resp = null;
		if (reqModel == null ) {
			orderListResModel.setError_no(PTMessageEnum.QB178_PARAMETER_EMPTY_ERROR.getCode());
			orderListResModel.setError_info(PTMessageEnum.QB178_PARAMETER_EMPTY_ERROR.getMessage());
			resp = JSON.toJSONString(orderListResModel);
			resp = Qb178InMsgUtil.sortResponse(resp);
			return resp;
		}
		log.info("查询订单列表Body参数:" + JSONObject.fromObject(reqModel));
		boolean checkSignData = Qb178InMsgUtil.checkSignData(reqModel);
		if (!checkSignData) {
			orderListResModel.setError_no(PTMessageEnum.QB178_SIGN_DATA_ERROR.getCode());
			orderListResModel.setError_info(PTMessageEnum.QB178_SIGN_DATA_ERROR.getMessage());
			resp = JSON.toJSONString(orderListResModel);
			resp = Qb178InMsgUtil.sortResponse(resp);
		    log.info("查询订单列表验签结果{verify signature fail!}");
			return resp;
		}
		log.info("查询订单列表验签结果{verify signature succ!}");
		orderListResModel = qb178InService.queryOrderList((OrderListReqModel) reqModel);
		//签名
		String signString = JsonLibUtil.bean2Json(orderListResModel);
		Map<String, String> map = StandardClientUtil.parseResponse(signString);
		String sign = Qb178InMsgUtil.getSignDataRes(map);
		orderListResModel.setCert_sign(sign);
		resp = JsonLibUtil.bean2Json(orderListResModel);
		resp = Qb178InMsgUtil.sortResponse(resp);
		log.info("查询订单列表返回:" + resp);
		return resp;
	}

	/**
	 * 查询会员详情
	 * @param request
	 * @param response
	 * @param reqModel
	 * @return
	 */
	@RequestMapping(value = "/qb178/business/queryUserDetails", method = RequestMethod.POST)
	public @ResponseBody
	String orderList(HttpServletRequest request,
					 HttpServletResponse response, QueryUserDetailsReqModel reqModel) {
		Map<String, String[]> paramMap = request.getParameterMap();
		log.info("查询会员详情request参数:["+paramMap+"]");
		// 待返回报文密文
		QueryUserDetailsResModel queryUserDetailsResModel = new QueryUserDetailsResModel();
		String resp = null;
		if (reqModel == null ) {
			queryUserDetailsResModel.setError_no(PTMessageEnum.QB178_SIGN_DATA_ERROR.getCode());
			queryUserDetailsResModel.setError_info(PTMessageEnum.QB178_SIGN_DATA_ERROR.getMessage());
			//签名
			String signString = JsonLibUtil.bean2Json(queryUserDetailsResModel);
			Map<String, String> map = StandardClientUtil.parseResponse(signString);
			String sign = Qb178InMsgUtil.getSignDataRes(map);
			queryUserDetailsResModel.setCert_sign(sign);
			resp = JsonLibUtil.bean2Json(queryUserDetailsResModel);
			resp = Qb178InMsgUtil.sortResponse(resp);
			return resp;
		}
		log.info("查询会员详情Body参数:" + JSONObject.fromObject(reqModel));
		boolean checkSignData = Qb178InMsgUtil.checkSignData(reqModel);
		if (!checkSignData) {
			queryUserDetailsResModel.setError_no(PTMessageEnum.QB178_SIGN_DATA_ERROR.getCode());
			queryUserDetailsResModel.setError_info(PTMessageEnum.QB178_SIGN_DATA_ERROR.getMessage());
			//签名
			String signString = JsonLibUtil.bean2Json(queryUserDetailsResModel);
			Map<String, String> map = StandardClientUtil.parseResponse(signString);
			String sign = Qb178InMsgUtil.getSignDataRes(map);
			queryUserDetailsResModel.setCert_sign(sign);
			resp = JsonLibUtil.bean2Json(queryUserDetailsResModel);
			resp = Qb178InMsgUtil.sortResponse(resp);
			log.info("查询会员详情验签结果{verify signature fail!}");
			return resp;
		}
		log.info("查询会员详情验签结果{verify signature succ!}");
		queryUserDetailsResModel = qb178InService.queryUserDetails((QueryUserDetailsReqModel) reqModel);
		//签名
		String signString = JsonLibUtil.bean2Json(queryUserDetailsResModel);
		Map<String, String> map = StandardClientUtil.parseResponse(signString);
		String sign = Qb178InMsgUtil.getSignDataRes(map);
		queryUserDetailsResModel.setCert_sign(sign);
		resp = JsonLibUtil.bean2Json(queryUserDetailsResModel);
		resp = Qb178InMsgUtil.sortResponse(resp);
		log.info("查询会员详情返回:" + resp);
		return resp;
	}

	/**
	 * 查询会员持仓余额
	 * @param request
	 * @param response
	 * @param req
     * @return
     */
	@RequestMapping(value = "/qb178/business/positionBalance", method = RequestMethod.POST)
	public @ResponseBody
	String queryPositionBalance(HttpServletRequest request,
			HttpServletResponse response, PositionBalanceReqModel reqModel) {
		Map<String, String[]> paramMap = request.getParameterMap();
		log.info("查询会员持仓余额request参数:["+paramMap+"]");
		// 待返回报文密文
		PositionBalanceResModel positionBalanceResModel = new PositionBalanceResModel();
		String resp = null;
		if (reqModel == null ) {
			positionBalanceResModel.setError_no(PTMessageEnum.QB178_PARAMETER_EMPTY_ERROR.getCode());
			positionBalanceResModel.setError_info(PTMessageEnum.QB178_PARAMETER_EMPTY_ERROR.getMessage());
			//签名
			String signString = JsonLibUtil.bean2Json(positionBalanceResModel);
			Map<String, String> map = StandardClientUtil.parseResponse(signString);
			String sign = Qb178InMsgUtil.getSignDataRes(map);
			positionBalanceResModel.setCert_sign(sign);
			resp = JsonLibUtil.bean2Json(positionBalanceResModel);
			resp = Qb178InMsgUtil.sortResponse(resp);
			return resp;
		}
		log.info("查询会员持仓余额Body参数:" + JSONObject.fromObject(reqModel));
		boolean checkSignData = Qb178InMsgUtil.checkSignData(reqModel);
		if (!checkSignData) {
			positionBalanceResModel.setError_no(PTMessageEnum.QB178_SIGN_DATA_ERROR.getCode());
			positionBalanceResModel.setError_info(PTMessageEnum.QB178_SIGN_DATA_ERROR.getMessage());
			//签名
			String signString = JsonLibUtil.bean2Json(positionBalanceResModel);
			Map<String, String> map = StandardClientUtil.parseResponse(signString);
			String sign = Qb178InMsgUtil.getSignDataRes(map);
			positionBalanceResModel.setCert_sign(sign);
			resp = JsonLibUtil.bean2Json(positionBalanceResModel);
			resp = Qb178InMsgUtil.sortResponse(resp);
		    log.info("查询会员持仓余额验签结果{verify signature fail!}");
			return resp;
		}
		log.info("查询会员持仓余额验签结果{verify signature succ!}");
		
		if(StringUtil.isBlank(reqModel.getProduct_code())){
			positionBalanceResModel.setError_no(PTMessageEnum.QB178_QUERY_POSITION_NO_PRODUCT_CODE.getCode());
			positionBalanceResModel.setError_info(PTMessageEnum.QB178_QUERY_POSITION_NO_PRODUCT_CODE.getMessage());
			//签名
			String signString = JsonLibUtil.bean2Json(positionBalanceResModel);
			Map<String, String> map = StandardClientUtil.parseResponse(signString);
			String sign = Qb178InMsgUtil.getSignDataRes(map);
			positionBalanceResModel.setCert_sign(sign);
			resp = JsonLibUtil.bean2Json(positionBalanceResModel);
			resp = Qb178InMsgUtil.sortResponse(resp);
			log.info(PTMessageEnum.QB178_QUERY_POSITION_NO_PRODUCT_CODE.getMessage());
			return resp;
		}
		positionBalanceResModel = qb178InService.queryPositionBalance((PositionBalanceReqModel) reqModel);
		resp = JSON.toJSONString(positionBalanceResModel);
		//签名
		String signString = JsonLibUtil.bean2Json(positionBalanceResModel);
		Map<String, String> map = StandardClientUtil.parseResponse(signString);
		String sign = Qb178InMsgUtil.getSignDataRes(map);
		positionBalanceResModel.setCert_sign(sign);
		resp = JsonLibUtil.bean2Json(positionBalanceResModel);
		log.info("查询会员持仓返回:" + resp);
		resp = Qb178InMsgUtil.sortResponse(resp);
		log.info("查询会员持仓返回:" + resp);
		return resp;
	}
	
	/**
	 * 查询会员资金余额
	 * @param request
	 * @param response
	 * @param req
     * @return
     */
	@RequestMapping(value = "/qb178/business/queryBalance", method = RequestMethod.POST)
	public @ResponseBody
	String queryBalance(HttpServletRequest request,
			HttpServletResponse response, QueryBalanceReqModel reqModel) {
		Map<String, String[]> paramMap = request.getParameterMap();
		log.info("查询会员资金余额request参数:["+paramMap+"]");
		// 待返回报文密文
		QueryBalanceResModel queryBalanceResModel = new QueryBalanceResModel();
		String resp = null;
		if (reqModel == null ) {
			queryBalanceResModel.setError_no(PTMessageEnum.QB178_PARAMETER_EMPTY_ERROR.getCode());
			queryBalanceResModel.setError_info(PTMessageEnum.QB178_PARAMETER_EMPTY_ERROR.getMessage());

			//签名
			String signString = JsonLibUtil.bean2Json(queryBalanceResModel);
			Map<String, String> map = StandardClientUtil.parseResponse(signString);
			String sign = Qb178InMsgUtil.getSignDataRes(map);
			queryBalanceResModel.setCert_sign(sign);
			resp = JsonLibUtil.bean2Json(queryBalanceResModel);
			resp = Qb178InMsgUtil.sortResponse(resp);
			return resp;
		}
		log.info("查询会员资金余额Body参数:" + JSONObject.fromObject(reqModel));
		boolean checkSignData = Qb178InMsgUtil.checkSignData(reqModel);
		if (!checkSignData) {
			queryBalanceResModel.setError_no(PTMessageEnum.QB178_SIGN_DATA_ERROR.getCode());
			queryBalanceResModel.setError_info(PTMessageEnum.QB178_SIGN_DATA_ERROR.getMessage());

			//签名
			String signString = JsonLibUtil.bean2Json(queryBalanceResModel);
			Map<String, String> map = StandardClientUtil.parseResponse(signString);
			String sign = Qb178InMsgUtil.getSignDataRes(map);
			queryBalanceResModel.setCert_sign(sign);
			resp = JsonLibUtil.bean2Json(queryBalanceResModel);
			resp = Qb178InMsgUtil.sortResponse(resp);
		    log.info("查询会员资金余额验签结果{verify signature fail!}");
			return resp;
		}
		log.info("查询会员资金余额验签结果{verify signature succ!}");
		queryBalanceResModel = qb178InService.queryBalance((QueryBalanceReqModel) reqModel);

		//签名
		String signString = JsonLibUtil.bean2Json(queryBalanceResModel);
		Map<String, String> map = StandardClientUtil.parseResponse(signString);
		String sign = Qb178InMsgUtil.getSignDataRes(map);
		queryBalanceResModel.setCert_sign(sign);
		resp = JsonLibUtil.bean2Json(queryBalanceResModel);
		resp = Qb178InMsgUtil.sortResponse(resp);
		log.info("查询会员资金余额返回:" + resp);
		return resp;
	}
	
	/**
	 * 查询会员资金流水
	 * @param request
	 * @param response
	 * @param req
     * @return
     */
	@RequestMapping(value = "/qb178/business/queryBalanceJnl", method = RequestMethod.POST)
	public @ResponseBody
	String queryBalanceJnl(HttpServletRequest request,
			HttpServletResponse response, QueryBalanceJnlReqModel reqModel) {
		Map<String, String[]> paramMap = request.getParameterMap();
		log.info("查询会员资金流水request参数:["+paramMap+"]");
		// 待返回报文密文
		QueryBalanceJnlResModel queryBalanceJnlResModel = new QueryBalanceJnlResModel();
		String resp = null;
		if (reqModel == null ) {
			queryBalanceJnlResModel.setError_no(PTMessageEnum.QB178_PARAMETER_EMPTY_ERROR.getCode());
			queryBalanceJnlResModel.setError_info(PTMessageEnum.QB178_PARAMETER_EMPTY_ERROR.getMessage());
			//签名
			String signString = JsonLibUtil.bean2Json(queryBalanceJnlResModel);
			Map<String, String> map = StandardClientUtil.parseResponse(signString);
			String sign = Qb178InMsgUtil.getSignDataRes(map);
			queryBalanceJnlResModel.setCert_sign(sign);
			resp = JsonLibUtil.bean2Json(queryBalanceJnlResModel);
			resp = Qb178InMsgUtil.sortResponse(resp);
			return resp;
		}
		log.info("查询会员资金流水Body参数:" + JSONObject.fromObject(reqModel));
		boolean checkSignData = Qb178InMsgUtil.checkSignData(reqModel);
		if (!checkSignData) {
			queryBalanceJnlResModel.setError_no(PTMessageEnum.QB178_SIGN_DATA_ERROR.getCode());
			queryBalanceJnlResModel.setError_info(PTMessageEnum.QB178_SIGN_DATA_ERROR.getMessage());
			//签名
			String signString = JsonLibUtil.bean2Json(queryBalanceJnlResModel);
			Map<String, String> map = StandardClientUtil.parseResponse(signString);
			String sign = Qb178InMsgUtil.getSignDataRes(map);
			queryBalanceJnlResModel.setCert_sign(sign);
			resp = JsonLibUtil.bean2Json(queryBalanceJnlResModel);
			resp = Qb178InMsgUtil.sortResponse(resp);
		    log.info("查询会员资金流水验签结果{verify signature fail!}");
			return resp;
		}
		log.info("查询会员资金流水验签结果{verify signature succ!}");
		queryBalanceJnlResModel = qb178InService.queryBalanceJnl((QueryBalanceJnlReqModel) reqModel);


		//签名
		String signString = JsonLibUtil.bean2Json(queryBalanceJnlResModel);
		Map<String, String> map = StandardClientUtil.parseResponse(signString);
		String sign = Qb178InMsgUtil.getSignDataRes(map);
		queryBalanceJnlResModel.setCert_sign(sign);
		resp = JsonLibUtil.bean2Json(queryBalanceJnlResModel);
		resp = Qb178InMsgUtil.sortResponse(resp);
		log.info("查询会员资金流水返回:" + resp);
		return resp;
	}
	
	/**
	 * 查询还款计划
	 * @param request
	 * @param response
	 * @param req
     * @return
     */
	@RequestMapping(value = "/qb178/business/queryRepayPlan", method = RequestMethod.POST)
	public @ResponseBody
	String queryRepayPlan(HttpServletRequest request,
			HttpServletResponse response, QueryRepayPlanReqModel reqModel) {
		Map<String, String[]> paramMap = request.getParameterMap();
		log.info("查询还款计划request参数:["+paramMap+"]");
		// 待返回报文密文
		QueryRepayPlanResModel queryRepayPlanResModel = new QueryRepayPlanResModel();
		String resp = null;
		if (reqModel == null ) {
			queryRepayPlanResModel.setError_no(PTMessageEnum.QB178_PARAMETER_EMPTY_ERROR.getCode());
			queryRepayPlanResModel.setError_info(PTMessageEnum.QB178_PARAMETER_EMPTY_ERROR.getMessage());

			//签名
			String signString = JsonLibUtil.bean2Json(queryRepayPlanResModel);
			Map<String, String> map = StandardClientUtil.parseResponse(signString);
			String sign = Qb178InMsgUtil.getSignDataRes(map);
			queryRepayPlanResModel.setCert_sign(sign);
			resp = JsonLibUtil.bean2Json(queryRepayPlanResModel);
			resp = Qb178InMsgUtil.sortResponse(resp);
			return resp;
		}
		log.info("查询还款计划Body参数:" + JSONObject.fromObject(reqModel));
		boolean checkSignData = Qb178InMsgUtil.checkSignData(reqModel);
		if (!checkSignData) {
			queryRepayPlanResModel.setError_no(PTMessageEnum.QB178_SIGN_DATA_ERROR.getCode());
			queryRepayPlanResModel.setError_info(PTMessageEnum.QB178_SIGN_DATA_ERROR.getMessage());

			//签名
			String signString = JsonLibUtil.bean2Json(queryRepayPlanResModel);
			Map<String, String> map = StandardClientUtil.parseResponse(signString);
			String sign = Qb178InMsgUtil.getSignDataRes(map);
			queryRepayPlanResModel.setCert_sign(sign);
			resp = JsonLibUtil.bean2Json(queryRepayPlanResModel);
			resp = Qb178InMsgUtil.sortResponse(resp);
		    log.info("查询还款计划验签结果{verify signature fail!}");
			return resp;
		}
		log.info("查询还款计划验签结果{verify signature succ!}");
		queryRepayPlanResModel = qb178InService.queryRepayPlan((QueryRepayPlanReqModel) reqModel);

		//签名
		String signString = JsonLibUtil.bean2Json(queryRepayPlanResModel);
		Map<String, String> map = StandardClientUtil.parseResponse(signString);
		String sign = Qb178InMsgUtil.getSignDataRes(map);
		queryRepayPlanResModel.setCert_sign(sign);
		resp = JsonLibUtil.bean2Json(queryRepayPlanResModel);
		resp = Qb178InMsgUtil.sortResponse(resp);
		log.info("查询还款计划返回:" + resp);
		return resp;
	}
}
