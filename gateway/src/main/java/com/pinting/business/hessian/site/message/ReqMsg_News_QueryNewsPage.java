/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 分页查询所有新闻 入参
 * @author HuanXiong
 * @version $Id: ReqMsg_News_QueryNewsPage.java, v 0.1 2016-2-23 上午10:23:00 HuanXiong Exp $
 */
public class ReqMsg_News_QueryNewsPage extends ReqMsg {

    /**  */
    private static final long serialVersionUID = 4294101097893450296L;
    
    private String receiverType;    // 接收类型
    
    /*新闻公告类型 公告||媒体报道||湾粉活动||公司动态*/
    private String type;
    /*起始页*/
    private Integer pageNum = 0;
    /*每页条数*/
    private Integer numPerPage = 10;
    
    private Integer start = 1;
    
    private Integer totalPages; // 总页数
    
    private Integer totolRows = 0;  // 总数据数
    
    public Integer getTotalPages() {
        totalPages = (totolRows - totolRows % numPerPage) / numPerPage;
        if(totolRows % numPerPage > 0) 
            totalPages ++;
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getTotolRows() {
        return totolRows;
    }

    public void setTotolRows(Integer totolRows) {
        this.totolRows = totolRows;
    }
    
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReceiverType() {
        return receiverType;
    }

    public void setReceiverType(String receiverType) {
        this.receiverType = receiverType;
    }

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
    
}
