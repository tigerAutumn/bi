package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * Created by cyb on 2017/12/12.
 */
public class ReqMsg_Activity_ChrismasEveLuckyDraw extends ReqMsg {

    private static final long serialVersionUID = 7330195168430838032L;

    private Integer type;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
