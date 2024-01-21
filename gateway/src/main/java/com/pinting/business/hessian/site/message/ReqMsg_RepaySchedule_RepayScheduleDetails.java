package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 回款计划详情APP端 入参
 * Created by shh on 2017/3/31.
 */
public class ReqMsg_RepaySchedule_RepayScheduleDetails extends ReqMsg {

    private static final long serialVersionUID = -3006942061636214481L;

    /* 用户id */
    private Integer userId;

    /* 回款时间 */
    private String planDate;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getPlanDate() {
        return planDate;
    }

    public void setPlanDate(String planDate) {
        this.planDate = planDate;
    }
}
