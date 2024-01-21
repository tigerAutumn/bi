package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 代偿-债权转让协议 入参
 * Created by shh on 2017/3/8 17:23.
 */
public class ReqMsg_Match_GetCompensateTransferInfoRenew extends ReqMsg {

    private static final long serialVersionUID = 4257298286562701083L;

    /* 借贷关系ID */
    private Integer loanRelationId;

    /* 借款ID */
    private Integer loanId;

    /* 合作方编码 */
    private String partnerEnum;

    public Integer getLoanRelationId() {
        return loanRelationId;
    }

    public void setLoanRelationId(Integer loanRelationId) {
        this.loanRelationId = loanRelationId;
    }

    public Integer getLoanId() {
        return loanId;
    }

    public void setLoanId(Integer loanId) {
        this.loanId = loanId;
    }

    public String getPartnerEnum() {
        return partnerEnum;
    }

    public void setPartnerEnum(String partnerEnum) {
        this.partnerEnum = partnerEnum;
    }
}
