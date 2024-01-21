package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * Created by cyb on 2017/11/6.
 */
public class ReqMsg_Activity_ExchangeGift extends ReqMsg {

    private static final long serialVersionUID = 1033876248802862797L;

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
