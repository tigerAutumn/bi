package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * Author:      shh
 * Date:        2018/3/6
 * Description: 7贷借款服务费率查询 出参
 */
public class ResMsg_AgreementVersion_QuerySevenLoanServiceRate extends ResMsg {

    private static final long serialVersionUID = 1575782391733772788L;

    private String sevenLoanServiceRate;

    public String getSevenLoanServiceRate() {
        return sevenLoanServiceRate;
    }

    public void setSevenLoanServiceRate(String sevenLoanServiceRate) {
        this.sevenLoanServiceRate = sevenLoanServiceRate;
    }
}
