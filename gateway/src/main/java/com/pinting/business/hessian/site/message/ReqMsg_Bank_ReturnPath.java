/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 回款路径列表 入参
 * @author HuanXiong
 * @version $Id: ReqMsg_Bank_ReturnPath.java, v 0.1 2015-11-18 下午6:40:06 HuanXiong Exp $
 */
public class ReqMsg_Bank_ReturnPath extends ReqMsg {


    /**
	 * 
	 */
	private static final long serialVersionUID = 310610361317872396L;
	/*用户id*/
	private Integer userId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    
    
}
