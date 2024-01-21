package com.pinting.business.model.vo;

import java.util.Date;

/**
 * Created by 剑钊 on 2016/11/14.
 */
public class LoanVO {

    private Integer loanId;

    private Integer lnUserId;

    private String partner;

    private Double loanAmount;

    /**
     * 未还本金
     */
    private Double noReturnAmount;

    /**
     * 总服务费
     */
    private Double totalServiceFee;

    /**
     * 未还服务费
     */
    private Double noReturnServiceFee;

    /**
     * 期数
     */
    private Integer period;

    /**
     * 未还期数
     */
    private Integer noReturnPeriod;

    /**
     * 逾期期数
     */
    private Integer latePeriod;

    /**
     * 滞纳金
     */
    private Double lateFee;

    /**
     * 借款申请时间
     */
    private Date applyTime;

    /**
     * 状态
     */
    private String status;

    public Integer getLoanId() {
        return loanId;
    }

    public void setLoanId(Integer loanId) {
        this.loanId = loanId;
    }

    public Integer getLnUserId() {
        return lnUserId;
    }

    public void setLnUserId(Integer lnUserId) {
        this.lnUserId = lnUserId;
    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public Double getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(Double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public Double getNoReturnAmount() {

        if(noReturnAmount!=null) {
            return noReturnAmount;
        }else {
            return 0d;
        }
    }

    public void setNoReturnAmount(Double noReturnAmount) {
        this.noReturnAmount = noReturnAmount;
    }

    public Double getTotalServiceFee() {
        return totalServiceFee;
    }

    public void setTotalServiceFee(Double totalServiceFee) {
        this.totalServiceFee = totalServiceFee;
    }

    public Double getNoReturnServiceFee() {
        if(noReturnServiceFee!=null) {
            return noReturnServiceFee;
        }else {
            return 0d;
        }
    }

    public void setNoReturnServiceFee(Double noReturnServiceFee) {
        this.noReturnServiceFee = noReturnServiceFee;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public Integer getNoReturnPeriod() {

        if(noReturnPeriod!=null) {
            return noReturnPeriod;
        }else {
            return 0;
        }
    }

    public void setNoReturnPeriod(Integer noReturnPeriod) {
        this.noReturnPeriod = noReturnPeriod;
    }

    public Integer getLatePeriod() {
        if(latePeriod!=null) {
            return latePeriod;
        }else {
            return 0;
        }
    }

    public void setLatePeriod(Integer latePeriod) {
        this.latePeriod = latePeriod;
    }


    public Double getLateFee() {
        return lateFee;
    }

    public void setLateFee(Double lateFee) {
        this.lateFee = lateFee;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
