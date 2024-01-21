/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.hessian.site.message;

import java.util.Date;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * 
 * @author HuanXiong
 * @version $Id: ResMsg_UserBalance_Withdraw.java, v 0.1 2015-12-23 上午10:22:57 HuanXiong Exp $
 */
public class ResMsg_UserBalance_Withdraw extends ResMsg {

    /**  */
    private static final long serialVersionUID = -8245634820193665173L;

    private Date time;  // 提现限制时间
    
    private boolean needCheck;  // 是否需要审核，true需要审核
    
	private Date  withdrawTime; //提现申请时间
	private String orderNo; //订单号

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public boolean isNeedCheck() {
        return needCheck;
    }

    public void setNeedCheck(boolean needCheck) {
        this.needCheck = needCheck;
    }

	public Date getWithdrawTime() {
		return withdrawTime;
	}

	public void setWithdrawTime(Date withdrawTime) {
		this.withdrawTime = withdrawTime;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
    
}
