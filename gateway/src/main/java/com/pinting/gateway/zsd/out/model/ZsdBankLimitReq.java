package com.pinting.gateway.zsd.out.model;

import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

/**
 * Author:      shh
 * Date:        2017/8/30
 * Description: 推送银行卡限额 请求信息
 */
public class ZsdBankLimitReq extends BaseReqModel {

    /* 各银行限额 */
    @NotEmpty(message = "各银行限额")
    private List<BankLimit> limits;

    public List<BankLimit> getLimits() {
        return limits;
    }

    public void setLimits(List<BankLimit> limits) {
        this.limits = limits;
    }
}
