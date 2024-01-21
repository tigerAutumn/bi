package com.pinting.gateway.hessian.message.dafy;

import java.util.Date;

import com.pinting.core.hessian.msg.ReqMsg;

public class B2GReqMsg_DafyLoanNotice_RepayResultNotice extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3567020675172943850L;

	private String orderNo; //还款单号
	
	private String bgwOrderNo; //支付单号-非必填
	
	private String channelLoan; //支付渠道-BAOFOO
	
	private String resultCode; //支付结果码-SUCCESS/PROCESS/FAIL
	
	private String resultMsg; //支付结果信息-非必填
	
	private Date finishTime; //还款成功时间-非必填

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


	public String getChannelLoan() {
		return channelLoan;
	}

	public void setChannelLoan(String channelLoan) {
		this.channelLoan = channelLoan;
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
