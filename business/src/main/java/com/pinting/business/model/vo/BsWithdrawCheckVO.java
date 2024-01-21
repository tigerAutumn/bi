/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.model.vo;

import com.pinting.business.model.BsWithdrawCheck;

/**
 * 
 * @author HuanXiong
 * @version $Id: BsWithdrawCheckVO.java, v 0.1 2016-1-8 上午10:03:23 HuanXiong Exp $
 */
public class BsWithdrawCheckVO extends BsWithdrawCheck {
    
    private String mobile;
    
    private String userName;
    
    private String mUserName;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getmUserName() {
        return mUserName;
    }

    public void setmUserName(String mUserName) {
        this.mUserName = mUserName;
    }
    
}
