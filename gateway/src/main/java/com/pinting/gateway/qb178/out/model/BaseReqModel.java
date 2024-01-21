package com.pinting.gateway.qb178.out.model;

/**
 * @author bianyatian
 * @2017-7-28 下午2:56:33
 */
public class BaseReqModel {
	/* 合作商 id，渠道分配 */
	private Integer exchange_code;
	/* 合作商渠道，渠道分配 */
	private String channel_code;
	/* 合作商处理日期 yyyyMMdd */
	private String deal_date;
	/* 合作商处理时间 HHmmss */
	private String deal_time;
	/* 合作商流水号，全局唯一，建议时间戳 */
	private String serial_no;
	/* 签名串 */
	private String cert_sign;
	public Integer getExchange_code() {
		return exchange_code;
	}
	public void setExchange_code(Integer exchange_code) {
		this.exchange_code = exchange_code;
	}
	public String getChannel_code() {
		return channel_code;
	}
	public void setChannel_code(String channel_code) {
		this.channel_code = channel_code;
	}
	public String getDeal_date() {
		return deal_date;
	}
	public void setDeal_date(String deal_date) {
		this.deal_date = deal_date;
	}
	public String getDeal_time() {
		return deal_time;
	}
	public void setDeal_time(String deal_time) {
		this.deal_time = deal_time;
	}
	public String getSerial_no() {
		return serial_no;
	}
	public void setSerial_no(String serial_no) {
		this.serial_no = serial_no;
	}
	public String getCert_sign() {
		return cert_sign;
	}
	public void setCert_sign(String cert_sign) {
		this.cert_sign = cert_sign;
	}
}
