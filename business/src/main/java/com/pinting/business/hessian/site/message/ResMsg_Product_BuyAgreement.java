/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.hessian.site.message;

import java.io.Serializable;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * 判断产品是否是当前用户 出参
 * @author HuanXiong
 * @version $Id: ResMsg_Product_BuyAgreement.java, v 0.1 2016-3-17 上午11:45:55 HuanXiong Exp $
 */
public class ResMsg_Product_BuyAgreement extends ResMsg {

    /**  */
    private static final long serialVersionUID = -3294350226268682374L;

    private Boolean isSelf; // 产品是否属于当前用户

    public Boolean getIsSelf() {
        return isSelf;
    }

    public void setIsSelf(Boolean isSelf) {
        this.isSelf = isSelf;
    }
    
}
