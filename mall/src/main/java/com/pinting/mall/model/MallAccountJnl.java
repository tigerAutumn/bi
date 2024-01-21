package com.pinting.mall.model;

import java.util.Date;

public class MallAccountJnl {
    private Integer id; // 积分商城用户余额流水表ID

    private Integer userId; // 用户编号ID

    private Integer accountId; // 用户积分账户余额表ID

    private Integer ruleId; //

    private String transType; // 交易类型

    private String transName; // 交易名称

    private Date transTime; // 交易时间

    private Long points; // 交易积分

    private Long beforeBalance; // 交易前余额

    private Long afterBalance; // 交易后余额

    private Long beforeAvaliableBalance; // 交易前可用余额

    private Long afterAvaliableBalance; // 交易后可用余额

    private Long beforeFreezeBalance; // 交易前冻结余额

    private Long afterFreezeBalance; // 交易后冻结余额

    private String note; // 交易类型归类:   INVEST - 投资奖励 ACTIVITY - 活动奖励 NEWER-新手任务 INVITE-邀请好友奖励 EXCHANGE - 积分商城兑换

    private Date createTime; // 创建时间

    private Date updateTime; // 更新时间

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getRuleId() {
        return ruleId;
    }

    public void setRuleId(Integer ruleId) {
        this.ruleId = ruleId;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public String getTransName() {
        return transName;
    }

    public void setTransName(String transName) {
        this.transName = transName;
    }

    public Date getTransTime() {
        return transTime;
    }

    public void setTransTime(Date transTime) {
        this.transTime = transTime;
    }

    public Long getPoints() {
        return points;
    }

    public void setPoints(Long points) {
        this.points = points;
    }

    public Long getBeforeBalance() {
        return beforeBalance;
    }

    public void setBeforeBalance(Long beforeBalance) {
        this.beforeBalance = beforeBalance;
    }

    public Long getAfterBalance() {
        return afterBalance;
    }

    public void setAfterBalance(Long afterBalance) {
        this.afterBalance = afterBalance;
    }

    public Long getBeforeAvaliableBalance() {
        return beforeAvaliableBalance;
    }

    public void setBeforeAvaliableBalance(Long beforeAvaliableBalance) {
        this.beforeAvaliableBalance = beforeAvaliableBalance;
    }

    public Long getAfterAvaliableBalance() {
        return afterAvaliableBalance;
    }

    public void setAfterAvaliableBalance(Long afterAvaliableBalance) {
        this.afterAvaliableBalance = afterAvaliableBalance;
    }

    public Long getBeforeFreezeBalance() {
        return beforeFreezeBalance;
    }

    public void setBeforeFreezeBalance(Long beforeFreezeBalance) {
        this.beforeFreezeBalance = beforeFreezeBalance;
    }

    public Long getAfterFreezeBalance() {
        return afterFreezeBalance;
    }

    public void setAfterFreezeBalance(Long afterFreezeBalance) {
        this.afterFreezeBalance = afterFreezeBalance;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}