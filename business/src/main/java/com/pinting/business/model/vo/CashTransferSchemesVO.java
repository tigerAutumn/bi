package com.pinting.business.model.vo;

import java.util.Date;

/**
 * 回款计划VO
 * Created by shh on 2017/3/30.
 */
public class CashTransferSchemesVO extends PageInfoObject {

	private static final long serialVersionUID = 1254850321164068090L;

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

    /*用户ID*/
    private Integer userId;

    /*往期回款时间*/
    private String dateTime;

    /*回款详情开始条数*/
    private Integer startNumPage;

    /*回款详情结束条数*/
    private Integer endNumPerPage;

    /*回款详情时间*/
    private String detailsTime;
    
    public Integer getStartNumPage() {
        return startNumPage;
    }

    public void setStartNumPage(Integer startNumPage) {
        this.startNumPage = startNumPage;
    }

    public Integer getEndNumPerPage() {
        return endNumPerPage;
    }

    public void setEndNumPerPage(Integer endNumPerPage) {
        this.endNumPerPage = endNumPerPage;
    }

    public String getDetailsTime() {
        return detailsTime;
    }

    public void setDetailsTime(String detailsTime) {
        this.detailsTime = detailsTime;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

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
