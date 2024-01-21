/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 网银充值请求
 * @author HuanXiong
 * @version $Id: ReqMsg_Recharge_EBankRecharge.java, v 0.1 2015-11-20 下午5:42:51 HuanXiong Exp $
 */
public class ReqMsg_Recharge_EBankRecharge extends ReqMsg {

    /**  */
    private static final long serialVersionUID = 12823635102554471L;

    private Integer userId;
    
    private Double amount;  // 充值金额
    
    private String userName;
    
    private String mobile;
    
    private String idCard;
    
	private String flag;
	
	private String bankId;//银行编号
    
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}
    
    
}
