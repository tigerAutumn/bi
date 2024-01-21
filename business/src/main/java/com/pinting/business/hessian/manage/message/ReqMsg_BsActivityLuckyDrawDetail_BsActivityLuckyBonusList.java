package com.pinting.business.hessian.manage.message;

import java.util.Date;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_BsActivityLuckyDrawDetail_BsActivityLuckyBonusList extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1324659058464160586L;
	
	private String mobile;
	
	private Date beginTime;
	
	private Date overTime;
	
	/** 活动id */
	private Integer acId;
	
	private String ids;
	
	/** 是否中奖: 0  未中 , 1  中奖*/
	private String isWinOther;
	
	private int pageNum;
	
	/** 每页显示的记录数(默认为20条,可以通过set改变其数量)*/
	private int numPerPage = 20;
	
	/** 排序 */
	private String orderField;
	
	private String orderDirection;
	
	private String queryDateFlag;

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getOverTime() {
		return overTime;
	}

	public void setOverTime(Date overTime) {
		this.overTime = overTime;
	}

	public Integer getAcId() {
		return acId;
	}

	public void setAcId(Integer acId) {
		this.acId = acId;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getIsWinOther() {
		return isWinOther;
	}

	public void setIsWinOther(String isWinOther) {
		this.isWinOther = isWinOther;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getNumPerPage() {
		return numPerPage;
	}

	public void setNumPerPage(int numPerPage) {
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

	public String getQueryDateFlag() {
		return queryDateFlag;
	}

	public void setQueryDateFlag(String queryDateFlag) {
		this.queryDateFlag = queryDateFlag;
	}
	
}
