/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 财务对账数据：结算2.0（12.1-1.31）
 * @author HuanXiong
 * @version $Id: ReqMsg_FinancialAccount_ListQuery2.java, v 0.1 2016-2-15 下午12:09:45 HuanXiong Exp $
 */
public class ReqMsg_FinancialAccount_ListQuery2 extends ReqMsg {

    /**  */
    private static final long serialVersionUID = 1958774112138304480L;

    private String time;
    
    private String type;
    
    private Integer pageNum = 0;
    
    private Integer numPerPage = 20;
    
    private Integer start = 1;
    
    public Integer getStart() {
        start = (pageNum <= 1) ? 0 : ((pageNum - 1) * numPerPage);
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }
    
    public Integer getPageNum() {
        if (pageNum < 1) 
            this.pageNum = 1;
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getNumPerPage() {
        return numPerPage;
    }

    public void setNumPerPage(Integer numPerPage) {
        this.numPerPage = numPerPage;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
}
