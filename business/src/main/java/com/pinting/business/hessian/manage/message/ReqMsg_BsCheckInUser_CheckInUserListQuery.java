package com.pinting.business.hessian.manage.message;


import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 2016年终抽奖管理   入参
 * Created by shh on 2016/11/28 15:08.
 */
public class ReqMsg_BsCheckInUser_CheckInUserListQuery extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3724965923307178971L;
	
	private String mobile;
	
	/* 是否注册*/
	private String isRegisterTime;
	
	/* 是否中奖 */
	private String isWin;
	
	/* 奖品id */
	private String activityAwardId;
	
	private Integer pageNum;
	
	/** 每页显示的记录数(默认为20条,可以通过set改变其数量)*/
	private Integer numPerPage = 20;
	
	/** 排序 */
	private String orderField;
	
	private String orderDirection;

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getIsRegisterTime() {
		return isRegisterTime;
	}

	public void setIsRegisterTime(String isRegisterTime) {
		this.isRegisterTime = isRegisterTime;
	}

	public String getIsWin() {
		return isWin;
	}

	public void setIsWin(String isWin) {
		this.isWin = isWin;
	}

	public String getActivityAwardId() {
		return activityAwardId;
	}

	public void setActivityAwardId(String activityAwardId) {
		this.activityAwardId = activityAwardId;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public Integer getNumPerPage() {
		return numPerPage;
	}

	public void setNumPerPage(Integer numPerPage) {
		this.numPerPage = numPerPage;
	}

	public String getOrderField() {
		return orderField;
	}

	public void setOrderField(String orderField) {
		this.orderField = orderField;
	}

	public String getOrderDirection() {
		return orderDirection;
	}

	public void setOrderDirection(String orderDirection) {
		this.orderDirection = orderDirection;
	}

}
