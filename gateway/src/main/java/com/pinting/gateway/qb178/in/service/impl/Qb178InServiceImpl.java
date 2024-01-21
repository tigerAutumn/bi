package com.pinting.gateway.qb178.in.service.impl;

import com.pinting.core.hessian.service.HessianService;
import com.pinting.core.util.ConstantUtil;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.hessian.message.qb178.*;
import com.pinting.gateway.qb178.in.model.*;
import com.pinting.gateway.qb178.in.service.Qb178InService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
/**
 * 
 * @project gateway
 * @title Qb178InServiceImpl.java
 * @author Dragon & cat
 * @date 2017-7-29
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description
 */
@Service
public class Qb178InServiceImpl implements Qb178InService {
	
	private Logger log = LoggerFactory.getLogger(Qb178InServiceImpl.class);
	private String dateTimeStr = "yyyyMMddHHmmss";
	
    @Autowired
    @Qualifier("gatewayQb178Service")
    private HessianService gatewayQb178Service;

	@Override
	public ProductListResModel queryProductList(ProductListReqModel req) {

		ProductListResModel res = new ProductListResModel();
		

	    G2BReqMsg_Qb178_QueryProductList reqHessian = new G2BReqMsg_Qb178_QueryProductList();
        reqHessian.setProduct_code(req.getProduct_code());
        reqHessian.setCreate_time_begin(StringUtil.isBlank(req.getCreate_time_begin())?null:DateUtil.parse(req.getCreate_time_begin(),"yyyyMMddHHmmss"));
        reqHessian.setCreate_time_end(StringUtil.isBlank(req.getCreate_time_end())?null:DateUtil.parse(req.getCreate_time_end(),"yyyyMMddHHmmss"));
        reqHessian.setProduct_type(req.getProduct_type());
        reqHessian.setProduct_status(req.getProduct_status());
        reqHessian.setPage_num(req.getPage_num());
        reqHessian.setPage_size(req.getPage_size());
        G2BResMsg_Qb178_QueryProductList resHessian = (G2BResMsg_Qb178_QueryProductList) gatewayQb178Service.handleMsg(reqHessian);
		if (!ConstantUtil.BSRESCODE_SUCCESS.equals(resHessian.getResCode())) {
			log.error("查询产品列表异常："+resHessian.getResMsg());
			res.setError_no(resHessian.getResCode());
			res.setError_info(resHessian.getResMsg());
			return res;
		}
       	res.setCurrent_page(resHessian.getCurrent_page());
       	res.setTotal_num(resHessian.getTotal_num());
       	res.setData(resHessian.getData());
        return res;
	
	}

	@Override
	public OrderListResModel queryOrderList(OrderListReqModel req) {

		OrderListResModel res = new OrderListResModel();
		

	    G2BReqMsg_Qb178_QueryOrderList reqHessian = new G2BReqMsg_Qb178_QueryOrderList();
        reqHessian.setProduct_code(req.getProduct_code());
        reqHessian.setUser_account(req.getUser_account());
        reqHessian.setCreate_time_begin(StringUtil.isBlank(req.getCreate_time_begin())?null : DateUtil.parse(req.getCreate_time_begin(),"yyyyMMddHHmmss"));
        reqHessian.setCreate_time_end(StringUtil.isBlank(req.getCreate_time_end())?null :DateUtil.parse(req.getCreate_time_end(),"yyyyMMddHHmmss"));
        reqHessian.setPage_num(req.getPage_num());
        reqHessian.setPage_size(req.getPage_size());
        G2BResMsg_Qb178_QueryOrderList resHessian = (G2BResMsg_Qb178_QueryOrderList) gatewayQb178Service.handleMsg(reqHessian);
		if (!ConstantUtil.BSRESCODE_SUCCESS.equals(resHessian.getResCode())) {
			log.error("查询订单列表异常："+resHessian.getResMsg());
			res.setError_no(resHessian.getResCode());
			res.setError_info(resHessian.getResMsg());
			return res;
		}
       	res.setCurrent_page(resHessian.getCurrent_page());
       	res.setTotal_num(resHessian.getTotal_num());
       	res.setData(resHessian.getData());
        return res;

	}

