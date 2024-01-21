package com.pinting.business.model.vo;

/**
 * Author:      cyb
 * Date:        2017/2/23
 * Description: 活动VO
 */
public class BsActivityVO {

    private int id;

    /* yyyy-MM-dd HH:mm:ss */
    private String startTime;

    /* yyyy-MM-dd HH:mm:ss */
    private String endTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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
}
