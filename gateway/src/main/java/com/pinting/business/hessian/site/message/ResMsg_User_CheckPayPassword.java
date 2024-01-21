/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.hessian.site.message;

import java.util.Date;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * 
 * @author HuanXiong
 * @version $Id: ResMsg_User_CheckPayPassword.java, v 0.1 2015-11-17 下午3:59:34 HuanXiong Exp $
 */
public class ResMsg_User_CheckPayPassword extends ResMsg {

	
    /**
	 * 
	 */
	private static final long serialVersionUID = -8169103381514576914L;

	private boolean truePayPassword;
    
    private Integer failNums;
    
    private Date failTime;
    
    private String toastMsg;
    
    public String getToastMsg() {
        return toastMsg;
    }

    public void setToastMsg(String toastMsg) {
        this.toastMsg = toastMsg;
    }

    public Integer getFailNums() {
        return failNums;
    }

    public void setFailNums(Integer failNums) {
        this.failNums = failNums;
    }

    public Date getFailTime() {
        return failTime;
    }

    public void setFailTime(Date failTime) {
        this.failTime = failTime;
    }

    public boolean isTruePayPassword() {
        return truePayPassword;
    }

    public void setTruePayPassword(boolean truePayPassword) {
        this.truePayPassword = truePayPassword;
    }

}
