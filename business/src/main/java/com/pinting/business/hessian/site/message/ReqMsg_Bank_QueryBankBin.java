/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 查询卡bin 入参
 * @author HuanXiong
 * @version $Id: ReqMsg_Bank_QueryByBankByBin.java, v 0.1 2015-11-14 下午2:05:28 HuanXiong Exp $
 */
public class ReqMsg_Bank_QueryBankBin extends ReqMsg {

    /**  */
    private static final long serialVersionUID = 9056518389587546454L;
    /*卡号*/
    private String            cardNo;
    /*用户id*/
    private Integer           userId;


    public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

}
