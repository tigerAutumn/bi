package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

import java.util.HashMap;
import java.util.List;

/**
 * Created by cyb on 2017/11/6.
 */
public class ResMsg_Activity_LuckyNumber extends ResMsg {

    private static final long serialVersionUID = 758032977621066340L;

    private String isStart;

    private String isLogin;

    private List<HashMap<String, Object>> luckyNumber;

    private List<HashMap<String, Object>> luckyNumberList;

    /* 等待公布。YES-需要等待；NO-无需等待 */
    private String waitPublish;

    public String getWaitPublish() {
        return waitPublish;
    }

    public void setWaitPublish(String waitPublish) {
        this.waitPublish = waitPublish;
    }

    public List<HashMap<String, Object>> getLuckyNumber() {
        return luckyNumber;
    }

    public void setLuckyNumber(List<HashMap<String, Object>> luckyNumber) {
        this.luckyNumber = luckyNumber;
    }

    public List<HashMap<String, Object>> getLuckyNumberList() {
        return luckyNumberList;
    }

    public void setLuckyNumberList(List<HashMap<String, Object>> luckyNumberList) {
        this.luckyNumberList = luckyNumberList;
    }

    public String getIsStart() {
        return isStart;
    }

    public void setIsStart(String isStart) {
        this.isStart = isStart;
    }

    public String getIsLogin() {
        return isLogin;
    }

    public void setIsLogin(String isLogin) {
        this.isLogin = isLogin;
    }
}
