package com.pinting.business.hessian.manage.message;

import java.util.Date;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_MAccount_AssetsList extends ReqMsg {


	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -3864589961902212641L;

	private int pageNum;
	private int status;
	private Date createTime;
	
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * 每页显示的记录数(默认为20条,可以通过set改变其数量)
	 */
	private int numPerPage = 20;
	//排序
		private String orderField;
		private String orderDirection;
		
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
	public int getPageNum() {
		if (pageNum < 1) {

			this.pageNum = 1;
		}

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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}


	
}
