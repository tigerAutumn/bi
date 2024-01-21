package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author:      cyb
 * Date:        2017/2/27
 * Description: 2017周年庆第三期活动主页面信息
 */
public class ResMsg_Activity_AnniversaryThirdHomePageInfo extends ResMsg {

    private static final long serialVersionUID = -2789768310514648993L;

    /* 是否登录 */
    private String isLogin;

    /* 五重礼活动开始时间 */
    private String startTimeFifth;

    /* 五重礼活动结束时间 */
    private String endTimeFifth;

    /* 六重礼活动开始时间 */
    private String startTimeSixth;

    /* 六重礼活动结束时间 */
    private String endTimeSixth;

    /* 五重礼是否开始标识 */
    private String isStartFifth;

    /* 六重礼是否开始标识 */
    private String isStartSixth;

    /* 五重礼今日我的年化投资额 */
    private Double selfAnnualizedInvestment;

    /* 五重礼我的预计可瓜分奖金 */
    private Double todayUserAwardAmount;

    /* 五重礼今日总年化投资额 */
    private Double todayTotalAmount;

    /* 五重礼今日可瓜分总奖金 */
    private Double todayAwardAmount;

    /* 五重礼今日年化投资额排行榜 */
    List<HashMap<String, Object>> todayInvestUserList;

    /* 六重礼是否抽中过奖品 */
    private String isWin;

    /* 六重礼抽中的奖品 */
    private Integer awardId;

    /* 下一个升级金额 */
    private Double nextUpAmount;

    public String getIsLogin() {
        return isLogin;
    }

    public void setIsLogin(String isLogin) {
        this.isLogin = isLogin;
    }

    public String getStartTimeFifth() {
        return startTimeFifth;
    }

    public void setStartTimeFifth(String startTimeFifth) {
        this.startTimeFifth = startTimeFifth;
    }

    public String getEndTimeFifth() {
        return endTimeFifth;
    }

    public void setEndTimeFifth(String endTimeFifth) {
        this.endTimeFifth = endTimeFifth;
    }

    public String getStartTimeSixth() {
        return startTimeSixth;
    }

    public void setStartTimeSixth(String startTimeSixth) {
        this.startTimeSixth = startTimeSixth;
    }

    public String getEndTimeSixth() {
        return endTimeSixth;
    }

    public void setEndTimeSixth(String endTimeSixth) {
        this.endTimeSixth = endTimeSixth;
    }

    public String getIsStartFifth() {
        return isStartFifth;
    }

    public void setIsStartFifth(String isStartFifth) {
        this.isStartFifth = isStartFifth;
    }

    public String getIsStartSixth() {
        return isStartSixth;
    }

    public void setIsStartSixth(String isStartSixth) {
        this.isStartSixth = isStartSixth;
    }

    public Double getSelfAnnualizedInvestment() {
        return selfAnnualizedInvestment;
    }

    public void setSelfAnnualizedInvestment(Double selfAnnualizedInvestment) {
        this.selfAnnualizedInvestment = selfAnnualizedInvestment;
    }

    public Double getTodayUserAwardAmount() {
        return todayUserAwardAmount;
    }

    public void setTodayUserAwardAmount(Double todayUserAwardAmount) {
        this.todayUserAwardAmount = todayUserAwardAmount;
    }

    public Double getTodayTotalAmount() {
        return todayTotalAmount;
    }

    public void setTodayTotalAmount(Double todayTotalAmount) {
        this.todayTotalAmount = todayTotalAmount;
    }

    public Double getTodayAwardAmount() {
        return todayAwardAmount;
    }

    public void setTodayAwardAmount(Double todayAwardAmount) {
        this.todayAwardAmount = todayAwardAmount;
    }

    public String getIsWin() {
        return isWin;
    }

    public void setIsWin(String isWin) {
        this.isWin = isWin;
    }

    public Integer getAwardId() {
        return awardId;
    }

    public void setAwardId(Integer awardId) {
        this.awardId = awardId;
    }

    public List<HashMap<String, Object>> getTodayInvestUserList() {
        return todayInvestUserList;
    }

    public void setTodayInvestUserList(List<HashMap<String, Object>> todayInvestUserList) {
        this.todayInvestUserList = todayInvestUserList;
    }

    public Double getNextUpAmount() {
        return nextUpAmount;
    }

    public void setNextUpAmount(Double nextUpAmount) {
        this.nextUpAmount = nextUpAmount;
    }
}
