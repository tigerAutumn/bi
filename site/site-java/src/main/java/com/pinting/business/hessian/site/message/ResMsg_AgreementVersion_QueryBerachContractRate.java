package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * Author:      shh
 * Date:        2018/3/2
 * Description: 购买的云贷、7贷理财产品提前终止违约金百分比查询 出参
 */
public class ResMsg_AgreementVersion_QueryBerachContractRate extends ResMsg {

    private static final long serialVersionUID = -3383574725271572762L;

    /* 违约金百分比 */
    private String berachContractRate;

    public String getBerachContractRate() {
        return berachContractRate;
    }

    public void setBerachContractRate(String berachContractRate) {
        this.berachContractRate = berachContractRate;
    }
}
