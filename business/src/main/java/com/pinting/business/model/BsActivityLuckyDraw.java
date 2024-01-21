package com.pinting.business.model;

import java.util.Date;

public class BsActivityLuckyDraw {
    private Integer id;

	private Integer userId;

	private Integer activityId;

	private Integer awardId;

	private String isBackDraw;

	private Date backDrawTime;

	private String isUserDraw;

	private Date userDrawTime;

	private String isWin;

	private String isConfirm;

	private Date confirmTime;

	private String isAutoConfirm;

	private Integer opUserId;

	private String note;

	private Integer couponId;

	private String couponType;

	private String orderNo;

	private Double yearInterest;

	private Integer subAccountId;

	private Date createTime;

	private Date updateTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getActivityId() {
		return activityId;
	}

	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}

	public Integer getAwardId() {
		return awardId;
	}

	public void setAwardId(Integer awardId) {
		this.awardId = awardId;
	}

	public String getIsBackDraw() {
		return isBackDraw;
	}

	public void setIsBackDraw(String isBackDraw) {
		this.isBackDraw = isBackDraw;
	}

	public Date getBackDrawTime() {
		return backDrawTime;
	}

	public void setBackDrawTime(Date backDrawTime) {
		this.backDrawTime = backDrawTime;
	}

	public String getIsUserDraw() {
		return isUserDraw;
	}

	public void setIsUserDraw(String isUserDraw) {
		this.isUserDraw = isUserDraw;
	}

	public Date getUserDrawTime() {
		return userDrawTime;
	}

	public void setUserDrawTime(Date userDrawTime) {
		this.userDrawTime = userDrawTime;
	}

	public String getIsWin() {
		return isWin;
	}

	public void setIsWin(String isWin) {
		this.isWin = isWin;
	}

	public String getIsConfirm() {
		return isConfirm;
	}

	public void setIsConfirm(String isConfirm) {
		this.isConfirm = isConfirm;
	}

	public Date getConfirmTime() {
		return confirmTime;
	}

	public void setConfirmTime(Date confirmTime) {
		this.confirmTime = confirmTime;
	}

	public String getIsAutoConfirm() {
		return isAutoConfirm;
	}

	public void setIsAutoConfirm(String isAutoConfirm) {
		this.isAutoConfirm = isAutoConfirm;
	}

	public Integer getOpUserId() {
		return opUserId;
	}

	public void setOpUserId(Integer opUserId) {
		this.opUserId = opUserId;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Integer getCouponId() {
		return couponId;
	}

	public void setCouponId(Integer couponId) {
		this.couponId = couponId;
	}

	public String getCouponType() {
		return couponType;
	}

	public void setCouponType(String couponType) {
		this.couponType = couponType;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Double getYearInterest() {
		return yearInterest;
	}

	public void setYearInterest(Double yearInterest) {
		this.yearInterest = yearInterest;
	}

	public Integer getSubAccountId() {
		return subAccountId;
	}

	public void setSubAccountId(Integer subAccountId) {
		this.subAccountId = subAccountId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}