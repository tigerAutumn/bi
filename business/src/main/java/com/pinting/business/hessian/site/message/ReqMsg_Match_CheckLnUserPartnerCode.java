package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * Author:      shh
 * Date:        2018/2/1
 * Description: 赞时贷资金迁移协议 查询借款人的资产方
 */
public class ReqMsg_Match_CheckLnUserPartnerCode extends ReqMsg {

    private static final long serialVersionUID = -4490150382469123307L;

    private Integer loanRelationId;

    public Integer getLoanRelationId() {
        return loanRelationId;
    }

    public void setLoanRelationId(Integer loanRelationId) {
        this.loanRelationId = loanRelationId;
    }
}
