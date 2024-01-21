package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * 判断借款协议、债权转让的协议loanRelationId是否属于当前登录的用户 出参s
 * Created by shh on 2016/11/1 14:50.
 */
public class ResMsg_Match_CheckUserIdLoanRelationId extends ResMsg {

    private static final long serialVersionUID = -7687222924723611168L;

    /*符合借贷关系ID，不符合，返回-1*/
    private Integer loanRelationId;

    public Integer getLoanRelationId() {
        return loanRelationId;
    }

    public void setLoanRelationId(Integer loanRelationId) {
        this.loanRelationId = loanRelationId;
    }
}