	@Override
	public QueryUserDetailsResModel queryUserDetails(QueryUserDetailsReqModel req) {
		QueryUserDetailsResModel res = new QueryUserDetailsResModel();
		G2BReqMsg_Qb178_QueryUserDetails reqHessian = new G2BReqMsg_Qb178_QueryUserDetails();
		reqHessian.setUser_account(req.getUser_account());
		reqHessian.setCreate_time_begin(StringUtil.isBlank(req.getCreate_time_begin())?null : DateUtil.parse(req.getCreate_time_begin(),"yyyyMMddHHmmss"));
		reqHessian.setCreate_time_end(StringUtil.isBlank(req.getCreate_time_end())?null :DateUtil.parse(req.getCreate_time_end(),"yyyyMMddHHmmss"));
		reqHessian.setPage_num(req.getPage_num());
		reqHessian.setPage_size(req.getPage_size());
		G2BResMsg_Qb178_QueryUserDetails resHessian = (G2BResMsg_Qb178_QueryUserDetails) gatewayQb178Service.handleMsg(reqHessian);
		if (!ConstantUtil.BSRESCODE_SUCCESS.equals(resHessian.getResCode())) {
			log.error("查询会员详情异常："+resHessian.getResMsg());
			res.setError_no(resHessian.getResCode());
			res.setError_info(resHessian.getResMsg());
			return res;
		}
		res.setCurrent_page(resHessian.getCurrent_page());
		res.setTotal_num(resHessian.getTotal_num());
		res.setData(resHessian.getData());
		return res;
	}

	@Override
	public PositionBalanceResModel queryPositionBalance(
			PositionBalanceReqModel reqModel) {
		PositionBalanceResModel resModel = new PositionBalanceResModel();
		
		G2BReqMsg_Qb178_QueryPositionBalance req = new G2BReqMsg_Qb178_QueryPositionBalance();
		req.setProduct_code(Integer.parseInt(reqModel.getProduct_code()));
		req.setCreate_time_begin(
				StringUtils.isBlank(reqModel.getCreate_time_begin()) ? null :DateUtil.parse(reqModel.getCreate_time_begin(),dateTimeStr));
		req.setCreate_time_end(
				StringUtils.isBlank(reqModel.getCreate_time_end()) ? null :DateUtil.parse(reqModel.getCreate_time_end(),dateTimeStr));
		req.setPage_num(reqModel.getPage_num());
        req.setPage_size(reqModel.getPage_size());
        
        G2BResMsg_Qb178_QueryPositionBalance res = (G2BResMsg_Qb178_QueryPositionBalance)gatewayQb178Service.handleMsg(req);
        if (!ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
			log.error("查询会员持仓余额："+res.getResMsg());
			resModel.setError_no(res.getResCode());
			resModel.setError_info(res.getResMsg());
			return resModel;
		}
        resModel.setCurrent_page(res.getCurrent_page());
        resModel.setTotal_num(res.getTotal_num());
        resModel.setData(res.getData());
        return resModel;
	}
	
