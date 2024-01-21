package com.pinting.gateway.hessian.message.dafy;

import java.util.Date;

import com.pinting.core.hessian.msg.ReqMsg;

public class B2GReqMsg_DafyLoanNotice_RevenueSettle extends ReqMsg {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7471509539124368492L;
	/*划拨单号*/
	private		String		orderNo;
	/*划拨申请时间*/
	private		Date		applyTime;
	/*划拨完成时间*/
	private		Date		finishTime;
	/*结算类型*/
	private		String		settleType;
	/*金额*/
	private		Double		amount;
	/*结算明细文件下载地址*/
	private		String		fileUrl;
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
	public String getSettleType() {
		return settleType;
	}
	public void setSettleType(String settleType) {
		this.settleType = settleType;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getFileUrl() {
		return fileUrl;
	}
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	
	
}
