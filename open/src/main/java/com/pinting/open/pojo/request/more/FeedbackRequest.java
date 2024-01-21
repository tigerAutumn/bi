/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.open.pojo.request.more;

import com.pinting.open.base.request.Request;
import com.pinting.open.base.util.Constants;

/**
 * 
 * @author HuanXiong
 * @version $Id: FeedbackRequest.java, v 0.1 2015-12-22 下午3:17:06 HuanXiong Exp $
 */
public class FeedbackRequest extends Request {
    
    private Integer userId;
    
    private String info;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String restApiUrl() {
        return Constants.BASE_REST_URL + "/mobile/more/feedback";
    }

    @Override
    public String testApiUrl() {
        return Constants.BASE_TEST_URL + "/mobile/more/feedback";
    }
    
}
