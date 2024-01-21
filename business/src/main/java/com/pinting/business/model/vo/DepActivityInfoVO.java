package com.pinting.business.model.vo;

import java.util.List;

/**
 * Author:      cyb
 * Date:        2017/6/26
 * Description:
 */
public class DepActivityInfoVO {

    /* 是否登录 */
    private String isLogin;

    /* 答题活动页面信息 */
    private ActivityAnswerInfoVO answer;

    /* 抱团活动页面信息 */
    private List<ActivityTeamInfoVO> teamList;

    /* 点亮活动信息 */
    private ActivityBaseVO light;

    public List<ActivityTeamInfoVO> getTeamList() {
        return teamList;
    }

    public void setTeamList(List<ActivityTeamInfoVO> teamList) {
        this.teamList = teamList;
    }

    public String getIsLogin() {
        return isLogin;
    }

    public void setIsLogin(String isLogin) {
        this.isLogin = isLogin;
    }

    public ActivityAnswerInfoVO getAnswer() {
        return answer;
    }

    public void setAnswer(ActivityAnswerInfoVO answer) {
        this.answer = answer;
    }

    public ActivityBaseVO getLight() {
        return light;
    }

    public void setLight(ActivityBaseVO light) {
        this.light = light;
    }
}
