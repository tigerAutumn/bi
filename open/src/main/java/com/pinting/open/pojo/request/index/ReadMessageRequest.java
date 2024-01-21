package com.pinting.open.pojo.request.index;

import com.pinting.open.base.request.Request;
import com.pinting.open.base.util.Constants;

/**
 * Author:      cyb
 * Date:        2017/6/30
 * Description:
 */
public class ReadMessageRequest extends Request {

    /* 用户ID */
    private Integer userId;

    /* 类型：NOTICE-公告；ACTIVITY-活动 */
    private String position;

    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String restApiUrl() {
        return Constants.BASE_REST_URL + "/mobile/index/readMessage";
    }

    public String testApiUrl() {
        return Constants.BASE_TEST_URL + "/mobile/index/readMessage";
    }
}
