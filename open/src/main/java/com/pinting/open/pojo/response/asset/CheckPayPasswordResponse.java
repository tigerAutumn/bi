/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.open.pojo.response.asset;

import com.pinting.open.base.response.Response;

/**
 * 安全中心回款路径校验交易密码返回数据
 * @author HuanXiong
 * @version $Id: CheckPayPasswordResponse.java, v 0.1 2015-12-17 下午7:09:44 HuanXiong Exp $
 */
public class CheckPayPasswordResponse extends Response {
    private boolean truePayPassword;   //是否校验成功
    
    private Integer failNums;  //错误次数
    
    private String toastMsg;  //超过次数限制的提示信息

	public boolean isTruePayPassword() {
		return truePayPassword;
	}

	public void setTruePayPassword(boolean truePayPassword) {
		this.truePayPassword = truePayPassword;
	}

	public Integer getFailNums() {
		return failNums;
	}

	public void setFailNums(Integer failNums) {
		this.failNums = failNums;
	}

	public String getToastMsg() {
		return toastMsg;
	}

	public void setToastMsg(String toastMsg) {
		this.toastMsg = toastMsg;
	}
    
    
    
}
