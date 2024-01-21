package com.pinting.business.model.common;


import com.pinting.business.util.Constants;

import java.io.Serializable;

/**
 * 分页页码查询参数
 */
public class PagerReqDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer numPerPage = Constants.MANAGE_DEFAULT_NUMPERPAGE; // 每页条数

    private Integer pageNum = Constants.MANAGE_DEFAULT_PAGENUM; // 起始页码

    private String orderBy; // ASC升序，DESC降序

    private Boolean count = true; // 是否查询select count(*) from值, 默认查询count值

    public Integer getNumPerPage() {
        return numPerPage;
    }

    public void setNumPerPage(Integer numPerPage) {
        this.numPerPage = numPerPage;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public Boolean getCount() {
        return count;
    }

    public void setCount(Boolean count) {
        this.count = count;
    }
}