package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * Author:      cyb
 * Date:        2016/8/29
 * Description:
 */
public class ReqMsg_Match_QueryLnLoanRelationList extends ReqMsg {

    private static final long serialVersionUID = 9187744117698539507L;

    /* 用户ID */
    private Integer userId;

    /* 用户子账户ID */
    private Integer subAccountId;

    /* 当前页数 */
    private Integer pageNum;

    /* 每页显示条数 */
    private Integer numPerPage;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getSubAccountId() {
        return subAccountId;
    }

    public void setSubAccountId(Integer subAccountId) {
        this.subAccountId = subAccountId;
    }

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
