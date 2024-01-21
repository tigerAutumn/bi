package com.pinting.business.hessian.manage.message;


import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_BsUser_BsSubUserListQuery extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8517720232675651894L;

	private Integer userId;
	
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	/**
	 * 每页显示的记录数(默认为20条,可以通过set改变其数量)
	 */
	private int numPerPage = 20;
	/**
	 * 当前页码
	 */
	private int pageNum;

	public int getNumPerPage() {
		return numPerPage;
	}

	public void setNumPerPage(int numPerPage) {
		this.numPerPage = numPerPage;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	
}
