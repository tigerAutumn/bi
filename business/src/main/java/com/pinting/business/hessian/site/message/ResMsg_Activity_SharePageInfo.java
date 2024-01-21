package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * Created by cyb on 2017/11/7.
 */
public class ResMsg_Activity_SharePageInfo extends ResMsg {

    private static final long serialVersionUID = 8111606777954486150L;

    private String isJoined;

    public String getIsJoined() {
        return isJoined;
    }

    public void setIsJoined(String isJoined) {
        this.isJoined = isJoined;
    }
}
