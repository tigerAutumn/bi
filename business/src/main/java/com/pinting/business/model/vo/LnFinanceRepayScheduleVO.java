package com.pinting.business.model.vo;

import com.pinting.business.model.LnFinanceRepaySchedule;

/**
 * Created by babyshark on 2017/4/8.
 */
public class LnFinanceRepayScheduleVO extends LnFinanceRepaySchedule {
    private Integer userId;
    private String hfUserId;
    private Integer bsSubAccountId;

    public Integer getBsSubAccountId() {
        return bsSubAccountId;
    }

    public void setBsSubAccountId(Integer bsSubAccountId) {
        this.bsSubAccountId = bsSubAccountId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getHfUserId() {
        return hfUserId;
    }

    public void setHfUserId(String hfUserId) {
        this.hfUserId = hfUserId;
    }

    @Override
    public String toString() {
        return "LnFinanceRepayScheduleVO{" +
                "userId=" + userId +
                ", hfUserId='" + hfUserId + '\'' +
                ", bsSubAccountId=" + bsSubAccountId +
                "}" + "LnFinanceRepaySchedule{" +
                "id=" + getId() +
                ", relationId=" + getRelationId() +
                ", repaySerial=" + getRepaySerial() +
                ", planDate=" + getPlanDate() +
                ", planTotal=" + getPlanTotal() +
                ", planPrincipal=" + getPlanPrincipal() +
                ", planInterest=" + getPlanInterest() +
                ", planTransInterest=" + getPlanTransInterest() +
                ", planFee=" + getPlanFee() +
                ", diffAmount=" + getDiffAmount() +
                ", doneTime=" + getDoneTime() +
                ", status=" + getStatus() +
                ", createTime=" + getCreateTime() +
                ", updateTime=" + getUpdateTime() +
                '}';
    }
}
