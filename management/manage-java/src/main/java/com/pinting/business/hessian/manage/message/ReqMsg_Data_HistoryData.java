/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 
 * @author HuanXiong
 * @version $Id: ReqMsg_Data_HistoryData.java, v 0.1 2015-12-25 上午10:50:04 HuanXiong Exp $
 */
public class ReqMsg_Data_HistoryData extends ReqMsg {

    /**  */
    private static final long serialVersionUID = 8786163910134961979L;

    private String startTime;
    
    private String endTime;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
