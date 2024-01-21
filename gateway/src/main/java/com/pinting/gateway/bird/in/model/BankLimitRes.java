package com.pinting.gateway.bird.in.model;

import com.pinting.gateway.hessian.message.loan.model.BankLimit;

import java.util.List;

/**
 * Created by 剑钊 on 2016/8/10.
 */
public class BankLimitRes extends BaseResModel {

    private List<BankLimit> limits;

    public List<BankLimit> getLimits() {
        return limits;
    }

    public void setLimits(List<BankLimit> limits) {
        this.limits = limits;
    }
}
