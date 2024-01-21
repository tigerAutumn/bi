package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * Author:      cyb
 * Date:        2016/8/27
 * Description: 账户余额请求信息
 */
public class ReqMsg_Invest_AccountBalance extends ReqMsg {

    private static final long serialVersionUID = -833218527386327306L;

    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
