package com.pinting.open.pojo.request.asset;

import com.pinting.open.base.request.Request;
import com.pinting.open.base.util.Constants;

/**
 * 人脸核身token获取
 * Created by zousheng on 2018/05/28.
 */
public class TokenPoliceVerifyRequest extends Request {

    @Override
    public String restApiUrl() {
        return  Constants.BASE_REST_URL + "/mobile/asset/token_police_verify";
    }

    @Override
    public String testApiUrl() {
        return Constants.BASE_REST_URL + "/mobile/asset/token_police_verify";
    }
}
