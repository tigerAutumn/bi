package com.pinting.gateway.hessian.message.baofoo;

import java.util.Date;

import com.pinting.core.hessian.msg.ReqMsg;
/**
 * 支付订单查询
 * @project business
 * @title B2GReqMsg_BaoFooAgreementPay_QueryOrder.java
 * @author Dragon & cat
 * @date 2018-4-1
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description
 */
public class B2GReqMsg_BaoFooAgreementPay_QueryOrder extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4106855765833687450L;
	/*报文发送日期时间*/
	private 	Date 		send_time;
	/*报文流水号*/
	private 	String 		msg_id;
	/*报文编号/版本号*/
	private 	String 		version;
	/*终端号*/
	private 	String 		terminal_id;
	/*交易类型*/
	private 	String 		txn_type;
	/*商户号*/
	private 	String 		member_id;
	/*商户原始订单号*/
	private 	String 		orig_trans_id;
	/*交易日期*/
	private 	Date 		orig_trade_date;
	/*商户保留域1*/
	private 	String 		req_reserved1;
	/*商户保留域2*/
	private 	String 		req_reserved2;
	/*系统保留域1*/
	private 	String 		additional_info1;
	/*系统保留域2*/
	private 	String 		additional_info2;
	/*签名域*/
	private 	String 		signature;
	public Date getSend_time() {
		return send_time;
	}
	public void setSend_time(Date send_time) {
		this.send_time = send_time;
	}
	public String getMsg_id() {
		return msg_id;
	}
	public void setMsg_id(String msg_id) {
		this.msg_id = msg_id;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getTerminal_id() {
		return terminal_id;
	}
	public void setTerminal_id(String terminal_id) {
		this.terminal_id = terminal_id;
	}
	public String getTxn_type() {
		return txn_type;
	}
	public void setTxn_type(String txn_type) {
		this.txn_type = txn_type;
	}
	public String getMember_id() {
		return member_id;
	}
	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}
	public String getOrig_trans_id() {
		return orig_trans_id;
	}
	public void setOrig_trans_id(String orig_trans_id) {
		this.orig_trans_id = orig_trans_id;
	}
	public Date getOrig_trade_date() {
		return orig_trade_date;
	}
	public void setOrig_trade_date(Date orig_trade_date) {
		this.orig_trade_date = orig_trade_date;
	}
	public String getReq_reserved1() {
		return req_reserved1;
	}
	public void setReq_reserved1(String req_reserved1) {
		this.req_reserved1 = req_reserved1;
	}
	public String getReq_reserved2() {
		return req_reserved2;
	}
	public void setReq_reserved2(String req_reserved2) {
		this.req_reserved2 = req_reserved2;
	}
	public String getAdditional_info1() {
		return additional_info1;
	}
	public void setAdditional_info1(String additional_info1) {
		this.additional_info1 = additional_info1;
	}
	public String getAdditional_info2() {
		return additional_info2;
	}
	public void setAdditional_info2(String additional_info2) {
		this.additional_info2 = additional_info2;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	
}
