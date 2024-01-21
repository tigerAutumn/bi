/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * 充值首页信息
 * @author HuanXiong
 * @version $Id: ResMsg_Recharge_RechargeIndexInfo.java, v 0.1 2015-11-19 上午10:28:47 HuanXiong Exp $
 */
public class ResMsg_Recharge_RechargeIndexInfo extends ResMsg {
    
    /**  */
    private static final long serialVersionUID = 5918422111147390300L;

    private Integer userId;
    
    private Double availableBalance;    // 可用余额
    
    private String rechargeLimit;  //充值下限金额


    private Double depAvailableBalance; // 存管户可用余额

    public Double getDepAvailableBalance() {
        return depAvailableBalance;
    }

    public void setDepAvailableBalance(Double depAvailableBalance) {
        this.depAvailableBalance = depAvailableBalance;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Double getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(Double availableBalance) {
        this.availableBalance = availableBalance;
    }

	public String getRechargeLimit() {
		return rechargeLimit;
	}

	public void setRechargeLimit(String rechargeLimit) {
		this.rechargeLimit = rechargeLimit;
	}
    
}
