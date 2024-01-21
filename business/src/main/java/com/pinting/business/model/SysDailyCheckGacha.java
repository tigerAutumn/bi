package com.pinting.business.model;

import java.util.Date;

public class SysDailyCheckGacha {
    private Integer id;

    private Date checkDate;

    private String channel;

    private Double balanceWithdrawTotalAmt;

    private Double balanceWithdrawSuccAmt;

    private Double balanceWithdrawFailAmt;

    private Double balanceWithdrawProcessAmt;

    private Integer balanceWithdrawTotalCount;

    private Integer balanceWithdrawSuccCount;

    private Integer balanceWithdrawFailCount;

    private Integer balanceWithdrawProcessCount;

    private Double bonusWithdrawTotalAmt;

    private Double bonusWithdrawSuccAmt;

    private Double bonusWithdrawFailAmt;

    private Double bonusWithdrawProcessAmt;

    private Integer bonusWithdrawTotalCount;

    private Integer bonusWithdrawSuccCount;

    private Integer bonusWithdrawFailCount;

    private Integer bonusWithdrawProcessCount;

    private Double sysRevenueAmt;

    private Double sysRepeatRevenueAmt;

    private Double baofoo2HfbankTotalAmt;

    private Double baofoo2HfbankSuccAmt;

    private Double baofoo2HfbankFailAmt;

    private Double baofoo2HfbankProcessAmt;

    private Integer baofoo2HfbankTotalCount;

    private Integer baofoo2HfbankSuccCount;

    private Integer baofoo2HfbankFailCount;

    private Integer baofoo2HfbankProcessCount;

    private Double preDepositionBackAmt;

    private Double depositionRepayTotalAmt;

    private Double depositionRepaySuccAmt;

    private Double depositionRepayFailAmt;

    private Double depositionRepayProcessAmt;

    private Integer depositionRepayTotalCount;

    private Integer depositionRepaySuccCount;

    private Integer depositionRepayFailCount;

    private Integer depositionRepayProcessCount;

    private Double depositionRepayRepeatAmt;

    private Integer depositionRepayRepeatCount;

    private Double depositionCompensateAmt;

    private Double depostionCompensateSuccAmt;

    private Double depositionCompensateRepeatAmt;

    private Double depositionCompensateFailAmt;

    private Integer depositionCompensateCount;

    private Integer depositionCompensateSuccCount;

    private Integer depositionCompensateRepeatCount;

