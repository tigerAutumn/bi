package com.pinting.gateway.loan7.in.model;

import java.util.Date;

/**
 * 自主放款-还款结果查询
 * @author bianyatian
 * @2016-11-28 下午5:09:05
 */
public class QueryRepayResultReqModel extends BaseReqModel {
	/*客户端标识*/
	private			String 		clientKey;
	
	private String orderNo; //还款单号
	
	private Date applyDate; //提交放款日期

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
