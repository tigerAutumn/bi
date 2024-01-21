package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * Created by cyb on 2018/5/4.
 */
public class ReqMsg_Product_IsExistRedPacket extends ReqMsg {

    private static final long serialVersionUID = 7702305870504548958L;

    private Integer userId;

    private Integer term;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getTerm() {
        return term;
    }

    public void setTerm(Integer term) {
        this.term = term;
    }
}

