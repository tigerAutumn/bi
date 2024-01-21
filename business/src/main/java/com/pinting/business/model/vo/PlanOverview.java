package com.pinting.business.model.vo;

import java.io.Serializable;

/**
 * Created by cyb on 2018/3/12.
 */
public class PlanOverview implements Serializable {

    private static final long serialVersionUID = 5424369539570096397L;
    // 计划名称
    private String name;
    // 总成交额
    private String amount;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

}
