package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * Author:      cyb
 * Date:        2017/4/5
 * Description:
 */
public class ReqMsg_AgentActivity_WaterVotePageInfo extends ReqMsg {
    private static final long serialVersionUID = -6069184158842238014L;

    private Integer userId;
    private Integer signUpNo;
    private Integer pageNum;
    private Integer numPerPage;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getSignUpNo() {
        return signUpNo;
    }

    public void setSignUpNo(Integer signUpNo) {
        this.signUpNo = signUpNo;
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
