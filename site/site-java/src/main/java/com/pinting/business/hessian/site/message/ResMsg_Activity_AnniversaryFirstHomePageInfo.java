package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * Author:      cyb
 * Date:        2017/2/27
 * Description: 2017周年庆第一期活动主页面信息
 */
public class ResMsg_Activity_AnniversaryFirstHomePageInfo extends ResMsg {

    private static final long serialVersionUID = -2789768310514648993L;

    /* 是否登录 */
    private String isLogin;

    /* 一重礼活动开始时间 */
    private String startTimeFirst;

    /* 一重礼活动结束时间 */
    private String endTimeFirst;

    /* 二重礼活动开始时间 */
    private String startTimeSecond;

    /* 二重礼活动结束时间 */
    private String endTimeSecond;

    /* 一重礼是否开始标识 */
    private String isStartFirst;

    /* 二重礼是否开始标识 */
    private String isStartSecond;

    /* 元宝个数 */
    private Integer goldIngotNum;

    /* 是否可解锁：yes-可以；no-不可以 */
    private String canUnlock;

    /* 已解锁元宝个数 */
    private Integer unlockedNum;

    public Integer getUnlockedNum() {
        return unlockedNum;
    }

    public void setUnlockedNum(Integer unlockedNum) {
        this.unlockedNum = unlockedNum;
    }

    public String getIsLogin() {
        return isLogin;
    }

    public void setIsLogin(String isLogin) {
        this.isLogin = isLogin;
    }

    public String getStartTimeFirst() {
        return startTimeFirst;
    }

    public void setStartTimeFirst(String startTimeFirst) {
        this.startTimeFirst = startTimeFirst;
    }

    public String getEndTimeFirst() {
        return endTimeFirst;
    }

    public void setEndTimeFirst(String endTimeFirst) {
        this.endTimeFirst = endTimeFirst;
    }

    public String getStartTimeSecond() {
        return startTimeSecond;
    }

    public void setStartTimeSecond(String startTimeSecond) {
        this.startTimeSecond = startTimeSecond;
    }

    public String getEndTimeSecond() {
        return endTimeSecond;
    }

    public void setEndTimeSecond(String endTimeSecond) {
        this.endTimeSecond = endTimeSecond;
    }

    public String getIsStartFirst() {
        return isStartFirst;
    }

    public void setIsStartFirst(String isStartFirst) {
        this.isStartFirst = isStartFirst;
    }

    public String getIsStartSecond() {
        return isStartSecond;
    }

    public void setIsStartSecond(String isStartSecond) {
        this.isStartSecond = isStartSecond;
    }

    public Integer getGoldIngotNum() {
        return goldIngotNum;
    }

    public void setGoldIngotNum(Integer goldIngotNum) {
        this.goldIngotNum = goldIngotNum;
    }

    public String getCanUnlock() {
        return canUnlock;
    }

    public void setCanUnlock(String canUnlock) {
        this.canUnlock = canUnlock;
    }
}
