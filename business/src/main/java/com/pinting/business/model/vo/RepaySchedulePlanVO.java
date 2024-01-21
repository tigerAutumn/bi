package com.pinting.business.model.vo;

import java.util.Date;

/**
 * 管理台 还款计划数据
 * Created by shh on 2016/11/8 17:21.
 */
public class RepaySchedulePlanVO extends PageInfoObject {

    /** 借款id */
    private Integer loanId;
    /** 期数 */
    private Integer serialId;
    /** 合约还款日 */
    private Date planDate;
    /** 应还本金 */
    private Double shouldPrincipal;
    /** 应还利息 */
    private Double shouldServiceCharges;
    /** 逾期滞纳金 */
    private Double overdueManagementFee;
    /** 应还总额 */
    private Double planTotal;
    /** 实际还款额 */
    private Double doneTotal;
    /** 实际还款时间 */
    private Date doneTime;
    /** 还款状态 */
    private String status;

    public Integer getLoanId() {
        return loanId;
    }

    public void setLoanId(Integer loanId) {
        this.loanId = loanId;
    }

    public Integer getSerialId() {
        return serialId;
    }

    public void setSerialId(Integer serialId) {
        this.serialId = serialId;
    }

    public Date getPlanDate() {
        return planDate;
    }

    public void setPlanDate(Date planDate) {
        this.planDate = planDate;
    }

    public Double getShouldPrincipal() {
        return shouldPrincipal;
    }

    public void setShouldPrincipal(Double shouldPrincipal) {
        this.shouldPrincipal = shouldPrincipal;
    }

    public Double getShouldServiceCharges() {
        return shouldServiceCharges;
    }

    public void setShouldServiceCharges(Double shouldServiceCharges) {
        this.shouldServiceCharges = shouldServiceCharges;
    }

    public Double getOverdueManagementFee() {
        return overdueManagementFee;
    }

    public void setOverdueManagementFee(Double overdueManagementFee) {
        this.overdueManagementFee = overdueManagementFee;
    }

    public Double getPlanTotal() {
        return planTotal;
    }

    public void setPlanTotal(Double planTotal) {
        this.planTotal = planTotal;
    }

    public Double getDoneTotal() {
        return doneTotal;
    }

    public void setDoneTotal(Double doneTotal) {
        this.doneTotal = doneTotal;
    }

    public Date getDoneTime() {
        return doneTime;
    }

    public void setDoneTime(Date doneTime) {
        this.doneTime = doneTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
