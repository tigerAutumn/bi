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
 * @version $Id: MyRecommendRequest.java, v 0.1 2015-12-22 下午3:25:24 HuanXiong Exp $
 */
public class MyRecommendRequest extends Request {
    
    private Integer userId;
    
    private Integer page;
    
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    @Override
    public String restApiUrl() {
        return Constants.BASE_REST_URL + "/mobile/more/myRecommend";
    }

    @Override
    public String testApiUrl() {
        return Constants.BASE_TEST_URL + "/mobile/more/myRecommend";
    }

}
