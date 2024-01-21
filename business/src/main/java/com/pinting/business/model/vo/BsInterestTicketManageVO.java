package com.pinting.business.model.vo;

import java.util.Date;

/**
 * 加息券
 * @author SHENGP
 * @date  2018年4月4日 上午11:43:51
 */
public class BsInterestTicketManageVO extends PageInfoObject {

	private static final long serialVersionUID = 3981200344978866352L;

	private Integer userId; // 用户ID
	
	private String mobile; // 手机号
	
	private String serialNo; // 加息券批次号
	
	private String distributeType; // 发放类型
	
	private String serialName; // 加息券名称
	
	private String useValidTimeStart; // 有效期开始时间
	
	private String useValidTimeEnd; // 有效期结束时间
	
	private Date startUseValidTime; // 有效期开始时间
	
	private Date endUseValidTime; // 有效期结束时间
	
	private String productLimit; // 产品限制
	
	private String termLimit; // 期限限制
	
	private String investLimit; // 使用条件
	
	private Double ticketApr; // 加息幅度
	
	private Date distributeTime; // 发放时间
	
	private String distributeTimeStart; // 发放开始时间
	
	private String distributeTimeEnd; // 发放结束时间
	
	private String status; // 状态
	
	private Date useTime; // 使用时间
	
	private String useTimeStart; // 使用开始时间
	
	private String useTimeEnd; // 使用结束时间
	
	private String orderNo; // 订单编号
	
	private Double interestAmount; // 加息收益
	
	private Date lastFinishInterestDate; // 加息收益发放时间
	
	private String lastFinishInterestDateStart; // 加息收益发放开始时间
	
	private String lastFinishInterestDateEnd; // 加息收益发放结束时间
	
	private String distributeStatus; // 加息收益发放状态

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getDistributeType() {
		return distributeType;
	}

	public void setDistributeType(String distributeType) {
		this.distributeType = distributeType;
	}

	public String getSerialName() {
		return serialName;
	}

	public void setSerialName(String serialName) {
		this.serialName = serialName;
	}

	public String getProductLimit() {
		return productLimit;
	}

	public void setProductLimit(String productLimit) {
		this.productLimit = productLimit;
	}

	public String getTermLimit() {
		return termLimit;
	}

	public void setTermLimit(String termLimit) {
		this.termLimit = termLimit;
	}

	public String getInvestLimit() {
		return investLimit;
	}

	public void setInvestLimit(String investLimit) {
		this.investLimit = investLimit;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getDistributeStatus() {
		return distributeStatus;
	}

	public void setDistributeStatus(String distributeStatus) {
		this.distributeStatus = distributeStatus;
	}

	public String getUseValidTimeStart() {
		return useValidTimeStart;
	}

	public void setUseValidTimeStart(String useValidTimeStart) {
		this.useValidTimeStart = useValidTimeStart;
	}

	public String getUseValidTimeEnd() {
		return useValidTimeEnd;
	}

	public void setUseValidTimeEnd(String useValidTimeEnd) {
		this.useValidTimeEnd = useValidTimeEnd;
	}
	
	public String getDistributeTimeStart() {
		return distributeTimeStart;
	}

	public void setDistributeTimeStart(String distributeTimeStart) {
		this.distributeTimeStart = distributeTimeStart;
	}

	public String getDistributeTimeEnd() {
		return distributeTimeEnd;
	}

	public void setDistributeTimeEnd(String distributeTimeEnd) {
		this.distributeTimeEnd = distributeTimeEnd;
	}

	public String getUseTimeStart() {
		return useTimeStart;
	}

	public void setUseTimeStart(String useTimeStart) {
		this.useTimeStart = useTimeStart;
	}

	public String getUseTimeEnd() {
		return useTimeEnd;
	}

	public void setUseTimeEnd(String useTimeEnd) {
		this.useTimeEnd = useTimeEnd;
	}

	public String getLastFinishInterestDateStart() {
		return lastFinishInterestDateStart;
	}

	public void setLastFinishInterestDateStart(String lastFinishInterestDateStart) {
		this.lastFinishInterestDateStart = lastFinishInterestDateStart;
	}

	public String getLastFinishInterestDateEnd() {
		return lastFinishInterestDateEnd;
	}

	public void setLastFinishInterestDateEnd(String lastFinishInterestDateEnd) {
		this.lastFinishInterestDateEnd = lastFinishInterestDateEnd;
	}

	public Date getDistributeTime() {
		return distributeTime;
	}

	public void setDistributeTime(Date distributeTime) {
		this.distributeTime = distributeTime;
	}

	public Date getUseTime() {
		return useTime;
	}

	public void setUseTime(Date useTime) {
		this.useTime = useTime;
	}

	public Date getLastFinishInterestDate() {
		return lastFinishInterestDate;
	}

	public void setLastFinishInterestDate(Date lastFinishInterestDate) {
		this.lastFinishInterestDate = lastFinishInterestDate;
	}

	public Double getTicketApr() {
		return ticketApr;
	}

	public void setTicketApr(Double ticketApr) {
		this.ticketApr = ticketApr;
	}

	public Double getInterestAmount() {
		return interestAmount;
	}

	public void setInterestAmount(Double interestAmount) {
		this.interestAmount = interestAmount;
	}

	public Date getStartUseValidTime() {
		return startUseValidTime;
	}

	public void setStartUseValidTime(Date startUseValidTime) {
		this.startUseValidTime = startUseValidTime;
	}

	public Date getEndUseValidTime() {
		return endUseValidTime;
	}

	public void setEndUseValidTime(Date endUseValidTime) {
		this.endUseValidTime = endUseValidTime;
	}

}
