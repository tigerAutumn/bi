package com.pinting.gateway.hessian.message.dafy;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 
 * @author bianyatian
 * @2016-11-28 上午10:41:34
 */
public class G2BReqMsg_DafyLoan_FillFinishNotice extends ReqMsg {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1227761656283690787L;

	@NotEmpty(message="orderNo为空")
	private String orderNo; //划拨单号
	@NotEmpty(message="payOrderNo为空")
	private	String	payOrderNo;	//支付单号
	@NotNull(message="applyTime为空")
	private Date applyTime; //划拨申请时间
	@NotNull(message="finishTime为空")
	private Date finishTime; //划拨完成时间
	@NotEmpty(message="fillType为空")
	private String fillType; //补账类型,利息补账：INTEREST
	@NotNull(message="amount为空")
	private Double amount; //金额,单位元

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

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getFillType() {
		return fillType;
	}

	public void setFillType(String fillType) {
		this.fillType = fillType;
	}

	public String getPayOrderNo() {
		return payOrderNo;
	}

	public void setPayOrderNo(String payOrderNo) {
		this.payOrderNo = payOrderNo;
	}
	
}
