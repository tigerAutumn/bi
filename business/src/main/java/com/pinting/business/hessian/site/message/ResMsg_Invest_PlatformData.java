package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * 平台数据新增内容
 * Created by cyb on 2018/1/24.
 */
public class ResMsg_Invest_PlatformData extends ResMsg {

    private static final long serialVersionUID = -3948027273796125353L;

    private Double amount;

    private Integer times;

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getTimes() {
        return times;
    }

    public void setTimes(Integer times) {
        this.times = times;
    }
}
