package com.pinting.open.pojo.request.more;

import com.pinting.open.base.request.Request;
import com.pinting.open.base.util.Constants;

/**
 * Created by cyb on 2018/2/23.
 */
public class RiskRequest extends Request {

    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String restApiUrl() {
        return Constants.BASE_REST_URL + "/mobile/more/risk";
    }

    @Override
    public String testApiUrl() {
        return Constants.BASE_TEST_URL + "/mobile/more/risk";
    }

}
