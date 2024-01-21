package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 查询奖项 已抽奖的人次 入参
 * Created by shh on 2017/1/14 15:58.
 */
public class ReqMsg_EndOf2016CompanyAnnual_NumberOfDraws extends ReqMsg {

    private static final long serialVersionUID = 1097495014550034363L;

    /* 奖品等级Id */
    private Integer activityAwardId;

    public Integer getActivityAwardId() {
        return activityAwardId;
    }

    public void setActivityAwardId(Integer activityAwardId) {
        this.activityAwardId = activityAwardId;
    }
}
