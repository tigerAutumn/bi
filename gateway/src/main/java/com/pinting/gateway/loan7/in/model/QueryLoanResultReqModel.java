package com.pinting.gateway.loan7.in.model;

import java.util.Date;

/**
 * 自主放款-放款结果查询
 * @author Dragon & cat
 * @date 2016-11-28
 */
public class QueryLoanResultReqModel extends BaseReqModel {
	/*客户端标识*/
	private			String 		clientKey;
	/*放款单号*/
	private			String		orderNo;
	/*提交放款日期*/
	private			Date		applyDate;
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public Date getApplyDate() {
		return applyDate;
	}
	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}
	public String getClientKey() {
		return clientKey;
	}
	public void setClientKey(String clientKey) {
		this.clientKey = clientKey;
	}
	
	
}
