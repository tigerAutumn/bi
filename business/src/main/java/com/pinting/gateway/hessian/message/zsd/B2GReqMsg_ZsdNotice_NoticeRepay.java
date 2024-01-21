package com.pinting.gateway.hessian.message.zsd;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 还款通知
 * @author SHENGUOPING
 * @date  2017年8月30日 下午8:33:48
 */
public class B2GReqMsg_ZsdNotice_NoticeRepay extends ReqMsg {

	private static final long serialVersionUID = 4946558467633319163L;
	
	private String orderNo;

    private String loanId;
    
    /*支付渠道*/
	private	String	payChannel;

    private String repayResultCode;

    private String repayResultMsg;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getLoanId() {
		return loanId;
	}

	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}

	public String getPayChannel() {
		return payChannel;
	}

	public void setPayChannel(String payChannel) {
		this.payChannel = payChannel;
	}

	public String getRepayResultCode() {
		return repayResultCode;
	}

	public void setRepayResultCode(String repayResultCode) {
		this.repayResultCode = repayResultCode;
	}

	public String getRepayResultMsg() {
		return repayResultMsg;
	}

	public void setRepayResultMsg(String repayResultMsg) {
		this.repayResultMsg = repayResultMsg;
	}
    
}
