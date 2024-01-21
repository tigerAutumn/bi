package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * Author:      cyb
 * Date:        2017/2/14
 * Description:
 */
public class ReqMsg_TransDetail_QueryZanReturnDetail extends ReqMsg {

    private static final long serialVersionUID = 5262885039972135925L;

    private Integer userId;

    private String time;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
