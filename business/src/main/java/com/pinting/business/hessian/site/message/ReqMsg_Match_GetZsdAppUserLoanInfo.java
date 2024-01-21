package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 赞时贷APP借款协议 入参
 * Created by shh on 2017/10/31 14:00.
 * @author shh
 */
public class ReqMsg_Match_GetZsdAppUserLoanInfo extends ReqMsg {

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
