package com.pinting.business.model.vo;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.pinting.business.model.LnPayOrders;
/**
 * 分期订单
 * @author Dragon & cat
 * @date 2016-11-11
 */
public class LnPayOrdersVO extends LnPayOrders {
    
	/* 注册手机号*/
	private String userMobile;
	/* 交易开始时间*/
    private Date beginTime;
    /* 交易结束时间*/
    private Date overTime;
    /* 银行类型列表*/
	private List<HashMap<String, Object>> buyBankTypeList;

	public String getUserMobile() {
		return userMobile;
	}

	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getOverTime() {
		return overTime;
	}

	public void setOverTime(Date overTime) {
		this.overTime = overTime;
	}

	public List<HashMap<String, Object>> getBuyBankTypeList() {
		return buyBankTypeList;
	}

	public void setBuyBankTypeList(List<HashMap<String, Object>> buyBankTypeList) {
		this.buyBankTypeList = buyBankTypeList;
	}
	
	
}
