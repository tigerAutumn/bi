package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 回款计划APP端 入参
 * Created by shh on 2017/3/31.
 */
public class ReqMsg_RepaySchedule_RepaymentPlanList extends ReqMsg {

    private static final long serialVersionUID = 346160759222931097L;

    /* 用户id */
    private Integer userId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
