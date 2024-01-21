package com.pinting.business.model.vo;

import com.pinting.business.model.LnRepaySchedule;

import java.util.Date;

/**
 * Created by babyshark on 2016/8/31.
 */
public class RepayScheduleVO extends LnRepaySchedule{

    private Integer lnUserId;

    private String bgwOrderNo;

    private String repayPayStatus;

    private Double principal;

    /* 科目编码 */
    private String subjectCode;

    /* 科目应还金额 */
    private Double planAmount;

    /* 借款人姓名 */
    private String userName;

    /* 借款时间 */
    private Date loanTime;

    public Double getPrincipal() {
        return principal;
    }

    public void setPrincipal(Double principal) {
        this.principal = principal;
    }

    public String getRepayPayStatus() {
        return repayPayStatus;
    }

    public void setRepayPayStatus(String repayPayStatus) {
        this.repayPayStatus = repayPayStatus;
    }

    public Integer getLnUserId() {
        return lnUserId;
    }

    public void setLnUserId(Integer lnUserId) {
        this.lnUserId = lnUserId;
    }

    public String getBgwOrderNo() {
        return bgwOrderNo;
    }

    public void setBgwOrderNo(String bgwOrderNo) {
        this.bgwOrderNo = bgwOrderNo;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public Double getPlanAmount() {
        return planAmount;
    }

    public void setPlanAmount(Double planAmount) {
        this.planAmount = planAmount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getLoanTime() {
        return loanTime;
    }

    public void setLoanTime(Date loanTime) {
        this.loanTime = loanTime;
    }
}
