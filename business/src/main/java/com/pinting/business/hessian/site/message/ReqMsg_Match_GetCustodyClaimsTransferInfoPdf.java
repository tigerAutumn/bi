package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 存管港湾产品债权转让协议 入参
 * Created by zousheng on 2017/5/09 13:21.
 */
public class ReqMsg_Match_GetCustodyClaimsTransferInfoPdf extends ReqMsg {

    private static final long serialVersionUID = 1L;

    /* 借贷关系ID */
    private Integer loanRelationId;

    /* 子账户ID */
    private Integer subAccountId;

    public Integer getLoanRelationId() {
        return loanRelationId;
    }

    public void setLoanRelationId(Integer loanRelationId) {
        this.loanRelationId = loanRelationId;
    }

    public Integer getSubAccountId() {
        return subAccountId;
    }

    public void setSubAccountId(Integer subAccountId) {
        this.subAccountId = subAccountId;
    }
}
