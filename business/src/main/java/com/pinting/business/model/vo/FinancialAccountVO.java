/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.model.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 财务对账
 * @author HuanXiong
 * @version $Id: FinancialAccountVO.java, v 0.1 2016-2-15 上午11:15:30 HuanXiong Exp $
 */
public class FinancialAccountVO implements Serializable {

    /**  */
    private static final long serialVersionUID = -2209293447114598898L;
    
    private Integer subAccountId;
    
    private Integer userId;
    
    private String mobile;
    
    private String userName;
    
    private Integer term;
    
    private Double baseRate;    // 利率
    
    private Double balance;     // 购买金额
    
    private Integer needInterestDays;   // 应计息天数
    
    private String status;     // 产品状态
    
    private Date openTime;      // 购买时间
    
    private Date interestBeginDate; // 计息日期
    
    private Date returnMoneyDays;   // 到期提现日
    
    private String agentName;   // 渠道来源
    
    private Double dafyReturnBalance;   // 达飞利息回款
    
    private Double userInterestBalance; // 用户利息扣除
    
    private Double userBonusBalance;    // 用户奖励金扣除

    public Integer getSubAccountId() {
        return subAccountId;
    }

    public void setSubAccountId(Integer subAccountId) {
        this.subAccountId = subAccountId;
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

    public Integer getNeedInterestDays() {
        return needInterestDays;
    }

    public void setNeedInterestDays(Integer needInterestDays) {
        this.needInterestDays = needInterestDays;
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

    public Date getReturnMoneyDays() {
        return returnMoneyDays;
    }

    public void setReturnMoneyDays(Date returnMoneyDays) {
        this.returnMoneyDays = returnMoneyDays;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public Double getDafyReturnBalance() {
        return dafyReturnBalance;
    }

    public void setDafyReturnBalance(Double dafyReturnBalance) {
        this.dafyReturnBalance = dafyReturnBalance;
    }

    public Double getUserInterestBalance() {
        return userInterestBalance;
    }

    public void setUserInterestBalance(Double userInterestBalance) {
        this.userInterestBalance = userInterestBalance;
    }

    public Double getUserBonusBalance() {
        return userBonusBalance;
    }

    public void setUserBonusBalance(Double userBonusBalance) {
        this.userBonusBalance = userBonusBalance;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
