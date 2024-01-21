/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * 网银充值返回
 * @author HuanXiong
 * @version $Id: ResMsg_Recharge_EBankRecharge.java, v 0.1 2015-11-20 下午5:51:16 HuanXiong Exp $
 */
public class ResMsg_Recharge_EBankRecharge extends ResMsg {

    /**  */
    private static final long serialVersionUID = -1838577123549498464L;

    private String htmlStr;

    public String getHtmlStr() {
        return htmlStr;
    }

    public void setHtmlStr(String htmlStr) {
        this.htmlStr = htmlStr;
    }
    
}
