package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_BsUser_FeedbacksQuery extends ReqMsg {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8604206543394820465L;
	private String userId;
	private String roleId;
	private String pageNum;
	private String numPerPage;
    private String orderField;
    private String orderDirection;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getPageNum() {
		return pageNum;
	}
	public void setPageNum(String pageNum) {
		this.pageNum = pageNum;
	}
	public String getNumPerPage() {
		return numPerPage;
	}
	public void setNumPerPage(String numPerPage) {
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
