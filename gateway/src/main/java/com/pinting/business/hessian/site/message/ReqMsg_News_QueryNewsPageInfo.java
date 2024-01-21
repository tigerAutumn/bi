package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * Author:      shh
 * Date:        2017/6/30
 * Description: 分页查询港湾资讯、平台公告 入参
 */
public class ReqMsg_News_QueryNewsPageInfo extends ReqMsg {

    private static final long serialVersionUID = 7448423707327270223L;

    private String receiverType;    // 接收类型

    /**
     * 新闻公告类型
     * 1、公告（平台公告）
     * 2、媒体报道||湾粉活动||公司动态（港湾资讯）
     */
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
