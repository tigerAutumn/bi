/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 设置交易密码
 * @author HuanXiong
 * @version $Id: ReqMsg_User_SetUpTraderPassword.java, v 0.1 2015-11-16 下午2:56:19 HuanXiong Exp $
 */
public class ReqMsg_User_SetUpTraderPassword extends ReqMsg {


    /**
	 * 
	 */
	private static final long serialVersionUID = -407563098735019549L;

	private String            payPassword;

    private Integer           userId;

    public String getPayPassword() {
        return payPassword;
    }

    public void setPayPassword(String payPassword) {
        this.payPassword = payPassword;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

}
