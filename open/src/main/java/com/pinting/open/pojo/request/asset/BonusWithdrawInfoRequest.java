package com.pinting.open.pojo.request.asset;

import com.pinting.open.base.request.Request;
import com.pinting.open.base.util.Constants;

/**
 * Created by cyb on 2018/2/27.
 */
public class BonusWithdrawInfoRequest extends Request {

    /*用户ID*/
    private		Integer		userId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String restApiUrl() {
        return Constants.BASE_REST_URL + "/mobile/asset/my_bonus_withdraw_info";
    }

    @Override
    public String testApiUrl() {
        return Constants.BASE_TEST_URL + "/mobile/asset/my_bonus_withdraw_info";
    }
}
