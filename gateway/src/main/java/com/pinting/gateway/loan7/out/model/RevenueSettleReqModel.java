package com.pinting.gateway.loan7.out.model;

import java.util.Date;
/**
 * 自主放款-营收结算通知
 * @author Dragon & cat
 * @date 2016-11-24
 */
public class RevenueSettleReqModel extends BaseReqModel {
	/*划拨单号*/
	private		String		orderNo;
	/*划拨申请时间*/
	private		Date		applyTime;
	/*划拨完成时间*/
	private		Date		finishTime;
	/*结算类型*/
	private		String		settleType;
	/*金额*/
	private		Long		amount;
	/*申请流水号*/
	private	String	requestSeq;
	
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
	public Long getAmount() {
		return amount;
	}
	public void setAmount(Long amount) {
		this.amount = amount;
	}
	public String getRequestSeq() {
		return requestSeq;
	}
	public void setRequestSeq(String requestSeq) {
		this.requestSeq = requestSeq;
	}
	public String getFileUrl() {
		return fileUrl;
	}
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	@Override
	public String toString() {
		return "RevenueSettleReqModel [orderNo=" + orderNo + ", applyTime="
				+ applyTime + ", finishTime=" + finishTime + ", settleType="
				+ settleType + ", amount=" + amount + ", requestSeq="
				+ requestSeq + ", fileUrl=" + fileUrl + "]";
	}
	
	
}
