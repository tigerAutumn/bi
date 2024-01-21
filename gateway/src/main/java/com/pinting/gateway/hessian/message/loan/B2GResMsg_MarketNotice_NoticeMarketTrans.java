package com.pinting.gateway.hessian.message.loan;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * Created by 剑钊 on 2016/8/3.
 */
public class B2GResMsg_MarketNotice_NoticeMarketTrans extends ResMsg {

    private String responseTime;

    public String getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(String responseTime) {
        this.responseTime = responseTime;
    }
}
