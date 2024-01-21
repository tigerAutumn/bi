package com.pinting.gateway.loan7.in.model;

import java.util.Date;

/**
 * 自主放款-补账完成通知
 * @author bianyatian
 * @2016-11-28 上午10:24:38
 */
public class FillFinishReqModel extends BaseReqModel {
	
	/*客户端标识*/
	private			String 		clientKey;

	private String orderNo; //划拨单号
	
	private	String	payOrderNo;	//支付单号
	
	private Date applyTime; //划拨申请时间
	
	private Date finishTime; //划拨完成时间
	
	private String fillType; //补账类型,利息补账：INTEREST
	
	private Long amount; //金额,单位分

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public String getFillType() {
		return fillType;
	}

	public void setFillType(String fillType) {
		this.fillType = fillType;
	}

	public String getClientKey() {
		return clientKey;
	}

	public void setClientKey(String clientKey) {
		this.clientKey = clientKey;
	}

	public String getPayOrderNo() {
		return payOrderNo;
	}

	public void setPayOrderNo(String payOrderNo) {
		this.payOrderNo = payOrderNo;
	}
	
	
}
