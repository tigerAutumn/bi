package com.pinting.open.pojo.request.asset;

import com.pinting.open.base.request.Request;
import com.pinting.open.base.util.Constants;

/**
 * 回款计划列表
 * Created by shh on 2017/3/30.
 */
public class RepayScheduleListRequest extends Request {

    /* 用户id */
    private Integer userId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String restApiUrl() {
        return Constants.BASE_REST_URL + "/mobile/asset/repayScheduleList";
    }

    @Override
    public String testApiUrl() {
        return Constants.BASE_REST_URL + "/mobile/asset/repayScheduleList";
    }
}
