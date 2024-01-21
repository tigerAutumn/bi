package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * Author:      cyb
 * Date:        2017/2/27
 * Description: 2017周年庆二重礼3月18日解锁元宝
 */
public class ReqMsg_Activity_AnniversaryUnlockGoldIngot extends ReqMsg {

    private static final long serialVersionUID = 4919473029802738071L;

    private Integer userId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
