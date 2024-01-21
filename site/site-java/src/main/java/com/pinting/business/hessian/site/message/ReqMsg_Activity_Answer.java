package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * Author:      cyb
 * Date:        2017/6/27
 * Description:
 */
public class ReqMsg_Activity_Answer extends ReqMsg {

    private static final long serialVersionUID = -6626547475734013005L;

    private Integer userId;

    private Integer score;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
