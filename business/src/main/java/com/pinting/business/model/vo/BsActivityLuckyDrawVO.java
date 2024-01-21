package com.pinting.business.model.vo;

import java.util.Date;

import com.pinting.business.model.BsActivityLuckyDraw;

public class BsActivityLuckyDrawVO extends BsActivityLuckyDraw {
	
	private String mobile; //中奖用户手机号
	
	private String awardContent; //奖品名称
	
	private String noteAmount; //bs_activity_award表中的note
	
	private String agentName; //渠道名称

	private String userName; //用户姓名

	private String activityName; //活动名称

	private String gender; //性别
	
	private Integer userId;//用户id
	
	private String productName;//产品名称
	
	private String showTerminal;//产品终端
	
	private String orderNo;//订单号
	
	private Date openTime;//购买时间
	
	private Double purchasingPrice;//购买金额
	
	private Integer term;//期限
	
	private Double productRate;//利率
	
	private Double yearInterest;//年化出借额
	
	private Double useRedPacket;//使用红包

	private Double useTicket;//使用加息券
	
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAwardContent() {
		return awardContent;
	}

	public void setAwardContent(String awardContent) {
		this.awardContent = awardContent;
	}

	public String getNoteAmount() {
		return noteAmount;
	}

	public void setNoteAmount(String noteAmount) {
		this.noteAmount = noteAmount;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getShowTerminal() {
		return showTerminal;
	}

	public void setShowTerminal(String showTerminal) {
		this.showTerminal = showTerminal;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Date getOpenTime() {
		return openTime;
	}

	public void setOpenTime(Date openTime) {
		this.openTime = openTime;
	}

	public Double getPurchasingPrice() {
		return purchasingPrice;
	}

	public void setPurchasingPrice(Double purchasingPrice) {
		this.purchasingPrice = purchasingPrice;
	}

	public Integer getTerm() {
		return term;
	}

	public void setTerm(Integer term) {
		this.term = term;
	}

	public Double getProductRate() {
		return productRate;
	}

	public void setProductRate(Double productRate) {
		this.productRate = productRate;
	}

	public Double getYearInterest() {
		return yearInterest;
	}

	public void setYearInterest(Double yearInterest) {
		this.yearInterest = yearInterest;
	}

	public Double getUseRedPacket() {
		return useRedPacket;
	}

	public void setUseRedPacket(Double useRedPacket) {
		this.useRedPacket = useRedPacket;
	}

	public Double getUseTicket() {
		return useTicket;
	}

	public void setUseTicket(Double useTicket) {
		this.useTicket = useTicket;
	}
}
