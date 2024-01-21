package com.pinting.business.model.vo;

/**
 * Author:      shh
 * Date:        2017/6/23
 * Description: 微信点亮存管图标瓜分百万红包首页信息VO
 */
public class ActivityForLightUp2017VO {

    /* 开始时间 */
    private String startTime;

    /* 结束时间 */
    private String endTime;

    /* 点亮人数 */
    private Integer num;

    /* 活动是否开始 */
    private String isStart;

    /* 是否已参加点亮活动 */
    private String isLightUp;

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

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getIsStart() {
        return isStart;
    }

    public void setIsStart(String isStart) {
        this.isStart = isStart;
    }

    public String getIsLightUp() {
        return isLightUp;
    }

    public void setIsLightUp(String isLightUp) {
        this.isLightUp = isLightUp;
    }
}
