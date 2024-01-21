/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.hessian.site.message;

import java.util.HashMap;
import java.util.List;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * 
 * @author HuanXiong
 * @version $Id: ResMsg_User_BsSubUserListQuery.java, v 0.1 2015-12-22 下午4:04:56 HuanXiong Exp $
 */
public class ResMsg_User_BsSubUserListQuery extends ResMsg {

    private static final long serialVersionUID = -9088926883106846790L;

    /**
     * 以下循环：
     * id                   用户Id
     * nick                 用户昵称
     * userName                   用户名
     * mobile               手机号
     * idCard               身份证
     */
    private List<HashMap<String, Object>> bsUserList;
    
    private Double totalEarnings;
    
    private Integer totalCount;
    
    private Integer pageIndex;

    public List<HashMap<String, Object>> getBsUserList() {
        return bsUserList;
    }

    public void setBsUserList(List<HashMap<String, Object>> bsUserList) {
        this.bsUserList = bsUserList;
    }

    public Double getTotalEarnings() {
        return totalEarnings;
    }

    public void setTotalEarnings(Double totalEarnings) {
        this.totalEarnings = totalEarnings;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }
    
}
