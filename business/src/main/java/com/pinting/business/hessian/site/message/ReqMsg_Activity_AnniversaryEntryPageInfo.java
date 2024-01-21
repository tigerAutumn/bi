package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * Author:      cyb
 * Date:        2017/2/27
 * Description: 周年庆入口页面信息
 */
public class ReqMsg_Activity_AnniversaryEntryPageInfo extends ReqMsg {

    private static final long serialVersionUID = -6709719526756772561L;

    private Integer userId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
