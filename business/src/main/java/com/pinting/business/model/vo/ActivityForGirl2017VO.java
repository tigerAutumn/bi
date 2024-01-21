package com.pinting.business.model.vo;

import java.io.Serializable;

/**
 * Author:      cyb
 * Date:        2017/2/10
 * Description: 2017年女神节活动页面信息VO
 */
public class ActivityForGirl2017VO implements Serializable {

    /* 开始时间 */
    private String startTime;

    /* 结束时间 */
    private String endTime;

    /* 总送出奖品个数 */
    private Integer num;

    /* 活动是否开始 */
    private String isStart;

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
}
