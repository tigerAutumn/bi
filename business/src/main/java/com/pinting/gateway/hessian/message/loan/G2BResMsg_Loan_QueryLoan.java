package com.pinting.gateway.hessian.message.loan;

import com.pinting.core.hessian.msg.ResMsg;

public class G2BResMsg_Loan_QueryLoan extends ResMsg {

    private String loanResultCode;

    private String loanId;

    private String channel;

    private String loanResultMsg;

    /**
     * 打款成功时间
     */
    private String loanTime;

    public String getLoanResultCode() {
        return loanResultCode;
    }

    public void setLoanResultCode(String loanResultCode) {
        this.loanResultCode = loanResultCode;
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
