package com.pinting.business.model.vo;

import java.util.Date;

/**
 * Created by cyb on 2018/1/8.
 */
public class BorrowerInfoVO {
    private String userName;
    private String idCard;
    private String loanId;
    private String loanAmount;
    private String period;
    private Date loanTime;

    private String sex;
    private int age;
    private int historyOverdueTimes;
    private Double historyOverdueAmount;
    private Double currentOverdueAmount;
    private String partnerCode;

    /* 合作方业务标识 */
    private String partnerBusinessFlag;

    public String getPartnerCode() {
        return partnerCode;
    }

    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getHistoryOverdueTimes() {
        return historyOverdueTimes;
    }

    public void setHistoryOverdueTimes(int historyOverdueTimes) {
        this.historyOverdueTimes = historyOverdueTimes;
    }

    public Double getHistoryOverdueAmount() {
        return historyOverdueAmount;
    }

    public void setHistoryOverdueAmount(Double historyOverdueAmount) {
        this.historyOverdueAmount = historyOverdueAmount;
    }

    public Double getCurrentOverdueAmount() {
        return currentOverdueAmount;
    }

    public void setCurrentOverdueAmount(Double currentOverdueAmount) {
        this.currentOverdueAmount = currentOverdueAmount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public String getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(String loanAmount) {
        this.loanAmount = loanAmount;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public Date getLoanTime() {
        return loanTime;
    }

    public void setLoanTime(Date loanTime) {
        this.loanTime = loanTime;
    }

    public String getPartnerBusinessFlag() {
        return partnerBusinessFlag;
    }

    public void setPartnerBusinessFlag(String partnerBusinessFlag) {
        this.partnerBusinessFlag = partnerBusinessFlag;
    }
}
