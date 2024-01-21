package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 债权转让协议req
 * Created by shh on 2016/9/22 11:07.
 */
public class ReqMsg_Match_GetUserClaimsTransferInfo extends ReqMsg {

    private static final long serialVersionUID = 8418293729015891201L;

    /* 借贷关系ID */
    private Integer loanRelationId;

    public Integer getLoanRelationId() {
        return loanRelationId;
    }

    public void setLoanRelationId(Integer loanRelationId) {
        this.loanRelationId = loanRelationId;
    }
}
