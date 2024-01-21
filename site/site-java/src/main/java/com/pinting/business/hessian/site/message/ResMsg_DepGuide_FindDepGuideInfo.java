package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * Author:      cyb
 * Date:        2017/4/12
 * Description:
 */
public class ResMsg_DepGuide_FindDepGuideInfo extends ResMsg {

    private static final long serialVersionUID = 7143175722872942510L;

    /* 账户类型：DEP-存管账户；DOUBLE-双帐户；SIMPLE-普通账户 */
    private String accountType;

    /* 是否恒丰开户：NO_BIND_CARD-未绑卡；FAILED_BIND_HF-恒丰批量开户失败；WAIT_ACTIVATE-待激活；OPEN-已激活； */
    private String isOpened;

    /* 是否实名：YES-已实名；NO-未实名 */
    private String isBindName;

    private String riskStatus; // 风险测评状态 测评过-yes；未测评-no；已过期-expire

    public String getRiskStatus() {
        return riskStatus;
    }

    public void setRiskStatus(String riskStatus) {
        this.riskStatus = riskStatus;
    }

    public String getIsBindName() {
        return isBindName;
    }

    public void setIsBindName(String isBindName) {
        this.isBindName = isBindName;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getIsOpened() {
        return isOpened;
    }

    public void setIsOpened(String isOpened) {
        this.isOpened = isOpened;
    }

}
