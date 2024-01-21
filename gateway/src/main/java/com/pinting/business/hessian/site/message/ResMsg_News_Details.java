/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.hessian.site.message;

import java.util.HashMap;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * 根据id和类型查询某一个新闻的内容 出参
 * @author HuanXiong
 * @version $Id: ResMsg_News_Details.java, v 0.1 2016-2-23 上午10:31:26 HuanXiong Exp $
 */
public class ResMsg_News_Details extends ResMsg {

    /**  */
    private static final long serialVersionUID = 4267395068487988059L;
    /*结果列表*/
    private HashMap<String, Object> details;

    public HashMap<String, Object> getDetails() {
        return details;
    }

    public void setDetails(HashMap<String, Object> details) {
        this.details = details;
    }

}
