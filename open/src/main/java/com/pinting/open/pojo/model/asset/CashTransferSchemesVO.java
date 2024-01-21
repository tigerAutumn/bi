package com.pinting.open.pojo.model.asset;

import java.util.Date;

/**
 * 回款计划VO
 * Created by shh on 2017/3/30.
 */
public class CashTransferSchemesVO {

    /* 计划还款日期 */
    private String planDate;

    /* 未格式化的计划还款日期 */
    private Date basePlanDate;

    /* 未还款的计划金额 */
    private Double planTotalInit;

    /* 已还款的计划金额 */
    private Double planTotalRepaied;

    /* 回款中的计划金额 */
    private Double planTotalRepaying;

    /* 计划回款总金额 */
    private Double repayScheduleTotalAmount;

    /* 待回款金额 */
    private Double receivableAmount;

    /* 回款状态 */
    private String repayStatus;

    public String getPlanDate() {
        return planDate;
    }

    public void setPlanDate(String planDate) {
        this.planDate = planDate;
    }

    public Date getBasePlanDate() {
        return basePlanDate;
    }

    public void setBasePlanDate(Date basePlanDate) {
        this.basePlanDate = basePlanDate;
    }

    public Double getPlanTotalInit() {
        return planTotalInit;
    }

    public void setPlanTotalInit(Double planTotalInit) {
        this.planTotalInit = planTotalInit;
    }

    public Double getPlanTotalRepaied() {
        return planTotalRepaied;
    }

    public void setPlanTotalRepaied(Double planTotalRepaied) {
        this.planTotalRepaied = planTotalRepaied;
    }

    public Double getPlanTotalRepaying() {
        return planTotalRepaying;
    }

    public void setPlanTotalRepaying(Double planTotalRepaying) {
        this.planTotalRepaying = planTotalRepaying;
    }

    public Double getRepayScheduleTotalAmount() {
        return repayScheduleTotalAmount;
    }

    public void setRepayScheduleTotalAmount(Double repayScheduleTotalAmount) {
        this.repayScheduleTotalAmount = repayScheduleTotalAmount;
    }

    public Double getReceivableAmount() {
        return receivableAmount;
    }

    public void setReceivableAmount(Double receivableAmount) {
        this.receivableAmount = receivableAmount;
    }

    public String getRepayStatus() {
        return repayStatus;
    }

    public void setRepayStatus(String repayStatus) {
        this.repayStatus = repayStatus;
    }
}
