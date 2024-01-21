package com.pinting.gateway.baofoo.out.model.req;

import com.pinting.gateway.hessian.message.baofoo.model.RiskItems;

/**
 * 协议支付-直接支付类交易
 * @project gateway
 * @title AgreementReq.java
 * @author Dragon & cat
 * @date 2018-3-31
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description
 */
public class SinglePayReq extends BaoFooOutBaseReq {
	
	/*报文发送日期时间*/
	private 	String 		send_time;
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
	/*商户订单号*/
	private 	String 		trans_id;
	/*数字信封*/
	private 	String 		dgtl_envlp;
	/*用户ID*/
	private 	String 		user_id;
	/*签约协议号*/
	private 	String 		protocol_no;
	/*交易金额*/
	private 	Long 		txn_amt;
	/*卡信息*/
	private 	String 		card_info;
	/*风控参数*/
	private 	String 		risk_item;
	/*交易成功通知地址*/
	private 	String 		return_url;
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
	
	/*风控参数*/
	private		RiskItems		riskItems;
	
	public String getSend_time() {
		return send_time;
	}
	public void setSend_time(String send_time) {
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
	public String getTrans_id() {
		return trans_id;
	}
	public void setTrans_id(String trans_id) {
		this.trans_id = trans_id;
	}
	public String getDgtl_envlp() {
		return dgtl_envlp;
	}
	public void setDgtl_envlp(String dgtl_envlp) {
		this.dgtl_envlp = dgtl_envlp;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getProtocol_no() {
		return protocol_no;
	}
	public void setProtocol_no(String protocol_no) {
		this.protocol_no = protocol_no;
	}
	public Long getTxn_amt() {
		return txn_amt;
	}
	public void setTxn_amt(Long txn_amt) {
		this.txn_amt = txn_amt;
	}
	public String getCard_info() {
		return card_info;
	}
	public void setCard_info(String card_info) {
		this.card_info = card_info;
	}
	public String getRisk_item() {
		return risk_item;
	}
	public void setRisk_item(String risk_item) {
		this.risk_item = risk_item;
	}
	public String getReturn_url() {
		return return_url;
	}
	public void setReturn_url(String return_url) {
		this.return_url = return_url;
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
	public RiskItems getRiskItems() {
		return riskItems;
	}
	public void setRiskItems(RiskItems riskItems) {
		this.riskItems = riskItems;
	}
	
}
