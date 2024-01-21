package com.pinting.gateway.hessian.message.zsd;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 
 * @project gateway
 * @title B2GReqMsg_ZsdNotice_NoticeLoan.java
 * @author Dragon & cat
 * @date 2017-9-1
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description
 */
public class B2GReqMsg_ZsdNotice_NoticeLoan extends ReqMsg {

    /**
	 * 
	 */
	private static final long serialVersionUID = -2993457177084629266L;

	private String orderNo;

    private String loanId;
    
    private String channel;

	/*支付渠道*/
	private	String	payChannel;

    private String loanResultCode;

    private String loanResultMsg;

    /**
     * 打款成功时间
     */
    private String loanTime;

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

	public String getLoanResultCode() {
        return loanResultCode;
    }

    public void setLoanResultCode(String loanResultCode) {
        this.loanResultCode = loanResultCode;
    }

    public String getLoanResultMsg() {
        return loanResultMsg;
    }

    public void setLoanResultMsg(String loanResultMsg) {
        this.loanResultMsg = loanResultMsg;
    }

    public String getLoanTime() {
        return loanTime;
    }

    public void setLoanTime(String loanTime) {
        this.loanTime = loanTime;
    }

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}
    
    
}
