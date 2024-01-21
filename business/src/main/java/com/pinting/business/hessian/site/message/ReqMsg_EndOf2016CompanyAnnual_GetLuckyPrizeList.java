package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 2016公司年会抽奖活动 五六七等奖抽取 入参
 * Created by shh on 2017/1/14 15:24.
 */
public class ReqMsg_EndOf2016CompanyAnnual_GetLuckyPrizeList extends ReqMsg {

    private static final long serialVersionUID = -3644808933053221852L;

    /* 奖品等级Id */
    private Integer activityAwardId;

    public Integer getActivityAwardId() {
        return activityAwardId;
    }

    public void setActivityAwardId(Integer activityAwardId) {
        this.activityAwardId = activityAwardId;
    }
}
