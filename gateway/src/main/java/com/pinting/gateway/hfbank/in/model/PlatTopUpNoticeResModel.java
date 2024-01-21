package com.pinting.gateway.hfbank.in.model;

/**
 * 平台充值回调通知响应信息
 * @author SHENGP
 * @date  2017年4月24日 下午4:53:01
 */
public class PlatTopUpNoticeResModel extends BaseResModel {
	/* 订单号 */
	private String order_no;

	public String getOrder_no() {
		return order_no;
	}

	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}
}
