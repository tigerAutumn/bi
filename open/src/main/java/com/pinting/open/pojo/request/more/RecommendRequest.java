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
 * @version $Id: RecommendRequest.java, v 0.1 2015-12-22 下午3:30:53 HuanXiong Exp $
 */
public class RecommendRequest extends Request {
    
    @Override
    public String restApiUrl() {
        return Constants.BASE_REST_URL + "/mobile/more/recommend";
    }

    @Override
    public String testApiUrl() {
        return Constants.BASE_TEST_URL + "/mobile/more/recommend";
    }
    
}
