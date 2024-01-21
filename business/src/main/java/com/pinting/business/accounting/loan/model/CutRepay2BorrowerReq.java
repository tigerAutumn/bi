package com.pinting.business.accounting.loan.model;

import java.util.List;

/**
 * Created by babyshark on 2017/4/5.
 */
public class CutRepay2BorrowerReq extends DepBaseReq{
    private Double amount;//总金额
    private List<DepRepaySchedule> depRepaySchedules;//存管账单信息

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public List<DepRepaySchedule> getDepRepaySchedules() {
        return depRepaySchedules;
    }

    public void setDepRepaySchedules(List<DepRepaySchedule> depRepaySchedules) {
        this.depRepaySchedules = depRepaySchedules;
    }
}
