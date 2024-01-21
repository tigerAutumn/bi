package com.pinting.open.pojo.response.asset;

import com.pinting.open.base.response.Response;

/**
 * 人脸核身token获取
 * Created by zousheng on 2018/05/28.
 */
public class TokenPoliceVerifyResponse extends Response {

    private String accessToken; // 要获取的Access Token

    private Long expiresIn; // Access Token的有效期(秒为单位，一般为1个月)

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }
}
