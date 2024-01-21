package com.pinting.business.accounting.loan.model;

/**
 * 借款金额匹配控制参数定义对象
 */
public class LoanMatchInvestorRangeVO {

    private Double oneLevelMatchAmount = 10000d; // 第一级匹配最小金额, 默认10000
    private Double twoLevelMatchAmount = 1000d; // 第二级匹配最小金额， 默认1000
    private Double threeLevelMatchAmount = 20d; // 第三级匹配最小金额， 默认20
    private int redisFlagExpTime = 5 * 60; // redis标识失效时间 默认 300秒
    private int redisLevelQueueCount = 100; // redis区间队列记录条数 默认 100条
    private Double matchMaxAmountNormal = 10000d; // 普通用户最大可匹配金额 默认10000元
    private Double matchMaxAmountQianBao = 5000d; // 钱报用户最大可匹配金额 默认5000元
    private Boolean isPriorityUseFree = false; // 匹配是否优先支持Free自由站岗户

    public Double getOneLevelMatchAmount() {
        return oneLevelMatchAmount;
    }

    public void setOneLevelMatchAmount(Double oneLevelMatchAmount) {
        this.oneLevelMatchAmount = oneLevelMatchAmount;
    }

    public Double getTwoLevelMatchAmount() {
        return twoLevelMatchAmount;
    }

    public void setTwoLevelMatchAmount(Double twoLevelMatchAmount) {
        this.twoLevelMatchAmount = twoLevelMatchAmount;
    }

    public Double getThreeLevelMatchAmount() {
        return threeLevelMatchAmount;
    }

    public void setThreeLevelMatchAmount(Double threeLevelMatchAmount) {
        this.threeLevelMatchAmount = threeLevelMatchAmount;
    }

    public int getRedisFlagExpTime() {
        return redisFlagExpTime;
    }

    public void setRedisFlagExpTime(int redisFlagExpTime) {
        this.redisFlagExpTime = redisFlagExpTime;
    }

    public int getRedisLevelQueueCount() {
        return redisLevelQueueCount;
    }

    public void setRedisLevelQueueCount(int redisLevelQueueCount) {
        this.redisLevelQueueCount = redisLevelQueueCount;
    }

    public Double getMatchMaxAmountNormal() {
        return matchMaxAmountNormal;
    }

    public void setMatchMaxAmountNormal(Double matchMaxAmountNormal) {
        this.matchMaxAmountNormal = matchMaxAmountNormal;
    }

    public Double getMatchMaxAmountQianBao() {
        return matchMaxAmountQianBao;
    }

    public void setMatchMaxAmountQianBao(Double matchMaxAmountQianBao) {
        this.matchMaxAmountQianBao = matchMaxAmountQianBao;
    }

    public Boolean getPriorityUseFree() {
        return isPriorityUseFree;
    }

    public void setPriorityUseFree(Boolean priorityUseFree) {
        isPriorityUseFree = priorityUseFree;
    }
}
