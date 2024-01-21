package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * Author:      cyb
 * Date:        2017/2/27
 * Description: 2017周年庆第一期活动主页面信息
 */
public class ReqMsg_Activity_AnniversarySecondHomePageInfo extends ReqMsg {

    private static final long serialVersionUID = 2733319721262046471L;

    /* 用户ID */
    private Integer userId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
