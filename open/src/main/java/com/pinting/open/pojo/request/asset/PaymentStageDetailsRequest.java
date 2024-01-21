package com.pinting.open.pojo.request.asset;

import com.pinting.open.base.request.Request;
import com.pinting.open.base.util.Constants;

/**
 * Author:      cyb
 * Date:        2017/2/15
 * Description: 交易明细分期回款列表请求信息
 */
public class PaymentStageDetailsRequest extends Request {

    /* 用户ID */
    private Integer userId;

    /* yyyy-MM-dd HH:mm:ss */
    private String time;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String restApiUrl() {
        return Constants.BASE_REST_URL + "/mobile/asset/payment_stage_details";
    }

    public String testApiUrl() {
        return Constants.BASE_TEST_URL + "/mobile/asset/payment_stage_details";
    }
}
