package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * Author:      cyb
 * Date:        2017/6/7
 * Description: 活动基本数据请求信息
 */
public class ReqMsg_Activity_BaseData extends ReqMsg {

    private static final long serialVersionUID = -1200235012284734744L;

    /* 活动类型：摇一摇-shake；理财活动-buy；2017女神节活动-girl等等 */
    private String activityType;

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }
}
