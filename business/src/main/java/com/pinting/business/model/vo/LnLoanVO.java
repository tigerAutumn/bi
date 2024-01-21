package com.pinting.business.model.vo;

/**
 * Created by 剑钊 on 2016/8/27.
 */
public class LnLoanVO {

    private String partnerLoanId;

    private String partnerOrderNo;

    private String status;

    private String channel;

    private String returnMsg;

    /**
     * 打款成功时间
     */
    private String loanTime;

    public String getPartnerLoanId() {
        return partnerLoanId;
    }

    public void setPartnerLoanId(String partnerLoanId) {
        this.partnerLoanId = partnerLoanId;
    }

    public String getPartnerOrderNo() {
        return partnerOrderNo;
    }

    public void setPartnerOrderNo(String partnerOrderNo) {
        this.partnerOrderNo = partnerOrderNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    public String getLoanTime() {
        return loanTime;
    }

    public void setLoanTime(String loanTime) {
        this.loanTime = loanTime;
    }
}
