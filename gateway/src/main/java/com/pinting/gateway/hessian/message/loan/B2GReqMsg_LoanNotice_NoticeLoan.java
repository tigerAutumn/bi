package com.pinting.gateway.hessian.message.loan;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * Created by 剑钊 on 2016/8/3.
 */
public class B2GReqMsg_LoanNotice_NoticeLoan extends ReqMsg {

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
