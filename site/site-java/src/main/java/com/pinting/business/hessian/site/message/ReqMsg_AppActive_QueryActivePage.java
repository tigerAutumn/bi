package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * Author:      shh
 * Date:        2017/7/7
 * Description: H5活动中心 入参
 */
public class ReqMsg_AppActive_QueryActivePage extends ReqMsg {

    private static final long serialVersionUID = -8906054818275813276L;

    /* 活动发布端口 */
    private String showTerminal;

    /* 起始页 */
    private Integer pageNum = 0;

    /* 每页条数 */
    private Integer numPerPage = 10;

    private Integer start = 1;

    /* 总页数 */
    private Integer totalPages;

    /* 总数据数 */
    private Integer totolRows = 0;

    public String getShowTerminal() {
        return showTerminal;
    }

    public void setShowTerminal(String showTerminal) {
        this.showTerminal = showTerminal;
    }

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
