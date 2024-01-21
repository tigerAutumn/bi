package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 判断借款协议、债权转让的协议loanRelationId是否属于当前登录的用户 入参
 * Created by shh on 2016/11/1 14:43.
 */
public class ReqMsg_Match_CheckUserIdLoanRelationId extends ReqMsg {

    private static final long serialVersionUID = -4623759997672423702L;

    /*用户id*/
    private Integer userId;

    /*符合借贷关系ID*/
    private Integer loanRelationId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getLoanRelationId() {
        return loanRelationId;
    }

    public void setLoanRelationId(Integer loanRelationId) {
        this.loanRelationId = loanRelationId;
    }
}