	@Override
	public QueryBalanceResModel queryBalance(QueryBalanceReqModel reqModel) {
		QueryBalanceResModel resModel = new QueryBalanceResModel();
		G2BReqMsg_Qb178_QueryBalance req = new G2BReqMsg_Qb178_QueryBalance();
		
		String accountArrStr = reqModel.getUser_account_ary().replaceAll("，", ",");
		String[] accountArr = accountArrStr.split(",");
		if(accountArr.length > 0){
			List<String> accountList = Arrays.asList(accountArr);
			req.setUser_account_ary_list(accountList);
		}
		req.setCreate_time_begin(
				StringUtils.isBlank(reqModel.getCreate_time_begin()) ? null :DateUtil.parse(reqModel.getCreate_time_begin(),dateTimeStr));
		req.setCreate_time_end(
				StringUtils.isBlank(reqModel.getCreate_time_end()) ? null :DateUtil.parse(reqModel.getCreate_time_end(),dateTimeStr));
		req.setPage_num(reqModel.getPage_num());
        req.setPage_size(reqModel.getPage_size());
		
        G2BResMsg_Qb178_QueryBalance res = (G2BResMsg_Qb178_QueryBalance)gatewayQb178Service.handleMsg(req);
        if (!ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
			log.error("查询会员资金余额："+res.getResMsg());
			resModel.setError_no(res.getResCode());
			resModel.setError_info(res.getResMsg());
			return resModel;
		}
        resModel.setCurrent_page(res.getCurrent_page());
        resModel.setTotal_num(res.getTotal_num());
        resModel.setData(res.getData());
        return resModel;
	}

	@Override
	public QueryBalanceJnlResModel queryBalanceJnl(
			QueryBalanceJnlReqModel reqModel) {
		QueryBalanceJnlResModel resModel = new QueryBalanceJnlResModel();
		G2BReqMsg_Qb178_QueryBalanceJnl req = new G2BReqMsg_Qb178_QueryBalanceJnl();
		req.setUser_account(reqModel.getUser_account());
		req.setCreate_time_begin(
				StringUtils.isBlank(reqModel.getCreate_time_begin()) ? null :DateUtil.parse(reqModel.getCreate_time_begin(),dateTimeStr));
		req.setCreate_time_end(
				StringUtils.isBlank(reqModel.getCreate_time_end()) ? null :DateUtil.parse(reqModel.getCreate_time_end(),dateTimeStr));
		req.setPage_num(reqModel.getPage_num());
        req.setPage_size(reqModel.getPage_size());
		
		G2BResMsg_Qb178_QueryBalanceJnl res = (G2BResMsg_Qb178_QueryBalanceJnl)gatewayQb178Service.handleMsg(req);
		if (!ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
			log.error("查询会员资金流水："+res.getResMsg());
			resModel.setError_no(res.getResCode());
			resModel.setError_info(res.getResMsg());
			return resModel;
		}
        resModel.setCurrent_page(res.getCurrent_page());
        resModel.setTotal_num(res.getTotal_num());
        resModel.setData(res.getData());
        return resModel;
	}

	@Override
	public QueryRepayPlanResModel queryRepayPlan(QueryRepayPlanReqModel reqModel) {
		QueryRepayPlanResModel resModel = new QueryRepayPlanResModel();
		G2BReqMsg_Qb178_QueryRepayPlan req = new G2BReqMsg_Qb178_QueryRepayPlan();
		
		req.setUser_account(reqModel.getUser_account());
		req.setProduct_code(StringUtils.isBlank(reqModel.getProduct_code()) ? null : Integer.parseInt(reqModel.getProduct_code()));
		req.setCreate_time_begin(
				StringUtils.isBlank(reqModel.getCreate_time_begin()) ? null :DateUtil.parse(reqModel.getCreate_time_begin(),dateTimeStr));
		req.setCreate_time_end(
				StringUtils.isBlank(reqModel.getCreate_time_end()) ? null :DateUtil.parse(reqModel.getCreate_time_end(),dateTimeStr));
		req.setPage_num(reqModel.getPage_num());
        req.setPage_size(reqModel.getPage_size());
        
        G2BResMsg_Qb178_QueryRepayPlan res = (G2BResMsg_Qb178_QueryRepayPlan)gatewayQb178Service.handleMsg(req);
		if (!ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
			log.error("查询还款计划："+res.getResMsg());
			resModel.setError_no(res.getResCode());
			resModel.setError_info(res.getResMsg());
			return resModel;
		}
        resModel.setCurrent_page(res.getCurrent_page());
        resModel.setTotal_num(res.getTotal_num());
        resModel.setData(res.getData());
        return resModel;
	}

}
