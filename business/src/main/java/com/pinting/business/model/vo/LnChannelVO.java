package com.pinting.business.model.vo;

import java.util.Date;

import com.pinting.business.model.BsPaymentChannel;

/**
 * Created by zhangbao on 2017/12/13.
 */
public class LnChannelVO extends BsPaymentChannel {

    private String partnerCode;
    
    private String isProtocolPay; //是否签约
    
    private Date transTime; //交易时间（updateTime）

    public String getPartnerCode() {
        return partnerCode;
    }

    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
    }

	public String getIsProtocolPay() {
		return isProtocolPay;
	}

	public void setIsProtocolPay(String isProtocolPay) {
		this.isProtocolPay = isProtocolPay;
	}

	public Date getTransTime() {
		return transTime;
	}

	public void setTransTime(Date transTime) {
		this.transTime = transTime;
	}
}
