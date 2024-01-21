package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * Author:      cyb
 * Date:        2017/2/27
 * Description: 2017周年庆第三期活动主页面信息
 */
public class ReqMsg_Activity_AnniversaryThirdHomePageInfo extends ReqMsg {

    private static final long serialVersionUID = -7218433075598396029L;

    private Integer userId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
