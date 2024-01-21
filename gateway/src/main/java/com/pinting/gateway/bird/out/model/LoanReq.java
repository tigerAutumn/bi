package com.pinting.gateway.bird.out.model;


public class LoanReq extends BaseReqModel {

    /**
     * 借款订单号
     */
    private String orderNo;


    /**
     * 借款编号
     */
    private String loanId;

    /**
     * 资金通道  目前为baofoo
     */
    private String channel;

    /**
     * 借款结果编码
     */
    private String loanResultCode;

    /**
     * 借款结果信息
     */
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

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
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
    
}
