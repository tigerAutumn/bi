package com.pinting.business.accounting.loan.model;

import com.pinting.business.accounting.loan.enums.PartnerEnum;

/**
 * Created by babyshark on 2017/4/4.
 */
public class DepBaseReq {
    private PartnerEnum partnerEnum;

    public PartnerEnum getPartnerEnum() {
        return partnerEnum;
    }

    public void setPartnerEnum(PartnerEnum partnerEnum) {
        this.partnerEnum = partnerEnum;
    }
}
