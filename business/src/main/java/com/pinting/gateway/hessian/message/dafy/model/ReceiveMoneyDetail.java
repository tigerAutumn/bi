package com.pinting.gateway.hessian.message.dafy.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @Project: gateway
 * @Title: ReceiveMoneyDetail.java
 * @author Zhou Changzai
 * @date 2015-4-1 下午5:12:52
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public class ReceiveMoneyDetail implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3591671113395712476L;
	private String cardNo;//	身份证号
	private String name;//	客户姓名
	private String bankNo;//	银行卡号
	private double amountBj;//	理财本金
	private double amountLx;//	理财收益
	private Date successTime;//	成功时间
	private int status;//	状态 status (0成功 1失败)
	private String orderNo;//	订单号
	private String productId;//	产品编号
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBankNo() {
		return bankNo;
	}
	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}
	public double getAmountBj() {
		return amountBj;
	}
	public void setAmountBj(double amountBj) {
		this.amountBj = amountBj;
	}
	public double getAmountLx() {
		return amountLx;
	}
	public void setAmountLx(double amountLx) {
		this.amountLx = amountLx;
	}
	public Date getSuccessTime() {
		return successTime;
	}
	public void setSuccessTime(Date successTime) {
		this.successTime = successTime;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
}
