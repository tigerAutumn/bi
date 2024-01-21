package com.pinting.business.accounting.loan.model;

/**
 * Created by babyshark on 2017/4/7.
 */
public class Repay2InvestorReq extends DepBaseReq{
    private DepRepaySchedule depRepaySchedule;//标的及账单信息

    public DepRepaySchedule getDepRepaySchedule() {
        return depRepaySchedule;
    }

    public void setDepRepaySchedule(DepRepaySchedule depRepaySchedule) {
        this.depRepaySchedule = depRepaySchedule;
    }
}
