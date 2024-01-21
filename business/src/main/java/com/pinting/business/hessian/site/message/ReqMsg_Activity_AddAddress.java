package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * Created by cyb on 2017/11/6.
 */
public class ReqMsg_Activity_AddAddress extends ReqMsg {

    private static final long serialVersionUID = 8732238892689085157L;

    private Integer userId;

    private Integer luckyDrawId;

    private String mobile;

    private String userName;

    private String address;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getLuckyDrawId() {
        return luckyDrawId;
    }

    public void setLuckyDrawId(Integer luckyDrawId) {
        this.luckyDrawId = luckyDrawId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
