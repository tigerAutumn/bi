package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * Author:      cyb
 * Date:        2017/2/27
 * Description: 六重礼抽奖
 */
public class ReqMsg_Activity_AnniversaryLuckyDraw extends ReqMsg {

    private static final long serialVersionUID = -2247949775350921725L;

    private Integer userId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

}
