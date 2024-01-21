package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * Created by shh on 2016/9/19 21:26.
 */
public class ReqMsg_Match_GetUserMatchLoanInfo extends ReqMsg {

    private static final long serialVersionUID = 6217670013554179766L;

    private Integer loanRelationId;

    public Integer getLoanRelationId() {
        return loanRelationId;
    }

    public void setLoanRelationId(Integer loanRelationId) {
        this.loanRelationId = loanRelationId;
    }
}
