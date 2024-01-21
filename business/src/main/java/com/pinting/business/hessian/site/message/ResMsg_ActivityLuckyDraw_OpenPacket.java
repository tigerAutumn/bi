package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * Created by Administrator on 2016/10/31.
 */
public class ResMsg_ActivityLuckyDraw_OpenPacket extends ResMsg {
    private static final long serialVersionUID = 6046687481428990411L;

    /*抽奖前可抽奖次数*/
    private Integer beforeTimes;
    /*抽奖后可抽奖次数*/
    private Integer afterTimes;
    /*奖品id*/
    private Integer awardId;
    /*奖品名称*/
    private String awardContent;
    /*是否开始，noStart-未开始，end-已结束，ing进行中*/
    private String isStart;

    public Integer getBeforeTimes() {
        return beforeTimes;
    }

    public void setBeforeTimes(Integer beforeTimes) {
        this.beforeTimes = beforeTimes;
    }

    public Integer getAfterTimes() {
        return afterTimes;
    }

    public void setAfterTimes(Integer afterTimes) {
        this.afterTimes = afterTimes;
    }

    public Integer getAwardId() {
        return awardId;
    }

    public void setAwardId(Integer awardId) {
        this.awardId = awardId;
    }

    public String getAwardContent() {
        return awardContent;
    }

    public void setAwardContent(String awardContent) {
        this.awardContent = awardContent;
    }

    public String getIsStart() {
        return isStart;
    }

    public void setIsStart(String isStart) {
        this.isStart = isStart;
    }
}
