package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

import java.util.HashMap;
import java.util.List;

/**
 * Author:      cyb
 * Date:        2017/2/27
 * Description: 2017周年庆第二期活动主页面信息
 */
public class ResMsg_Activity_AnniversarySecondHomePageInfo extends ResMsg {

    private static final long serialVersionUID = 3381176662736766241L;

    /* 是否登录 */
    private String isLogin;

    /* 三重礼活动开始时间 */
    private String startTimeThird;

    /* 三重礼活动结束时间 */
    private String endTimeThird;

    /* 四重礼活动开始时间 */
    private String startTimeFourth;

    /* 四重礼活动结束时间 */
    private String endTimeFourth;

    /* 三重礼是否开始标识 */
    private String isStartThird;

    /* 四重礼是否开始标识 */
    private String isStartFourth;

    /* 祝福语列表数据 */
    private List<HashMap<String, Object>> benisonList;

    /* 年化投资额实时排行榜（取前15名） */
    private List<HashMap<String, Object>> investUserList;

    public String getIsLogin() {
        return isLogin;
    }

    public void setIsLogin(String isLogin) {
        this.isLogin = isLogin;
    }

    public String getStartTimeThird() {
        return startTimeThird;
    }

    public void setStartTimeThird(String startTimeThird) {
        this.startTimeThird = startTimeThird;
    }

    public String getEndTimeThird() {
        return endTimeThird;
    }

    public void setEndTimeThird(String endTimeThird) {
        this.endTimeThird = endTimeThird;
    }

    public String getStartTimeFourth() {
        return startTimeFourth;
    }

    public void setStartTimeFourth(String startTimeFourth) {
        this.startTimeFourth = startTimeFourth;
    }

    public String getEndTimeFourth() {
        return endTimeFourth;
    }

    public void setEndTimeFourth(String endTimeFourth) {
        this.endTimeFourth = endTimeFourth;
    }

    public String getIsStartThird() {
        return isStartThird;
    }

    public void setIsStartThird(String isStartThird) {
        this.isStartThird = isStartThird;
    }

    public String getIsStartFourth() {
        return isStartFourth;
    }

    public void setIsStartFourth(String isStartFourth) {
        this.isStartFourth = isStartFourth;
    }

    public List<HashMap<String, Object>> getBenisonList() {
        return benisonList;
    }

    public void setBenisonList(List<HashMap<String, Object>> benisonList) {
        this.benisonList = benisonList;
    }

    public List<HashMap<String, Object>> getInvestUserList() {
        return investUserList;
    }

    public void setInvestUserList(List<HashMap<String, Object>> investUserList) {
        this.investUserList = investUserList;
    }
}
