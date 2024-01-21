package com.pinting.gateway.qb178.out.service.impl;

import com.pinting.gateway.qb178.out.model.*;
import com.pinting.gateway.qb178.out.service.SendQb178Service;
import com.pinting.gateway.qb178.out.util.HttpClientUtil;
import com.pinting.gateway.qb178.out.util.Qb178OutMsgUtil;
import com.pinting.gateway.util.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 发向钱报APP的通知
 * @author bianyatian
 * @2017-7-29 上午11:13:42
 */
@Service
public class SendQb178ServiceImpl implements SendQb178Service {

	
	
	@Override
	public UpdateRepayPlanResModel updateRepayPlan(UpdateRepayPlanReqModel req) {
		Map<String, String> params = BeanUtils.classToHashMapStr(req);
		UpdateRepayPlanResModel res = Qb178OutMsgUtil.requestQb178(HttpClientUtil.URL+"/nsatc/v1/settle_plan_update", params,
				UpdateRepayPlanResModel.class, HttpClientUtil.METHOD_POST, "更新还款计划状态");
		return res;
	}

	@Override
	public OrderNoticeResModel orderNotice(OrderNoticeReqModel req) {
		Map<String, String> params = BeanUtils.classToHashMapStr(req);
		OrderNoticeResModel res = Qb178OutMsgUtil.requestQb178(HttpClientUtil.URL+"/nsatc/v1/delegation_notify", params,
				OrderNoticeResModel.class, HttpClientUtil.METHOD_POST, "订单通知");
		return res;
	}

	@Override
	public UpdateProductInfoResModel updateProductInfo(
			UpdateProductInfoReqModel req) {
		Map<String, String> params = BeanUtils.classToHashMapStr(req);
		UpdateProductInfoResModel res = Qb178OutMsgUtil.requestQb178(HttpClientUtil.URL+"/nsatc/v1/product_status_update", params,
				UpdateProductInfoResModel.class, HttpClientUtil.METHOD_POST, "更新产品状态");
		return res;
	}

	@Override
	public UpdateUserInfoResModel updateUserInfo(UpdateUserInfoReqModel req) {
		Map<String, String> params = BeanUtils.classToHashMapStr(req);
		UpdateUserInfoResModel res = Qb178OutMsgUtil.requestQb178(HttpClientUtil.URL +"/nsatc/v1/user_info_update", params,
				UpdateUserInfoResModel.class, HttpClientUtil.METHOD_POST, "更新用户状态");
		return res;
	}

}
