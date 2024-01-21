package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

import java.util.HashMap;
import java.util.List;

/**
 * Author:      cyb
 * Date:        2017/6/26
 * Description:
 */
public class ResMsg_Activity_DepActivityInfo extends ResMsg {

    private static final long serialVersionUID = 7626591396424488640L;

    /* 是否登录 */
    private String isLogin;

    /* 答题活动页面信息 */
    private HashMap<String, Object> answer;

    /* 抱团活动页面信息 */
    private List<HashMap<String, Object>> teamList;

    /* 点亮活动信息 */
    private HashMap<String, Object> light;

    public String getIsLogin() {
        return isLogin;
    }

    public void setIsLogin(String isLogin) {
        this.isLogin = isLogin;
    }

    public HashMap<String, Object> getAnswer() {
        return answer;
    }

    public void setAnswer(HashMap<String, Object> answer) {
        this.answer = answer;
    }

    public List<HashMap<String, Object>> getTeamList() {
        return teamList;
    }

    public void setTeamList(List<HashMap<String, Object>> teamList) {
        this.teamList = teamList;
    }

    public HashMap<String, Object> getLight() {
        return light;
    }

    public void setLight(HashMap<String, Object> light) {
        this.light = light;
    }
}
