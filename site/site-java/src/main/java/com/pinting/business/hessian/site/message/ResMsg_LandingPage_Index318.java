/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * 2016 318活动的广告落地页 出参
 * @author HuanXiong
 * @version $Id: ResMsg_LandingPage_Index318.java, v 0.1 2016-3-23 上午11:23:19 HuanXiong Exp $
 */
public class ResMsg_LandingPage_Index318 extends ResMsg {

    /**  */
    private static final long serialVersionUID = 1400436737103617908L;

    private String totalInterest;   // 累计赚取

    public String getTotalInterest() {
        return totalInterest;
    }

    public void setTotalInterest(String totalInterest) {
        this.totalInterest = totalInterest;
    }
}
