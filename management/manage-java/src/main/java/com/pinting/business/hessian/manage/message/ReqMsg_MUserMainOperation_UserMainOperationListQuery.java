package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_MUserMainOperation_UserMainOperationListQuery extends ReqMsg {
	
	/**
	 * 序列化编号
	 */
	private static final long serialVersionUID = -6077497964942552048L;

    private String mobile;  //手机号
	
	private String srcIp;		//投资到期结束时间
	
	/**
	 * 每页显示的记录数(默认为20条,可以通过set改变其数量)
	 */
	private int numPerPage = 100;
	/**
	 * 当前页码
	 */
	private int pageNum;
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

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getSrcIp() {
		return srcIp;
	}

	public void setSrcIp(String srcIp) {
		this.srcIp = srcIp;
	}
}
