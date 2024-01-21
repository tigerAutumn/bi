package com.pinting.business.model.vo;

import java.io.Serializable;

/**
 * Author:      cyb
 * Date:        2017/2/3
 * Description: 元宵喜乐会首页信息
 */
public class LanternFestival2017DrawIndexVO implements Serializable {

    private static final long serialVersionUID = 2349664154899728780L;

    /* 开始时间 */
    private String startTime;

    /* 结束时间 */
    private String endTime;

    /* 摇一摇参与人数 */
    private Integer shakeNum;

    /* 总发出奖金 */
    private Double amount;

    /* 活动期间投资人数（灯笼总数） */
    private Integer investNum;

    /* 下一个中奖号码（灯笼号） */
    private String nextLuckInvestNum;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getShakeNum() {
        return shakeNum;
    }

    public void setShakeNum(Integer shakeNum) {
        this.shakeNum = shakeNum;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getInvestNum() {
        return investNum;
    }

    public void setInvestNum(Integer investNum) {
        this.investNum = investNum;
    }

    public String getNextLuckInvestNum() {
        return nextLuckInvestNum;
    }

    public void setNextLuckInvestNum(String nextLuckInvestNum) {
        this.nextLuckInvestNum = nextLuckInvestNum;
    }
}