    private Integer depositionCompensateFailCount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel == null ? null : channel.trim();
    }

    public Double getBalanceWithdrawTotalAmt() {
        return balanceWithdrawTotalAmt;
    }

    public void setBalanceWithdrawTotalAmt(Double balanceWithdrawTotalAmt) {
        this.balanceWithdrawTotalAmt = balanceWithdrawTotalAmt;
    }

    public Double getBalanceWithdrawSuccAmt() {
        return balanceWithdrawSuccAmt;
    }

    public void setBalanceWithdrawSuccAmt(Double balanceWithdrawSuccAmt) {
        this.balanceWithdrawSuccAmt = balanceWithdrawSuccAmt;
    }

    public Double getBalanceWithdrawFailAmt() {
        return balanceWithdrawFailAmt;
    }

    public void setBalanceWithdrawFailAmt(Double balanceWithdrawFailAmt) {
        this.balanceWithdrawFailAmt = balanceWithdrawFailAmt;
    }

    public Double getBalanceWithdrawProcessAmt() {
        return balanceWithdrawProcessAmt;
    }

    public void setBalanceWithdrawProcessAmt(Double balanceWithdrawProcessAmt) {
        this.balanceWithdrawProcessAmt = balanceWithdrawProcessAmt;
    }

    public Integer getBalanceWithdrawTotalCount() {
        return balanceWithdrawTotalCount;
    }

    public void setBalanceWithdrawTotalCount(Integer balanceWithdrawTotalCount) {
        this.balanceWithdrawTotalCount = balanceWithdrawTotalCount;
    }

    public Integer getBalanceWithdrawSuccCount() {
        return balanceWithdrawSuccCount;
    }

    public void setBalanceWithdrawSuccCount(Integer balanceWithdrawSuccCount) {
        this.balanceWithdrawSuccCount = balanceWithdrawSuccCount;
    }

    public Integer getBalanceWithdrawFailCount() {
        return balanceWithdrawFailCount;
    }

    public void setBalanceWithdrawFailCount(Integer balanceWithdrawFailCount) {
        this.balanceWithdrawFailCount = balanceWithdrawFailCount;
    }

    public Integer getBalanceWithdrawProcessCount() {
        return balanceWithdrawProcessCount;
    }

    public void setBalanceWithdrawProcessCount(Integer balanceWithdrawProcessCount) {
        this.balanceWithdrawProcessCount = balanceWithdrawProcessCount;
    }

    public Double getBonusWithdrawTotalAmt() {
        return bonusWithdrawTotalAmt;
    }

    public void setBonusWithdrawTotalAmt(Double bonusWithdrawTotalAmt) {
        this.bonusWithdrawTotalAmt = bonusWithdrawTotalAmt;
    }

    public Double getBonusWithdrawSuccAmt() {
        return bonusWithdrawSuccAmt;
    }

    public void setBonusWithdrawSuccAmt(Double bonusWithdrawSuccAmt) {
        this.bonusWithdrawSuccAmt = bonusWithdrawSuccAmt;
    }

    public Double getBonusWithdrawFailAmt() {
        return bonusWithdrawFailAmt;
    }

    public void setBonusWithdrawFailAmt(Double bonusWithdrawFailAmt) {
        this.bonusWithdrawFailAmt = bonusWithdrawFailAmt;
    }

    public Double getBonusWithdrawProcessAmt() {
        return bonusWithdrawProcessAmt;
    }

    public void setBonusWithdrawProcessAmt(Double bonusWithdrawProcessAmt) {
        this.bonusWithdrawProcessAmt = bonusWithdrawProcessAmt;
    }

    public Integer getBonusWithdrawTotalCount() {
        return bonusWithdrawTotalCount;
    }

    public void setBonusWithdrawTotalCount(Integer bonusWithdrawTotalCount) {
        this.bonusWithdrawTotalCount = bonusWithdrawTotalCount;
    }

    public Integer getBonusWithdrawSuccCount() {
        return bonusWithdrawSuccCount;
    }

    public void setBonusWithdrawSuccCount(Integer bonusWithdrawSuccCount) {
        this.bonusWithdrawSuccCount = bonusWithdrawSuccCount;
    }

    public Integer getBonusWithdrawFailCount() {
        return bonusWithdrawFailCount;
    }

    public void setBonusWithdrawFailCount(Integer bonusWithdrawFailCount) {
        this.bonusWithdrawFailCount = bonusWithdrawFailCount;
    }

    public Integer getBonusWithdrawProcessCount() {
        return bonusWithdrawProcessCount;
    }

    public void setBonusWithdrawProcessCount(Integer bonusWithdrawProcessCount) {
        this.bonusWithdrawProcessCount = bonusWithdrawProcessCount;
    }

    public Double getSysRevenueAmt() {
        return sysRevenueAmt;
    }

    public void setSysRevenueAmt(Double sysRevenueAmt) {
        this.sysRevenueAmt = sysRevenueAmt;
    }

    public Double getSysRepeatRevenueAmt() {
        return sysRepeatRevenueAmt;
    }

    public void setSysRepeatRevenueAmt(Double sysRepeatRevenueAmt) {
        this.sysRepeatRevenueAmt = sysRepeatRevenueAmt;
    }

    public Double getBaofoo2HfbankTotalAmt() {
        return baofoo2HfbankTotalAmt;
    }

    public void setBaofoo2HfbankTotalAmt(Double baofoo2HfbankTotalAmt) {
        this.baofoo2HfbankTotalAmt = baofoo2HfbankTotalAmt;
    }

    public Double getBaofoo2HfbankSuccAmt() {
        return baofoo2HfbankSuccAmt;
    }

    public void setBaofoo2HfbankSuccAmt(Double baofoo2HfbankSuccAmt) {
        this.baofoo2HfbankSuccAmt = baofoo2HfbankSuccAmt;
    }

    public Double getBaofoo2HfbankFailAmt() {
        return baofoo2HfbankFailAmt;
    }

    public void setBaofoo2HfbankFailAmt(Double baofoo2HfbankFailAmt) {
        this.baofoo2HfbankFailAmt = baofoo2HfbankFailAmt;
    }

    public Double getBaofoo2HfbankProcessAmt() {
        return baofoo2HfbankProcessAmt;
    }

    public void setBaofoo2HfbankProcessAmt(Double baofoo2HfbankProcessAmt) {
        this.baofoo2HfbankProcessAmt = baofoo2HfbankProcessAmt;
    }

    public Integer getBaofoo2HfbankTotalCount() {
        return baofoo2HfbankTotalCount;
    }

    public void setBaofoo2HfbankTotalCount(Integer baofoo2HfbankTotalCount) {
        this.baofoo2HfbankTotalCount = baofoo2HfbankTotalCount;
    }

    public Integer getBaofoo2HfbankSuccCount() {
        return baofoo2HfbankSuccCount;
    }

    public void setBaofoo2HfbankSuccCount(Integer baofoo2HfbankSuccCount) {
        this.baofoo2HfbankSuccCount = baofoo2HfbankSuccCount;
    }

    public Integer getBaofoo2HfbankFailCount() {
        return baofoo2HfbankFailCount;
    }

    public void setBaofoo2HfbankFailCount(Integer baofoo2HfbankFailCount) {
        this.baofoo2HfbankFailCount = baofoo2HfbankFailCount;
    }

    public Integer getBaofoo2HfbankProcessCount() {
        return baofoo2HfbankProcessCount;
    }

    public void setBaofoo2HfbankProcessCount(Integer baofoo2HfbankProcessCount) {
        this.baofoo2HfbankProcessCount = baofoo2HfbankProcessCount;
    }

    public Double getPreDepositionBackAmt() {
        return preDepositionBackAmt;
    }

    public void setPreDepositionBackAmt(Double preDepositionBackAmt) {
        this.preDepositionBackAmt = preDepositionBackAmt;
    }

    public Double getDepositionRepayTotalAmt() {
        return depositionRepayTotalAmt;
    }

    public void setDepositionRepayTotalAmt(Double depositionRepayTotalAmt) {
        this.depositionRepayTotalAmt = depositionRepayTotalAmt;
    }

    public Double getDepositionRepaySuccAmt() {
        return depositionRepaySuccAmt;
    }

    public void setDepositionRepaySuccAmt(Double depositionRepaySuccAmt) {
        this.depositionRepaySuccAmt = depositionRepaySuccAmt;
    }

    public Double getDepositionRepayFailAmt() {
        return depositionRepayFailAmt;
    }

    public void setDepositionRepayFailAmt(Double depositionRepayFailAmt) {
        this.depositionRepayFailAmt = depositionRepayFailAmt;
    }

    public Double getDepositionRepayProcessAmt() {
        return depositionRepayProcessAmt;
    }

    public void setDepositionRepayProcessAmt(Double depositionRepayProcessAmt) {
        this.depositionRepayProcessAmt = depositionRepayProcessAmt;
    }

    public Integer getDepositionRepayTotalCount() {
        return depositionRepayTotalCount;
    }

    public void setDepositionRepayTotalCount(Integer depositionRepayTotalCount) {
        this.depositionRepayTotalCount = depositionRepayTotalCount;
    }

    public Integer getDepositionRepaySuccCount() {
        return depositionRepaySuccCount;
    }

    public void setDepositionRepaySuccCount(Integer depositionRepaySuccCount) {
        this.depositionRepaySuccCount = depositionRepaySuccCount;
    }

    public Integer getDepositionRepayFailCount() {
        return depositionRepayFailCount;
    }

    public void setDepositionRepayFailCount(Integer depositionRepayFailCount) {
        this.depositionRepayFailCount = depositionRepayFailCount;
    }

    public Integer getDepositionRepayProcessCount() {
        return depositionRepayProcessCount;
    }

    public void setDepositionRepayProcessCount(Integer depositionRepayProcessCount) {
        this.depositionRepayProcessCount = depositionRepayProcessCount;
    }

    public Double getDepositionRepayRepeatAmt() {
        return depositionRepayRepeatAmt;
    }

    public void setDepositionRepayRepeatAmt(Double depositionRepayRepeatAmt) {
        this.depositionRepayRepeatAmt = depositionRepayRepeatAmt;
    }

    public Integer getDepositionRepayRepeatCount() {
        return depositionRepayRepeatCount;
    }

    public void setDepositionRepayRepeatCount(Integer depositionRepayRepeatCount) {
        this.depositionRepayRepeatCount = depositionRepayRepeatCount;
    }

    public Double getDepositionCompensateAmt() {
        return depositionCompensateAmt;
    }

    public void setDepositionCompensateAmt(Double depositionCompensateAmt) {
        this.depositionCompensateAmt = depositionCompensateAmt;
    }

    public Double getDepostionCompensateSuccAmt() {
        return depostionCompensateSuccAmt;
    }

    public void setDepostionCompensateSuccAmt(Double depostionCompensateSuccAmt) {
        this.depostionCompensateSuccAmt = depostionCompensateSuccAmt;
    }

    public Double getDepositionCompensateRepeatAmt() {
        return depositionCompensateRepeatAmt;
    }

    public void setDepositionCompensateRepeatAmt(Double depositionCompensateRepeatAmt) {
        this.depositionCompensateRepeatAmt = depositionCompensateRepeatAmt;
    }

    public Double getDepositionCompensateFailAmt() {
        return depositionCompensateFailAmt;
    }

    public void setDepositionCompensateFailAmt(Double depositionCompensateFailAmt) {
        this.depositionCompensateFailAmt = depositionCompensateFailAmt;
    }

    public Integer getDepositionCompensateCount() {
        return depositionCompensateCount;
    }

    public void setDepositionCompensateCount(Integer depositionCompensateCount) {
        this.depositionCompensateCount = depositionCompensateCount;
    }

    public Integer getDepositionCompensateSuccCount() {
        return depositionCompensateSuccCount;
    }

    public void setDepositionCompensateSuccCount(Integer depositionCompensateSuccCount) {
        this.depositionCompensateSuccCount = depositionCompensateSuccCount;
    }

    public Integer getDepositionCompensateRepeatCount() {
        return depositionCompensateRepeatCount;
    }

    public void setDepositionCompensateRepeatCount(Integer depositionCompensateRepeatCount) {
        this.depositionCompensateRepeatCount = depositionCompensateRepeatCount;
    }

    public Integer getDepositionCompensateFailCount() {
        return depositionCompensateFailCount;
    }

    public void setDepositionCompensateFailCount(Integer depositionCompensateFailCount) {
        this.depositionCompensateFailCount = depositionCompensateFailCount;
    }
    
}