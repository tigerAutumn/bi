package com.pinting.business.hessian.manage.message;


import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_ProductSerial_ProductSerialList extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2036939239946089573L;
	
	private int pageNum;
	
	/** 每页显示的记录数(默认为20条,可以通过set改变其数量)*/
	private int numPerPage = 20;
	
	/** 排序 */
	private String orderField;
	
	private String orderDirection;

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
