package com.pinting.business.model.vo;

import com.pinting.business.model.LnMarketGrantRecord;

/**
 * Created by 剑钊 on 2016/11/15.
 */
public class MarketingForCheckVO extends LnMarketGrantRecord{

    /**
     * 失败原因
     */
    private String errorReason;

    public String getErrorReason() {
        return errorReason;
    }

    public void setErrorReason(String errorReason) {
        this.errorReason = errorReason;
    }
}
