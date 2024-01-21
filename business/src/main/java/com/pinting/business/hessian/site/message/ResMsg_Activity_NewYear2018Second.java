package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by cyb on 2018/1/29.
 */
public class ResMsg_Activity_NewYear2018Second extends ResMsg {
    private static final long serialVersionUID = 1198753161192141820L;

    /* 活动是否开始 */
    private String isStart;

    private String startTime;

    private String endTime;

    /* 是否登录 */
    private String isLogin;

    /* 是否参与过 */
    private String isJoined;

    /* 备注 */
    private String note;

    ArrayList<HashMap<String, Object>> result;

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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public ArrayList<HashMap<String, Object>> getResult() {
        return result;
    }

    public void setResult(ArrayList<HashMap<String, Object>> result) {
        this.result = result;
    }
}
