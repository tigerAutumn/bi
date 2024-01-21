package com.pinting.business.model.dto;

import com.pinting.business.model.vo.PageInfoObject;

import java.util.Date;

/**
 * Created by 剑钊 on 2016/11/14.
 */
public class LoanDTO extends PageInfoObject{

    private String loanid;

    private Long loanId;

    private Long lnUserId;

    private String lnUserid;
    /**
     * 提现申请起始时间
     */
    private Date loanTimeStart;

    /**
     * 提现申请截至时间
     */
    private Date loanTimeEnd;

    private String partner;

    /**
     * 逾期期数开始值
     */
    private String lateStart;

    /**
     * 逾期期数结束值
     */
    private String lateEnd;

    /**
     * 提现状态
     */
    private String status;

    /**
     * 借款本金开始值
     */
    private String loanStart;

    /**
     * 借款本金结束值
     */
    private String loanEnd;

    private Double sLoan;

    private Double eLoan;

    /**
     * 未还本金开始值
     */
    private String noReturnLoanStart;

    /**
     * 未还本金结束值
     */
    private String noReturnLoanEnd;

    private Double sNoReturn;

    private Double eNoReturn;

    public Long getLoanId() {
        return loanId;
    }

    public void setLoanId(Long loanId) {
        this.loanId = loanId;
    }

    public Long getLnUserId() {
        return lnUserId;
    }

    public void setLnUserId(Long lnUserId) {
        this.lnUserId = lnUserId;
    }

    public Date getLoanTimeStart() {
        return loanTimeStart;
    }

    public void setLoanTimeStart(Date loanTimeStart) {
        this.loanTimeStart = loanTimeStart;
    }

    public Date getLoanTimeEnd() {
        return loanTimeEnd;
    }

    public void setLoanTimeEnd(Date loanTimeEnd) {
        this.loanTimeEnd = loanTimeEnd;
    }

    public String getLoanStart() {
        return loanStart;
    }

    public void setLoanStart(String loanStart) {
        this.loanStart = loanStart;
    }

    public String getLoanEnd() {
        return loanEnd;
    }

    public void setLoanEnd(String loanEnd) {
        this.loanEnd = loanEnd;
    }

    public Double getsLoan() {
        return sLoan;
    }

    public void setsLoan(Double sLoan) {
        this.sLoan = sLoan;
    }

    public Double geteLoan() {
        return eLoan;
    }

    public void seteLoan(Double eLoan) {
        this.eLoan = eLoan;
    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public String getLateStart() {
        return lateStart;
    }

    public String getLateEnd() {
        return lateEnd;
    }

    public void setLateStart(String lateStart) {
        this.lateStart = lateStart;
    }

    public void setLateEnd(String lateEnd) {
        this.lateEnd = lateEnd;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLoanid() {
        return loanid;
    }

    public void setLoanid(String loanid) {
        this.loanid = loanid;
    }

    public String getLnUserid() {
        return lnUserid;
    }

    public void setLnUserid(String lnUserid) {
        this.lnUserid = lnUserid;
    }

    public String getNoReturnLoanStart() {
        return noReturnLoanStart;
    }

    public void setNoReturnLoanStart(String noReturnLoanStart) {
        this.noReturnLoanStart = noReturnLoanStart;
    }

    public String getNoReturnLoanEnd() {
        return noReturnLoanEnd;
    }

    public void setNoReturnLoanEnd(String noReturnLoanEnd) {
        this.noReturnLoanEnd = noReturnLoanEnd;
    }

    public Double getsNoReturn() {
        return sNoReturn;
    }

    public void setsNoReturn(Double sNoReturn) {
        this.sNoReturn = sNoReturn;
    }

    public Double geteNoReturn() {
        return eNoReturn;
    }

    public void seteNoReturn(Double eNoReturn) {
        this.eNoReturn = eNoReturn;
    }
}
