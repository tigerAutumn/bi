/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.model.vo;

import java.io.Serializable;

import com.pinting.business.model.BsFinanceWithdrawRecord;

/**
 * 
 * @author HuanXiong
 * @version $Id: FinanceWithdrawRecordVO.java, v 0.1 2016-3-28 上午11:58:12 HuanXiong Exp $
 */
public class FinanceWithdrawRecordVO extends BsFinanceWithdrawRecord implements Serializable {

    /**  */
    private static final long serialVersionUID = -8973245782133315542L;
    
    private String opUserName;
    
    private String confirmUserName;
    
    // ==================================== 请求参数 ==========================================
    // 开始时间
    private String startTime;
    
    // 结束时间
    private String endTime;

    public String getOpUserName() {
        return opUserName;
    }

    public void setOpUserName(String opUserName) {
        this.opUserName = opUserName;
    }

    public String getConfirmUserName() {
        return confirmUserName;
    }

    public void setConfirmUserName(String confirmUserName) {
        this.confirmUserName = confirmUserName;
    }

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
