/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.open.pojo.request.asset;

import com.pinting.open.base.request.Request;
import com.pinting.open.base.util.Constants;

/**
 * 安全中心回款路径校验交易密码请求数据
 * @author HuanXiong
 * @version $Id: CheckPayPasswordRequest.java, v 0.1 2015-12-17 下午7:08:52 HuanXiong Exp $
 */
public class CheckPayPasswordRequest extends Request {

	private Integer userId;
    private String payPassword; // 交易密码
    
    
    
    public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getPayPassword() {
        return payPassword;
    }

    public void setPayPassword(String payPassword) {
        this.payPassword = payPassword;
    }

    /** 
     * @see com.pinting.open.base.request.Request#restApiUrl()
     */
    @Override
    public String restApiUrl() {
        return Constants.BASE_REST_URL + "/mobile/asset/safe/checkPayPassword";
    }

    /** 
     * @see com.pinting.open.base.request.Request#testApiUrl()
     */
    @Override
    public String testApiUrl() {
        return Constants.BASE_REST_URL + "/mobile/asset/safe/checkPayPassword";
    }

}
