package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * Author:      cyb
 * Date:        2017/2/13
 * Description: 2017年女神节活动页面信息响应信息
 */
public class ResMsg_Activity_ActivityForGirl2017PageInfo extends ResMsg {

    private static final long serialVersionUID = -3423924837660722653L;

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
