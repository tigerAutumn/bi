package com.pinting.open.pojo.response.asset;

import java.util.Date;

import com.pinting.open.base.response.Response;

public class BalanceWithdrawResponse extends Response {
    
    private String time;  // 提现限制时间
    
    private Boolean needCheck;  // 是否需要审核，true需要审核
    
	private String  withdrawTime; //提现申请时间
	
	private String orderNo; //订单号

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Boolean getNeedCheck() {
        return needCheck;
    }

    public void setNeedCheck(Boolean needCheck) {
        this.needCheck = needCheck;
    }
    
    
	public String getWithdrawTime() {
		return withdrawTime;
	}

	public void setWithdrawTime(String withdrawTime) {
		this.withdrawTime = withdrawTime;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

}
