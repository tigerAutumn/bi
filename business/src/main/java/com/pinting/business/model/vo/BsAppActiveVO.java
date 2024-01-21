package com.pinting.business.model.vo;

import com.pinting.business.model.BsAppActive;

/**
 * Author:      shh
 * Date:        2017/8/2
 * Description: 活动中心vo
 */
public class BsAppActiveVO extends BsAppActive {

    /* 活动状态 1 进行中 2 预热中  3 已结束 */
    private String appActiveStatus;

    public String getAppActiveStatus() {
        return appActiveStatus;
    }

    public void setAppActiveStatus(String appActiveStatus) {
        this.appActiveStatus = appActiveStatus;
    }
}
