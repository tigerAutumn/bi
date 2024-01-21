package com.pinting.gateway.bird.in.model;

/**
 * Created by 剑钊 on 2016/8/10.
 * 获取token
 */
public class TokenRes {

    private String responseTime;
    private String responseMsg;
    private String token;

    public String getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(String responseTime) {
        this.responseTime = responseTime;
    }

    public String getResponseMsg() {
        return responseMsg;
    }

    public void setResponseMsg(String responseMsg) {
        this.responseMsg = responseMsg;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
