package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 2016公司年会抽奖活动 一二等奖抽取 入参
 * Created by shh on 2017/1/14 15:34.
 */
public class ReqMsg_EndOf2016CompanyAnnual_GetGrandPrize extends ReqMsg {

    private static final long serialVersionUID = 3868932834146870140L;

    /* 奖品等级Id */
    private Integer activityAwardId;

    public Integer getActivityAwardId() {
        return activityAwardId;
    }

    public void setActivityAwardId(Integer activityAwardId) {
        this.activityAwardId = activityAwardId;
    }
}
