/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 判断产品是否已经下线 入参
 * @author HuanXiong
 * @version $Id: ReqMsg_Product_CheckProductIsOff.java, v 0.1 2016-2-18 上午11:50:45 HuanXiong Exp $
 */
public class ReqMsg_Product_CheckProductIsOff extends ReqMsg {

    /**  */
    private static final long serialVersionUID = 4402090422457802057L;
    /*产品id*/
    private Integer productId;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }
    
}
