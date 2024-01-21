package com.pinting.gateway.bird.in.model;

/**
 * Created by 剑钊 on 2016/8/10.
 */
public class DailyLimitRes extends BaseResModel {

    /**
     * 各额度情况JSON
     */
    private String amounts;

    public String getAmounts() {
        return amounts;
    }

    public void setAmounts(String amounts) {
        this.amounts = amounts;
    }
}
