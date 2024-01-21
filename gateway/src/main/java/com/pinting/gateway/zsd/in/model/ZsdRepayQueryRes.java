package com.pinting.gateway.zsd.in.model;

/**
 * Created by zhangbao on 2017/8/30.
 */
public class ZsdRepayQueryRes extends BaseResModel {
    /**
     * 借款编号
     */
    private String loanId;

    /**
     * 资金通道
     */
    private String channel;

    /**
     * 借款结果编码
     */
    private String repayResultCode;

    /**
     * 借款结果信息
     */
    private String repayResultMsg;

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
