/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.model.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 销售应收查询VO
 * @author HuanXiong
 * @version $Id: SaleReceivableVO.java, v 0.1 2016-2-18 上午11:21:26 HuanXiong Exp $
 */
public class SaleReceivableVO implements Serializable {

    /**  */
    private static final long serialVersionUID = 2682795037511091092L;
    
    private Integer bsSubAccountId;
    
    private Integer userId;
    
    private String mobile;
    
    private String userName;
    
    private Integer term;
    
    private Integer interestDays;
    
    private Double baseRate;
    
    private Double balance;
    
    private String status;
    
    private Date openTime;
    
    private Date interestBeginDate;
    
    private Date lastFinishInterestDate;
    
    private String agentName;
    
    private Double totalInterestAmount;
    
    private Double userInterestAmount;
    
    private Double userBonusBalance;

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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getTerm() {
        return term;
    }

    public void setTerm(Integer term) {
        this.term = term;
    }

    public Integer getInterestDays() {
        return interestDays;
    }

    public void setInterestDays(Integer interestDays) {
        this.interestDays = interestDays;
    }

    public Double getBaseRate() {
        return baseRate;
    }

    public void setBaseRate(Double baseRate) {
        this.baseRate = baseRate;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getOpenTime() {
        return openTime;
    }

    public void setOpenTime(Date openTime) {
        this.openTime = openTime;
    }

    public Date getInterestBeginDate() {
        return interestBeginDate;
    }

    public void setInterestBeginDate(Date interestBeginDate) {
        this.interestBeginDate = interestBeginDate;
    }

    public Date getLastFinishInterestDate() {
        return lastFinishInterestDate;
    }

    public void setLastFinishInterestDate(Date lastFinishInterestDate) {
        this.lastFinishInterestDate = lastFinishInterestDate;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public Double getTotalInterestAmount() {
        return totalInterestAmount;
    }

    public void setTotalInterestAmount(Double totalInterestAmount) {
        this.totalInterestAmount = totalInterestAmount;
    }

    public Double getUserInterestAmount() {
        return userInterestAmount;
    }

    public void setUserInterestAmount(Double userInterestAmount) {
        this.userInterestAmount = userInterestAmount;
    }

    public Double getUserBonusBalance() {
        return userBonusBalance;
    }

    public void setUserBonusBalance(Double userBonusBalance) {
        this.userBonusBalance = userBonusBalance;
    }
    
}
