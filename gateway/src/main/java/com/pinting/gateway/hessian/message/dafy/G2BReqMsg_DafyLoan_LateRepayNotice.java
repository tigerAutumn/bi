package com.pinting.gateway.hessian.message.dafy;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.pinting.core.hessian.msg.ReqMsg;

public class G2BReqMsg_DafyLoan_LateRepayNotice extends ReqMsg {

	
/**
	 * 
	 */
	private static final long serialVersionUID = -3624800858907844408L;
	@NotEmpty(message="orderNo为空")
	private String orderNo; //代偿单号
	@NotEmpty(message="payOrderNo为空")
	private	String	payOrderNo; //支付单号
	@NotNull(message="applyTime为空")
	private Date applyTime; //划拨申请时间
	@NotNull(message="finishTime为空")
	private Date finishTime; //划拨完成时间
	@NotNull(message="totalAmount为空")
	private	Double totalAmount;//代偿总金额
	
	private  ArrayList<HashMap<String, Object>>  repayments; //账单信息
	

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

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public ArrayList<HashMap<String, Object>> getRepayments() {
		return repayments;
	}

	public void setRepayments(ArrayList<HashMap<String, Object>> repayments) {
		this.repayments = repayments;
	}

	public String getPayOrderNo() {
		return payOrderNo;
	}

	public void setPayOrderNo(String payOrderNo) {
		this.payOrderNo = payOrderNo;
	}
	
}
