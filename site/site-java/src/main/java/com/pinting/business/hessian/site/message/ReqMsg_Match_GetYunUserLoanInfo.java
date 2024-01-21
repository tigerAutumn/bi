package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

import java.util.Date;

/**
 * 云贷三方借款协议 入参
 * Created by zhangpeng on 2018/3/5
 * @author zhangpeng
 */

public class ReqMsg_Match_GetYunUserLoanInfo extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2499375434241112219L;

	/* 合作方借款编号 */
    private String partnerLoanId;

    /* 借款放款时间 */
    private Date loanTime;

    public Date getLoanTime() {
        return loanTime;
    }

    public void setLoanTime(Date loanTime) {
        this.loanTime = loanTime;
    }

    public String getPartnerLoanId() {
        return partnerLoanId;
    }

    public void setPartnerLoanId(String partnerLoanId) {
        this.partnerLoanId = partnerLoanId;
    }
}
