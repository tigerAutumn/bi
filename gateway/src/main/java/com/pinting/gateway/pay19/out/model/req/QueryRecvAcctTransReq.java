/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.gateway.pay19.out.model.req;

/**
 * 
 * @author Baby shark love blowing wind
 * @version $Id: QueryRecvAcctTransReq.java, v 0.1 2015-11-3 下午4:48:24 BabyShark Exp $
 */
public class QueryRecvAcctTransReq extends AcctTransBaseReq {

  
    /**
	 * 
	 */
	private static final long serialVersionUID = -3078876887649708591L;
	private String            oriOutMxId;
    private String            oriOutOrderId;
    private String            oriOutOrderDate;
    private String            tradeType="TRANSFER";
    private String            ts;
	public String getOriOutMxId() {
		return oriOutMxId;
	}
	public void setOriOutMxId(String oriOutMxId) {
		this.oriOutMxId = oriOutMxId;
	}
	public String getOriOutOrderId() {
		return oriOutOrderId;
	}
	public void setOriOutOrderId(String oriOutOrderId) {
		this.oriOutOrderId = oriOutOrderId;
	}
	public String getOriOutOrderDate() {
		return oriOutOrderDate;
	}
	public void setOriOutOrderDate(String oriOutOrderDate) {
		this.oriOutOrderDate = oriOutOrderDate;
	}
	public String getTradeType() {
		return tradeType;
	}
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}
	public String getTs() {
		return ts;
	}
	public void setTs(String ts) {
		this.ts = ts;
	}
    
}
