package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_Tag_BsTagList extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7004578526644417439L;
	
	private String content;
	
    private int pageNum;
	
	/** 每页显示的记录数(默认为100条,可以通过set改变其数量)*/
	private int numPerPage = 100;
	
	/** 排序 */
	private String orderField;
	
	private String orderDirection;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

}
