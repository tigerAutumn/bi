package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 赞分期APP借款咨询与服务协议 根据借款编号查询管费、信息服务费、账户管理费、保费 入参
 * Created by shh on 2016/9/28 12:02.
 */
public class ReqMsg_Match_GetUserLoanFeeList extends ReqMsg {

    private static final long serialVersionUID = -3000922816266355874L;

    /* 借款编号 */
    private Integer loanId;

    /* 合作方借款编号 */
    private String partnerLoanId;

    public Integer getLoanId() {
        return loanId;
    }

    public void setLoanId(Integer loanId) {
        this.loanId = loanId;
    }

    public String getPartnerLoanId() {
        return partnerLoanId;
    }

    public void setPartnerLoanId(String partnerLoanId) {
        this.partnerLoanId = partnerLoanId;
    }
}
