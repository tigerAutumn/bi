package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * 
 * @author HuanXiong
 * @version $Id: ResMsg_Activity_LuckyDrawPageInfo.java, v 0.1 2016-1-21 下午6:00:23 HuanXiong Exp $
 */
public class ResMsg_Activity_LuckyDrawPageInfo extends ResMsg {

    /**  */
    private static final long serialVersionUID = -7463632105559786998L;

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
