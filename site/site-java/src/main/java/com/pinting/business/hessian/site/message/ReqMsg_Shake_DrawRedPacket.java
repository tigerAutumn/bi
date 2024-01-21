/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 
 * @author HuanXiong
 * @version $Id: ReqMsg_Shake_DrawRedPacket.java, v 0.1 2016-3-10 下午7:53:06 HuanXiong Exp $
 */
public class ReqMsg_Shake_DrawRedPacket extends ReqMsg {

    /**  */
    private static final long serialVersionUID = -388319629794725905L;
    
    private String activityFlag;    // 活动标识
    
    // ================================== 518理财节活动参数-开始 ================================== 
    private Double amount;  // 投资金额
    private Integer term;   // 投资期限
    // ================================== 518理财节活动参数-开始 ================================== 

    // ================================== 母亲节活动参数-开始 ================================== 
    private String serialNo;
    
    private Integer userId;
    // ================================== 母亲节活动参数-结束 ================================== 
    
    // ================================== 318摇一摇参数-开始 ================================== 
    private String mobile;
    // ================================== 318摇一摇参数-结束 ================================== 
    
    public String getMobile() {
        return mobile;
    }

    public Integer getTerm() {
        return term;
    }

    public void setTerm(Integer term) {
        this.term = term;
    }

    public String getActivityFlag() {
        return activityFlag;
    }

    public void setActivityFlag(String activityFlag) {
        this.activityFlag = activityFlag;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
    
}
