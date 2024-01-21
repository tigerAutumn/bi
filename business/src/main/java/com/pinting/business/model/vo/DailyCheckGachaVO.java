package com.pinting.business.model.vo;

import java.util.Date;

/**
 * 还款日常管理（ 日常对账轧差信息）
 * @author SHENGUOPING
 * @date  2018年1月4日 下午3:02:35
 */
public class DailyCheckGachaVO extends PageInfoObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1631158731969221566L;
	
	// 序号
	private Integer serialNo;
	// 交易类型
	private String transType;
	// 币港湾订单号
	private String orderNo;
	// 三方订单号
	private String thdOrderNo;
	// 交易金额
	private Double transAmount;
	// 状态
	private String status;
	// 状态描述
	private String statusRemark;
	// 请求时间
	private Date requestTime;
	// 完成时间
	private Date finishTime;
	
	// 合作方
	private String partnerCode;
	// 商户通道
	private String paymentChannelId;
	// 开始时间
	private String startTime;
	// 结束时间
	private String endTime;
		
	// 查询名称 
	private String selectName;
	
	public Integer getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(Integer serialNo) {
		this.serialNo = serialNo;
	}
	public String getTransType() {
		return transType;
	}
	public void setTransType(String transType) {
		this.transType = transType;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getThdOrderNo() {
		return thdOrderNo;
	}
	public void setThdOrderNo(String thdOrderNo) {
		this.thdOrderNo = thdOrderNo;
	}
	public Double getTransAmount() {
		return transAmount;
	}
	public void setTransAmount(Double transAmount) {
		this.transAmount = transAmount;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatusRemark() {
		return statusRemark;
	}
	public void setStatusRemark(String statusRemark) {
		this.statusRemark = statusRemark;
	}
	public Date getRequestTime() {
		return requestTime;
	}
	public void setRequestTime(Date requestTime) {
		this.requestTime = requestTime;
	}
	public Date getFinishTime() {
		return finishTime;
	}
	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}
	public String getPartnerCode() {
		return partnerCode;
	}
	public void setPartnerCode(String partnerCode) {
		this.partnerCode = partnerCode;
	}
	public String getPaymentChannelId() {
		return paymentChannelId;
	}
	public void setPaymentChannelId(String paymentChannelId) {
		this.paymentChannelId = paymentChannelId;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getSelectName() {
		return selectName;
	}
	public void setSelectName(String selectName) {
		this.selectName = selectName;
	}
	
}
