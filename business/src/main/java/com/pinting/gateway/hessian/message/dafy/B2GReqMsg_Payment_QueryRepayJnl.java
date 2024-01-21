package com.pinting.gateway.hessian.message.dafy;

import java.util.Date;
import java.util.List;

import com.pinting.core.hessian.msg.ReqMsg;
import com.pinting.gateway.hessian.message.dafy.model.Products;

/**
 * 
 * @Project: gateway
 * @Title: B2GReqMsg_Payment_QueryRepayJnl.java
 * @author dingpf
 * @date 2016-4-26 下午3:34:06
 * @Copyright: 2016 BiGangWan.com Inc. All rights reserved.
 */
public class B2GReqMsg_Payment_QueryRepayJnl extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2162127452570495739L;
	
	private String borrowerCustomerId;
	private String borrowId;
	private String propertySymbol;
    
    public String getPropertySymbol() {
        return propertySymbol;
    }
    public void setPropertySymbol(String propertySymbol) {
        this.propertySymbol = propertySymbol;
    }
	public String getBorrowerCustomerId() {
		return borrowerCustomerId;
	}
	public void setBorrowerCustomerId(String borrowerCustomerId) {
		this.borrowerCustomerId = borrowerCustomerId;
	}
	public String getBorrowId() {
		return borrowId;
	}
	public void setBorrowId(String borrowId) {
		this.borrowId = borrowId;
	}
	
}
