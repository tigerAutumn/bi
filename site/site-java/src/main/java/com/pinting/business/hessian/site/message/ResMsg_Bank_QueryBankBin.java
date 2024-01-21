/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * 查询卡bin 出参
 * @author HuanXiong
 * @version $Id: ResMsg_Bank_QueryBankBin.java, v 0.1 2015-11-14 下午2:07:56 HuanXiong Exp $
 */
public class ResMsg_Bank_QueryBankBin extends ResMsg {

    /**  */
    private static final long serialVersionUID = -4171874459590972621L;

    /*银行id*/
    private Integer           bankId;
    /*卡bin*/
    private String            cardBin;
    /*卡bin长度*/
    private Integer           cardBinLen;
    /*卡号长度*/
    private Integer           bankCardLen;
    /*银行名称*/
    private String            bankName;
    /*金额*/
    private Double            amount;
    /*银行功能类型*/
    private String			  bankCardFuncType;

    public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Integer getBankId() {
        return bankId;
    }

    public void setBankId(Integer bankId) {
        this.bankId = bankId;
    }

    public String getCardBin() {
        return cardBin;
    }

    public void setCardBin(String cardBin) {
        this.cardBin = cardBin;
    }

    public Integer getCardBinLen() {
        return cardBinLen;
    }

    public void setCardBinLen(Integer cardBinLen) {
        this.cardBinLen = cardBinLen;
    }

    public Integer getBankCardLen() {
        return bankCardLen;
    }

    public void setBankCardLen(Integer bankCardLen) {
        this.bankCardLen = bankCardLen;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

	public String getBankCardFuncType() {
		return bankCardFuncType;
	}

	public void setBankCardFuncType(String bankCardFuncType) {
		this.bankCardFuncType = bankCardFuncType;
	}

}
