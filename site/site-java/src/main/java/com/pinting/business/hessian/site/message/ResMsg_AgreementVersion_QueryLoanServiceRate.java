package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * Author:      shh
 * Date:        2018/3/6
 * Description: 云贷、7贷借款服务费率查询 出参
 */
public class ResMsg_AgreementVersion_QueryLoanServiceRate extends ResMsg {

    private static final long serialVersionUID = -6252768643774437208L;

    private String loanServiceRate;

    public String getLoanServiceRate() {
        return loanServiceRate;
    }

    public void setLoanServiceRate(String loanServiceRate) {
        this.loanServiceRate = loanServiceRate;
    }
}
