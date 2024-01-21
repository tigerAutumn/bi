package com.pinting.gateway.hessian.message.baofoo;

import java.util.Date;

import com.pinting.core.hessian.msg.ResMsg;
/**
 * 协议支付-直接支付类交易
 * @project gateway
 * @title B2GResMsg_BaoFooAgreementPay_DirectAgreementPay.java
 * @author Dragon & cat
 * @date 2018-3-31
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description
 */
public class B2GResMsg_BaoFooAgreementPay_DirectAgreementPay extends ResMsg {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4037373332893314745L;
	/*报文发送日期时间*/
	private 	Date 		send_time;
	/*应答报文流水号*/
	private 	String 		msg_id;
	/*报文编号/版本号*/
	private 	String 		version;
	/*应答码*/
	private 	String 		resp_code;
	/*终端号*/
	private 	String 		terminal_id;
	/*交易类型*/
	private 	String 		txn_type;
	/*商户号*/
	private 	String 		member_id;
	/*业务返回码*/
	private 	String 		biz_resp_code;
	/*业务返回说明*/
	private 	String 		biz_resp_msg;
	/*成功金额*/
	private 	Long 		succ_amt;
	/*成功时间*/
	private 	Date 		succ_time;
	/*宝付订单号*/
	private 	String 		order_id;
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
	
	/**
     * 钱包转账订单号
     */
    private String pay4OnlineOrderNo;
    
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
	public String getResp_code() {
		return resp_code;
	}
	public void setResp_code(String resp_code) {
		this.resp_code = resp_code;
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
	public String getBiz_resp_code() {
		return biz_resp_code;
	}
	public void setBiz_resp_code(String biz_resp_code) {
		this.biz_resp_code = biz_resp_code;
	}
	public String getBiz_resp_msg() {
		return biz_resp_msg;
	}
	public void setBiz_resp_msg(String biz_resp_msg) {
		this.biz_resp_msg = biz_resp_msg;
	}
	public Long getSucc_amt() {
		return succ_amt;
	}
	public void setSucc_amt(Long succ_amt) {
		this.succ_amt = succ_amt;
	}
	public Date getSucc_time() {
		return succ_time;
	}
	public void setSucc_time(Date succ_time) {
		this.succ_time = succ_time;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
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
	public String getPay4OnlineOrderNo() {
		return pay4OnlineOrderNo;
	}
	public void setPay4OnlineOrderNo(String pay4OnlineOrderNo) {
		this.pay4OnlineOrderNo = pay4OnlineOrderNo;
	}
	
}
