/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * 
 * @author HuanXiong
 * @version $Id: ResMsg_UserWithdraw_CanWithdrawTime.java, v 0.1 2015-12-24 上午10:54:06 HuanXiong Exp $
 */
public class ResMsg_UserWithdraw_CanWithdrawTime extends ResMsg {

    /**  */
    private static final long serialVersionUID = 2373358891584358527L;


    private String begin;
    private String end;
    private String beginTime;
    private String endTime;
    public String getBeginTime() {
        return beginTime;
    }
    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }
    public String getEndTime() {
        return endTime;
    }
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
    public String getBegin() {
        return begin;
    }
    public void setBegin(String begin) {
        this.begin = begin;
    }
    public String getEnd() {
        return end;
    }
    public void setEnd(String end) {
        this.end = end;
    }
    
}
