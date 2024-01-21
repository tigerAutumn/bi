package com.pinting.gateway.loan7.in.model;

import java.util.Date;
import java.util.List;

/**
 * 自主放款-代偿通知
 * @author bianyatian
 * @2016-11-28 下午1:57:27
 */
public class LateRepayReqModel extends BaseReqModel {
	
	/*客户端标识*/
	private			String 		clientKey;

	private String orderNo; //代偿单号
	
	private	String	payOrderNo; //支付单号
	
	private Date applyTime; //划拨申请时间
	
	private Date finishTime; //划拨完成时间
	
	private	Long totalAmount;//代偿总金额

	private  List<LateRepay>  repayments; //账单信息
	
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

	public Long getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Long totalAmount) {
		this.totalAmount = totalAmount;
	}

	public List<LateRepay> getRepayments() {
		return repayments;
	}

	public void setRepayments(List<LateRepay> repayments) {
		this.repayments = repayments;
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
