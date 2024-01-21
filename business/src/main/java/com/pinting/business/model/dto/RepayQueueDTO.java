package com.pinting.business.model.dto;

import com.pinting.business.model.LnBindCard;
import com.pinting.business.model.LnPayOrders;

/**
 * 还款队列
 * Created by zhangbao on 2017/10/11.
 */
public class RepayQueueDTO {

    private LnPayOrders lnPayOrder;

    private LnBindCard lnBindCard;

    private String channel;
    
    private String userSignNo;
    
    private String payIP;

    public LnPayOrders getLnPayOrder() {
        return lnPayOrder;
    }

    public void setLnPayOrder(LnPayOrders lnPayOrder) {
        this.lnPayOrder = lnPayOrder;
    }

    public LnBindCard getLnBindCard() {
        return lnBindCard;
    }

    public void setLnBindCard(LnBindCard lnBindCard) {
        this.lnBindCard = lnBindCard;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

	public String getUserSignNo() {
		return userSignNo;
	}

	public void setUserSignNo(String userSignNo) {
		this.userSignNo = userSignNo;
	}

	public String getPayIP() {
		return payIP;
	}

	public void setPayIP(String payIP) {
		this.payIP = payIP;
	}
	
}
