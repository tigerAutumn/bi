package com.pinting.gateway.bird.in.model;

import java.util.Date;

/**
 * Created by 剑钊 on 2016/8/10.
 * 获取token
 */
public class TokenReq {

    private String clientSecret;
    private String requestTime;
    private String clientKey;

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(String requestTime) {
        this.requestTime = requestTime;
    }

    public String getClientKey() {
        return clientKey;
    }

    public void setClientKey(String clientKey) {
        this.clientKey = clientKey;
    }
}
