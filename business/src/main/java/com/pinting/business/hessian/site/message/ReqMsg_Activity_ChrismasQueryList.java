package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * Created by cyb on 2017/12/12.
 */
public class ReqMsg_Activity_ChrismasQueryList extends ReqMsg {

    private static final long serialVersionUID = -4014833390037371409L;

    private Integer type;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
