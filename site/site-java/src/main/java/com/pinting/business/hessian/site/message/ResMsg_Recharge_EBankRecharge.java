/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * 
 * @author HuanXiong
 * @version $Id: ResMsg_Recharge_EBankRecharge.java, v 0.1 2015-11-21 下午2:16:39 HuanXiong Exp $
 */
public class ResMsg_Recharge_EBankRecharge extends ResMsg {

    /**  */
    private static final long serialVersionUID = 1983725151276987334L;
    
    private String htmlStr;

    public String getHtmlStr() {
        return htmlStr;
    }

    public void setHtmlStr(String htmlStr) {
        this.htmlStr = htmlStr;
    }
    
}
