package com.pinting.gateway.dafy.out.model;

import java.util.Date;

public class RepayResultNoticeReqModel extends BaseReqModel {

	private String orderNo; //还款单号
	
	private String bgwOrderNo; //支付单号-非必填
	
	private String channel; //支付渠道-BAOFOO
	
	private String resultCode; //支付结果码-SUCCESS/PROCESS/FAIL
	
	private String resultMsg; //支付结果信息-非必填
	
	private Date finishTime; //还款成功时间-非必填
	/*申请流水号*/
	private		String		requestSeq;

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

	public String getRequestSeq() {
		return requestSeq;
	}

	public void setRequestSeq(String requestSeq) {
		this.requestSeq = requestSeq;
	}

	@Override
	public String toString() {
		return "RepayResultNoticeReqModel [orderNo=" + orderNo
				+ ", bgwOrderNo=" + bgwOrderNo + ", channel=" + channel
				+ ", resultCode=" + resultCode + ", resultMsg=" + resultMsg
				+ ", finishTime=" + finishTime + ", requestSeq=" + requestSeq
				+ "]";
	}
	
}
