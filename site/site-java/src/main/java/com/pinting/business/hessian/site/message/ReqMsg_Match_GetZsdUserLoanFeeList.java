package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;


/**
 * 赞时贷APP借款咨询与服务协议 根据借款编号查询 快速信审费、平台使用费、账户管理费、代收通道费入参
 * Created by shh on 2017/10/31 14:00.
 * @author shh
 */
public class ReqMsg_Match_GetZsdUserLoanFeeList extends ReqMsg {

    private static final long serialVersionUID = -7960456001421672556L;

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
