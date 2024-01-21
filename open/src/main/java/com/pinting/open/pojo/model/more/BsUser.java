/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.open.pojo.model.more;

/**
 * 
 * @author HuanXiong
 * @version $Id: BsUser.java, v 0.1 2015-12-29 上午11:13:06 HuanXiong Exp $
 */
public class BsUser {
    private String mobile;
    
    private String registerTime;
    
    private boolean status;  // 投资状态 true:已投资；false：暂未投资

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(String registerTime) {
        this.registerTime = registerTime;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    
}
