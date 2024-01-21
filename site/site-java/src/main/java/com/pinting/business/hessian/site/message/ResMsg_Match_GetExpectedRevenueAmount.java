package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * 币港湾赞分期产品自动出借计划协议 等额本息计算期末预期收益数额  出参
 * Created by shh on 2016/9/29 12:13.
 */
public class ResMsg_Match_GetExpectedRevenueAmount extends ResMsg {

    private static final long serialVersionUID = 5614590619921417768L;

    /* 预期收益金额 */
    private Double incomeAmount;

    public Double getIncomeAmount() {
        return incomeAmount;
    }

    public void setIncomeAmount(Double incomeAmount) {
        this.incomeAmount = incomeAmount;
    }
}
