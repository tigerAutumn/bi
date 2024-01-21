/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * 判断产品是否已经下线 出参
 * @author HuanXiong
 * @version $Id: ResMsg_Product_CheckProductIsOff.java, v 0.1 2016-2-18 上午11:54:20 HuanXiong Exp $
 */
public class ResMsg_Product_CheckProductIsOff extends ResMsg {
    
    /**  */
    private static final long serialVersionUID = 7798546902846198990L;
    /*true：已下线,false：未下线*/
    private Boolean isOff;

    public Boolean getIsOff() {
        return isOff;
    }

    public void setIsOff(Boolean isOff) {
        this.isOff = isOff;
    }
    
}   
