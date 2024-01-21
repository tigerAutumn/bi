package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

import java.util.List;

/**
 * 收款确认函 、代偿债权转让协议（重新生成） 入参
 * Created by shh on 2017/2/8 17:47.
 */
public class ReqMsg_Match_GetUserClaimsTransferInfoListRenew extends ReqMsg {

    private static final long serialVersionUID = -8917497235556195709L;

    /* 合作方借款编号 */
    private String loanId;

    /* 代偿编号 */
    private String orderNo;

    /* 代偿成功的时间 */
    private String lateRepayDate;

    /* 合作方编码 */
    private String partnerEnum;

    /* 合作方借款编号List */
    private List<String> partnerLoanIdList;

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

    public List<String> getPartnerLoanIdList() {
        return partnerLoanIdList;
    }

    public void setPartnerLoanIdList(List<String> partnerLoanIdList) {
        this.partnerLoanIdList = partnerLoanIdList;
    }
}
