/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.open.pojo.request.asset;

import com.pinting.open.base.request.Request;
import com.pinting.open.base.util.Constants;

/**
 * 
 * @author HuanXiong
 * @version $Id: WithdrawalIndexRequest.java, v 0.1 2015-12-24 上午10:37:44 HuanXiong Exp $
 */
public class WithdrawalIndexRequest extends Request {
    
    private Integer userId;
    
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String restApiUrl() {
        return Constants.BASE_REST_URL + "/mobile/asset/withdrawal_index";
    }

    @Override
    public String testApiUrl() {
        return Constants.BASE_TEST_URL + "/mobile/asset/withdrawal_index";
    }

}
