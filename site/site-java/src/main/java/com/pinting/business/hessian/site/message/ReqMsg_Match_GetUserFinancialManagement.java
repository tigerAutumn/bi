package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 根据借款ID查询这一笔借款对应的理财人数据  入参
 * Created by shh on 2016/9/28 16:41.
 */
public class ReqMsg_Match_GetUserFinancialManagement extends ReqMsg {

    private static final long serialVersionUID = -4534671727622740667L;

    /* 借款用户编号 */
    private Integer lnUserId;

    /* 借款编号 */
    private Integer loanId;

    public Integer getLnUserId() {
        return lnUserId;
    }

    public void setLnUserId(Integer lnUserId) {
        this.lnUserId = lnUserId;
    }

    public Integer getLoanId() {
        return loanId;
    }

    public void setLoanId(Integer loanId) {
        this.loanId = loanId;
    }
}
