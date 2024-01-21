/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * 
 * @author HuanXiong
 * @version $Id: ResMsg_Activity_DuringActivity.java, v 0.1 2016-1-27 上午11:29:59 HuanXiong Exp $
 */
public class ResMsg_Activity_DuringActivity extends ResMsg {

    /**  */
    private static final long serialVersionUID = 6829295375871527735L;

    private String isDuringActivity;

    public String getIsDuringActivity() {
        return isDuringActivity;
    }

    public void setIsDuringActivity(String isDuringActivity) {
        this.isDuringActivity = isDuringActivity;
    }
}
