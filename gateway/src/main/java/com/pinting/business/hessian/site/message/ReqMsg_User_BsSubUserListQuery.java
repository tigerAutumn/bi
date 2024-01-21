/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 
 * @author HuanXiong
 * @version $Id: ReqMsg_User_BsSubUserListQuery.java, v 0.1 2015-12-22 下午4:03:50 HuanXiong Exp $
 */
public class ReqMsg_User_BsSubUserListQuery extends ReqMsg {

    /**  */
    private static final long serialVersionUID = -5178120193491012106L;
    
    private Integer userId;

    private Integer pageIndex;
    
    private Integer pageSize;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
    
}
