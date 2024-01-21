package com.pinting.open.pojo.request.index;

import com.pinting.open.base.request.Request;
import com.pinting.open.base.util.Constants;

/**
 * Author:      cyb
 * Date:        2017/6/30
 * Description:
 */
public class IndexMessageRequest extends Request {

    /* 用户ID */
    private Integer userId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String restApiUrl() {
        return Constants.BASE_REST_URL + "/mobile/index/indexMessage";
    }

    public String testApiUrl() {
        return Constants.BASE_TEST_URL + "/mobile/index/indexMessage";
    }
}
