package com.pinting.open.pojo.response.more;

import com.pinting.open.base.response.Response;

/**
 * Created by cyb on 2018/2/23.
 */
public class RiskResponse extends Response {

    private String riskStatus; // 风险测评状态 测评过-yes；未测评-no；已过期-expire

    public String getRiskStatus() {
        return riskStatus;
    }

    public void setRiskStatus(String riskStatus) {
        this.riskStatus = riskStatus;
    }
}
