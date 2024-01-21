package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * Created by cyb on 2018/1/29.
 */
public class ResMsg_Activity_NewYear2018Third extends ResMsg {
    private static final long serialVersionUID = -444436591563683442L;

    /* 当前平台累计年化出借额 */
    private Double loanAmount;

    /* 我的年化出借额度 */
    private Double myLoanAmount;

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

    public Double getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(Double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public Double getMyLoanAmount() {
        return myLoanAmount;
    }

    public void setMyLoanAmount(Double myLoanAmount) {
        this.myLoanAmount = myLoanAmount;
    }
}
