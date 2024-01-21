package com.pinting.business.accounting.loan.model;

import java.util.List;

/**
 * Created by babyshark on 2017/4/6.
 */
public class Repay2DepTargetReq extends DepBaseReq {
    List<DepRepaySchedule> depRepaySchedules;//最大500条

    public List<DepRepaySchedule> getDepRepaySchedules() {
        return depRepaySchedules;
    }

    public void setDepRepaySchedules(List<DepRepaySchedule> depRepaySchedules) {
        this.depRepaySchedules = depRepaySchedules;
    }
}
