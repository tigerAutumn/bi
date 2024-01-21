package com.pinting.gateway.zsd.in.model;

import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

/**
 * Author:      shh
 * Date:        2017/8/30
 * Description: 查询银行卡限额 响应信息
 */
public class ZsdBankLimitRsp extends BaseResModel {

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
