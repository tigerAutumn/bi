package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * Author:      cyb
 * Date:        2017/2/27
 * Description: 三重礼祝福语
 */
public class ResMsg_Activity_AnniversaryBenison extends ResMsg {

    private static final long serialVersionUID = -6546835643311145504L;

    /* 活动是否开始 */
    private String isStart;

    private String startTime;

    private String endTime;

    /* 是否登录 */
    private String isLogin;

    /* 是否参与过 */
    private String isJoined;

    public String getIsStart() {
        return isStart;
    }

    public void setIsStart(String isStart) {
        this.isStart = isStart;
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

    public String getIsLogin() {
        return isLogin;
    }

    public void setIsLogin(String isLogin) {
        this.isLogin = isLogin;
    }

    public String getIsJoined() {
        return isJoined;
    }

    public void setIsJoined(String isJoined) {
        this.isJoined = isJoined;
    }
}
