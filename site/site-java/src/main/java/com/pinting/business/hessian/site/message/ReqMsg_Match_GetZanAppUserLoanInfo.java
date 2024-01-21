package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 赞分期App借款协议  入参
 * Created by shh on 2016/9/24 11:32.
 */
public class ReqMsg_Match_GetZanAppUserLoanInfo extends ReqMsg {

    private static final long serialVersionUID = -8656474077043328644L;

    /* 合作方借款编号 */
    private String partnerLoanId;

    public String getPartnerLoanId() {
        return partnerLoanId;
    }

    public void setPartnerLoanId(String partnerLoanId) {
        this.partnerLoanId = partnerLoanId;
    }
}
