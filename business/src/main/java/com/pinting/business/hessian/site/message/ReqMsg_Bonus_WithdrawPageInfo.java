package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * Author:      cyb
 * Date:        2017/4/14
 * Description: 奖励金提现页面信息请求参数
 */
public class ReqMsg_Bonus_WithdrawPageInfo extends ReqMsg {

    private static final long serialVersionUID = -262372102151184732L;

    /*用户id*/
    private Integer userId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

}
