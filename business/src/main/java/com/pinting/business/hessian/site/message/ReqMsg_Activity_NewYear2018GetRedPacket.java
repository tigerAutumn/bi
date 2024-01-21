package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * Created by cyb on 2018/1/29.
 */
public class ReqMsg_Activity_NewYear2018GetRedPacket extends ReqMsg {

    private static final long serialVersionUID = 2915983347270131831L;

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
