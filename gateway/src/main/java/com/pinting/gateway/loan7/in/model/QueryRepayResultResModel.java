package com.pinting.gateway.loan7.in.model;

import java.util.Date;

/**
 * 自主放款-还款结果查询
 * @author bianyatian
 * @2016-11-28 下午5:09:28
 */
public class QueryRepayResultResModel extends BaseResModel {

	private String orderNo; //借款单号
	
	private String bgwOrderNo; //支付单号
	
	private String channel; //支付渠道
	
	private String resultCode; //支付结果码
	
	private String resultMsg; //支付结果信息
	
	private Date finishTime; //还款成功时间

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getBgwOrderNo() {
		return bgwOrderNo;
	}

	public void setBgwOrderNo(String bgwOrderNo) {
		this.bgwOrderNo = bgwOrderNo;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMsg() {
		return resultMsg;
	}

	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}
	
	
}
