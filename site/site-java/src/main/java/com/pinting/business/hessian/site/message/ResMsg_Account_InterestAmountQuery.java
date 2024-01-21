package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * 根据子账户编号，查询产品购买成功后的加息收益金额 出参
 *
 * @author shh
 * @date 2018/8/2 15:09
 * @Copyright: 2018 BiGangWan.com Inc. All rights reserved.
 */
public class ResMsg_Account_InterestAmountQuery extends ResMsg {

    private static final long serialVersionUID = 7954312611787014641L;

    /* 加息收益 */
    private Double interestAmount;

    public Double getInterestAmount() {
        return interestAmount;
    }

    public void setInterestAmount(Double interestAmount) {
        this.interestAmount = interestAmount;
    }
}
