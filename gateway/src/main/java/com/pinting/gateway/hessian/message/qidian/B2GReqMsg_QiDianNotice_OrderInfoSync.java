package com.pinting.gateway.hessian.message.qidian;

import java.util.List;

import com.pinting.core.hessian.msg.ReqMsg;
import com.pinting.gateway.hessian.message.qidian.model.OrderInfos;

public class B2GReqMsg_QiDianNotice_OrderInfoSync extends ReqMsg {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7510417457781563353L;
	/*1.理财购买 2.到期回款*/
	private		Integer		type;
	/*订单信息列表*/
	private 	List<OrderInfos>   orderInfos;
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public List<OrderInfos> getOrderInfos() {
		return orderInfos;
	}
	public void setOrderInfos(List<OrderInfos> orderInfos) {
		this.orderInfos = orderInfos;
	}
	
	
}
