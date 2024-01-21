package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 债权转让协议req-赞分期债权转让协议签章 生成pdf数据准备
 * Created by shh on 2018/1/30 22:37.
 */

public class ReqMsg_Match_GetUserClaimsTransferInfoForPdf extends ReqMsg {

    private static final long serialVersionUID = -2103271448626294740L;

    /* 借贷关系ID */
    private Integer loanRelationId;

    public Integer getLoanRelationId() {
        return loanRelationId;
    }

    public void setLoanRelationId(Integer loanRelationId) {
        this.loanRelationId = loanRelationId;
    }
}
