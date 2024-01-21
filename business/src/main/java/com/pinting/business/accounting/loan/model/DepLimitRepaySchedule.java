package com.pinting.business.accounting.loan.model;

import com.pinting.business.model.LnDepositionRepaySchedule;

/**
 * Created by zhangbao on 2017/5/2.
 */
public class DepLimitRepaySchedule extends LnDepositionRepaySchedule {
    private String partnerCode;

    public String getPartnerCode() {
        return partnerCode;
    }

    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
    }
}
