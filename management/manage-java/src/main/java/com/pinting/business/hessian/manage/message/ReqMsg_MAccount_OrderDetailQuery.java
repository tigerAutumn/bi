package com.pinting.business.hessian.manage.message;

import java.util.Date;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_MAccount_OrderDetailQuery extends ReqMsg {
	
	private static final long serialVersionUID = -6987339564590137076L;

	private Date beginTime;

	private Date overTime;
	
	/** 订单更新时间 */
	private Date startUpdateTime;
	
	private Date endUpdateTime;

	private int pageNum;

	/** 每页显示的记录数(默认为20条,可以通过set改变其数量)*/
	private int numPerPage = 20;

	/** 排序 */
	private String orderField;

	private String orderDirection;

	private String mobile;

	private String userName;
	
	/** 交易类型 */
	private String transType;
	
	/** 订单状态 */
	private Integer status;
	
	private String orderNo;
	
	private String queryFlag;

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

	public Date getStartUpdateTime() {
		return startUpdateTime;
	}

	public void setStartUpdateTime(Date startUpdateTime) {
		this.startUpdateTime = startUpdateTime;
	}

	public Date getEndUpdateTime() {
		return endUpdateTime;
	}

	public void setEndUpdateTime(Date endUpdateTime) {
		this.endUpdateTime = endUpdateTime;
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

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getQueryFlag() {
		return queryFlag;
	}

	public void setQueryFlag(String queryFlag) {
		this.queryFlag = queryFlag;
	}

}
