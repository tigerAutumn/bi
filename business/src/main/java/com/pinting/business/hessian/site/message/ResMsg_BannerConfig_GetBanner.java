package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * Author:      shh
 * Date:        2017/8/22
 * Description: 根据url查询banner信息 出参
 */
public class ResMsg_BannerConfig_GetBanner extends ResMsg {

    private static final long serialVersionUID = 4703923277482910614L;

    /* 活动类型 */
    private String activityType;

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }
}
