package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 中奖列表
 */
public class ReqMsg_Activity_LuckyDrawList extends ReqMsg {

    private static final long serialVersionUID = -3112125010266692067L;

    private Integer pageNum;

    private Integer numPerPage;

    public Integer getPageNum() {
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
