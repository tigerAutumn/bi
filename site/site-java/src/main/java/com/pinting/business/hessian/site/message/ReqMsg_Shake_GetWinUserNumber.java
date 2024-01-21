/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 
 * @author HuanXiong
 * @version $Id: ReqMsg_Shake_GetWinUserNumber.java, v 0.1 2016-3-11 下午2:13:15 HuanXiong Exp $
 */
public class ReqMsg_Shake_GetWinUserNumber extends ReqMsg {

    /**  */
    private static final long serialVersionUID = -1963801559308838874L;
    
    private String activityFlag;    // 活动标识
    
    // ===================== 528活动参数 开始========================
    private String terminal;
    // ===================== 528活动参数  结束========================
    
    // ===================== 母亲节活动参数 开始======================
    private Integer userId;
    // ===================== 母亲节活动参数 结束======================

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getActivityFlag() {
        return activityFlag;
    }

    public void setActivityFlag(String activityFlag) {
        this.activityFlag = activityFlag;
    }

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }
    
    // ===================== 318摇一摇参数 开始======================
    // ===================== 318摇一摇参数 结束======================
    
}

