package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 根据借款表id查询月偿还本息数额、借款基本信息  入参
 * Created by shh on 2016/10/13 20:34.
 */
public class ReqMsg_Match_GetLoanBasicInfo extends ReqMsg {

    private static final long serialVersionUID = 65258998423289113L;

    private Integer loanId;

    private Integer loanRelationId;

    public Integer getLoanId() {
        return loanId;
    }

    public void setLoanId(Integer loanId) {
        this.loanId = loanId;
    }

    public Integer getLoanRelationId() {
        return loanRelationId;
    }

    public void setLoanRelationId(Integer loanRelationId) {
        this.loanRelationId = loanRelationId;
    }
}
