package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * Created by cyb on 2018/2/23.
 */
public class ResMsg_DepGuide_Risk extends ResMsg {

    private static final long serialVersionUID = -7428896555534278354L;

    private String riskStatus; // 风险测评状态 测评过-yes；未测评-no；已过期-expire

    public String getRiskStatus() {
        return riskStatus;
    }

    public void setRiskStatus(String riskStatus) {
        this.riskStatus = riskStatus;
    }

}
