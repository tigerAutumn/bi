/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 
 * @author HuanXiong
 * @version $Id: ReqMsg_Activity_DuringActivity.java, v 0.1 2016-1-27 上午11:29:22 HuanXiong Exp $
 */
public class ReqMsg_Activity_DuringActivity extends ReqMsg {

    /**  */
    private static final long serialVersionUID = -1182937227600667664L;

    /* 活动类型：摇一摇-shake；理财活动-buy */
    private String activityType;

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }
}
