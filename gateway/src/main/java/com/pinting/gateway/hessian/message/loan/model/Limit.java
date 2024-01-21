package com.pinting.gateway.hessian.message.loan.model;

import java.io.Serializable;

/**
 * Created by 剑钊 on 2016/8/22.
 */
@SuppressWarnings("serial")
public class Limit implements Serializable {

    /**
     * 日限额
     */
    private String day;

    /**
     * 单笔限额
     */
    private String single;
    
    /**
     * 银行名称
     */
    private String bankName;


    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getSingle() {
        return single;
    }

    public void setSingle(String single) {
        this.single = single;
    }

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

}
