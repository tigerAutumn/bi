package com.pinting.business.model.vo;

import java.util.Date;

/**
 * 对账 可疑记录对象
 * @author SHENGUOPING
 * @date  2017年12月29日 下午3:45:35
 */
public class BsPayCheckErrorVO {

	// 备注-业务类型
	private String note;
	
	// 订单号
	private String orderNo;
	
	// 状态，数据库存值
	private String sysStatus;
	
	// 状态，页面显示
	private String transStatus;
	
	// 三方支付金额
	private Double doneAmount;
	
	// 本地金额
	private Double sysAmount;
	
	// 对账结果
	private String info;
	
	// 对账日期
	private Date settleDate;

	
	// 商户类型
	private String paymentChannelId;
	// 资产方
	private String partnerCode;
	// 宝付支付单号
	private String bfOrderNo;
	// 宝付订单状态
	private String hostSysStatus;
	// 业务类型
	private String businessType;
	// 渠道
	private String channel;
	
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getSysStatus() {
		return sysStatus;
	}

	public void setSysStatus(String sysStatus) {
		this.sysStatus = sysStatus;
	}

	public String getTransStatus() {
		return transStatus;
	}

	public void setTransStatus(String transStatus) {
		this.transStatus = transStatus;
	}

	public Double getDoneAmount() {
		return doneAmount;
	}

	public void setDoneAmount(Double doneAmount) {
		this.doneAmount = doneAmount;
	}

	public Double getSysAmount() {
		return sysAmount;
	}

	public void setSysAmount(Double sysAmount) {
		this.sysAmount = sysAmount;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public Date getSettleDate() {
		return settleDate;
	}

	public void setSettleDate(Date settleDate) {
		this.settleDate = settleDate;
	}

	public String getPaymentChannelId() {
		return paymentChannelId;
	}

	public void setPaymentChannelId(String paymentChannelId) {
		this.paymentChannelId = paymentChannelId;
	}

	public String getPartnerCode() {
		return partnerCode;
	}

	public void setPartnerCode(String partnerCode) {
		this.partnerCode = partnerCode;
	}

	public String getBfOrderNo() {
		return bfOrderNo;
	}

	public void setBfOrderNo(String bfOrderNo) {
		this.bfOrderNo = bfOrderNo;
	}

	public String getHostSysStatus() {
		return hostSysStatus;
	}

	public void setHostSysStatus(String hostSysStatus) {
		this.hostSysStatus = hostSysStatus;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}
	
}
