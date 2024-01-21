package com.pinting.gateway.hessian.message.pay19;

import java.util.Date;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 
 * @Project: business
 * @Title: G2BReqMsg_Pay4Another_queryCheckAccountFile.java
 * @author dingpf
 * @date 2015-11-16 下午2:06:00
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public class B2GReqMsg_Pay4Another_QueryCheckAccountFile extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2398520071291779568L;
	private Date checkDate;

	public Date getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}

}
