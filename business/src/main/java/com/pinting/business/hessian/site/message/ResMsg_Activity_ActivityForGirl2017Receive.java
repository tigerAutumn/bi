package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * Author:      cyb
 * Date:        2017/2/13
 * Description:
 */
public class ResMsg_Activity_ActivityForGirl2017Receive extends ResMsg {

    private static final long serialVersionUID = -2061890870662921625L;

    /* 本次是否成功领取：yes-已领取；no-未领取 */
    private String isWin;

    /* 活动是否开始：not_start-未开始；start-开始；end-结束 */
    private String isStart;

    /* 是否绑卡：yes-是；no-不是 */
    private String isBind;

    /* 是否活动指定用户-钱报用户：yes-是；no-不是 */
    private String isSpecifyUser;

    /* 曾经领取过：yes-是；no-不是 */
    private String everWin;

    /* 是否已经登录：yes-是；no-不是 */
    private String isLogin;

    public String getIsWin() {
        return isWin;
    }

    public void setIsWin(String isWin) {
        this.isWin = isWin;
    }

    public String getIsStart() {
        return isStart;
    }

    public void setIsStart(String isStart) {
        this.isStart = isStart;
    }

    public String getIsBind() {
        return isBind;
    }

    public void setIsBind(String isBind) {
        this.isBind = isBind;
    }

    public String getIsSpecifyUser() {
        return isSpecifyUser;
    }

    public void setIsSpecifyUser(String isSpecifyUser) {
        this.isSpecifyUser = isSpecifyUser;
    }

    public String getEverWin() {
        return everWin;
    }

    public void setEverWin(String everWin) {
        this.everWin = everWin;
    }

    public String getIsLogin() {
        return isLogin;
    }

    public void setIsLogin(String isLogin) {
        this.isLogin = isLogin;
    }
}
