package com.pinting.business.accounting.loan.model;

/**
 * Created by babyshark on 2016/8/31.
 */
public class FinanceRepayInfo {
    private Integer loanId;//借款编号
    private Integer repayScheduleId;//还款计划编号
    private Integer repaySerial;//期次

    public Integer getRepayScheduleId() {
        return repayScheduleId;
    }

    public void setRepayScheduleId(Integer repayScheduleId) {
        this.repayScheduleId = repayScheduleId;
    }

    public Integer getLoanId() {
        return loanId;
    }

    public void setLoanId(Integer loanId) {
        this.loanId = loanId;
    }

    public Integer getRepaySerial() {
        return repaySerial;
    }

    public void setRepaySerial(Integer repaySerial) {
        this.repaySerial = repaySerial;
    }
}
