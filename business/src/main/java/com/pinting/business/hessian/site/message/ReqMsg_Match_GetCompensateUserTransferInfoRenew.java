package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 代偿协议债权转让通知书 入参
 * Created by shh on 2017/3/8 14:36.
 */
public class ReqMsg_Match_GetCompensateUserTransferInfoRenew extends ReqMsg {

    private static final long serialVersionUID = -7060690176531752609L;

    /* 合作方借款编号 */
    private String loanId;

    /* 代偿编号 */
    private String orderNo;

    /* 代偿成功的时间 */
    private String lateRepayDate;

    /* 合作方编码 */
    private String partnerEnum;

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getLateRepayDate() {
        return lateRepayDate;
    }

    public void setLateRepayDate(String lateRepayDate) {
        this.lateRepayDate = lateRepayDate;
    }

    public String getPartnerEnum() {
        return partnerEnum;
    }

    public void setPartnerEnum(String partnerEnum) {
        this.partnerEnum = partnerEnum;
    }
}
