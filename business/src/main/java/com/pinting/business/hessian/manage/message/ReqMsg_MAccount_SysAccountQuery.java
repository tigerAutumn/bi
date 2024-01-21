package com.pinting.business.hessian.manage.message;

import java.util.Date;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_MAccount_SysAccountQuery extends ReqMsg {
	
	private static final long serialVersionUID = -6987339564590137076L;

	private Date beginTime;

	private Date overTime;

	/** 账务时间 */
	private Date startTransTime;

	private Date endTransTime;
	
	/** 交易金额 */
	private Double startTransAmount;
	
	private Double endTransAmount;

	private int pageNum;

	/** 每页显示的记录数(默认为20条,可以通过set改变其数量)*/
	private int numPerPage = 20;

	/** 排序 */
	private String orderField;

	private String orderDirection;

	/** 交易类型 */
	private String transOtherCode;
	
	private Integer status;
	
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

	public Date getStartTransTime() {
		return startTransTime;
	}

	public void setStartTransTime(Date startTransTime) {
		this.startTransTime = startTransTime;
	}

	public Date getEndTransTime() {
		return endTransTime;
	}

	public void setEndTransTime(Date endTransTime) {
		this.endTransTime = endTransTime;
	}

	public Double getStartTransAmount() {
		return startTransAmount;
	}

	public void setStartTransAmount(Double startTransAmount) {
		this.startTransAmount = startTransAmount;
	}

	public Double getEndTransAmount() {
		return endTransAmount;
	}

	public void setEndTransAmount(Double endTransAmount) {
		this.endTransAmount = endTransAmount;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getQueryFlag() {
		return queryFlag;
	}

	public void setQueryFlag(String queryFlag) {
		this.queryFlag = queryFlag;
	}

	public String getTransOtherCode() {
		return transOtherCode;
	}

	public void setTransOtherCode(String transOtherCode) {
		this.transOtherCode = transOtherCode;
	}
	

}
