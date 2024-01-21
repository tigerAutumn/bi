package com.pinting.gateway.bird.out.model;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by 剑钊 on 2016/8/13.
 * 
 */
@SuppressWarnings("serial")
public class BankLimit implements Serializable{

	private String payType;

    private Map<String,Limit> bankLimits;

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public Map<String, Limit> getBankLimits() {
		return bankLimits;
	}

	public void setBankLimits(Map<String, Limit> bankLimits) {
		this.bankLimits = bankLimits;
	}
}
