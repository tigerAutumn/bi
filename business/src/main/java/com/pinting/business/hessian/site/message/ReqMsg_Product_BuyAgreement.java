/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 判断产品是否是当前用户 入参
 * @author HuanXiong
 * @version $Id: ReqMsg_Product_BuyAgreement.java, v 0.1 2016-3-17 上午11:45:00 HuanXiong Exp $
 */
public class ReqMsg_Product_BuyAgreement extends ReqMsg {

    /**  */
    private static final long serialVersionUID = -2068958218156163400L;
    /*产品id*/
    private Integer productId;
    /*用户id*/
    private Integer userId;
    /*子账户id*/
    private Integer subAccountId;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getSubAccountId() {
        return subAccountId;
    }

    public void setSubAccountId(Integer subAccountId) {
        this.subAccountId = subAccountId;
    }
    
}
