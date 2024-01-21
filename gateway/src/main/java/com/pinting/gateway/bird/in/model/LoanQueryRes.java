package com.pinting.gateway.bird.in.model;

public class LoanQueryRes extends BaseResModel {

    /**
     * 借款编号
     */
    private String loanId;

    /**
     * 资金通道  目前为19Pay
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

}
