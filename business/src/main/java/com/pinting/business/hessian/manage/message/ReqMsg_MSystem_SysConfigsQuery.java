package com.pinting.business.hessian.manage.message;

import javax.validation.constraints.NotNull;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_MSystem_SysConfigsQuery extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6683655327193887145L;
	
	private String userId;
	private String roleId;
	@NotNull(message="页码不能为空")
	private String pageNum;
	@NotNull(message="每页条数不能为空")
	private String numPerPage;
	
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
	

}
