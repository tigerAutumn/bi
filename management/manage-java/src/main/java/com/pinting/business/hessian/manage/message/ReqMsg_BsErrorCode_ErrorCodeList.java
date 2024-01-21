package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_BsErrorCode_ErrorCodeList extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5958130537669516052L;
	
	/** 渠道类型 */
	private String errorCodeChannel;
	
    /** 接口类型 */
	private String interfaceType;

	/** 错误码 */
	private String errorCodeOther;

	/** 错误码内部描述 */
	private String errorInMsg;

	/** 错误码外部描述 */
	private String errorOutMsg;
	
	private int pageNum;
	
	/** 每页显示的记录数(默认为20条,可以通过set改变其数量)*/
	private int numPerPage = 20;
	
	/** 排序 */
	private String orderField;
	
	private String orderDirection;

	public String getErrorCodeChannel() {
		return errorCodeChannel;
	}

	public void setErrorCodeChannel(String errorCodeChannel) {
		this.errorCodeChannel = errorCodeChannel;
	}

	public String getInterfaceType() {
		return interfaceType;
	}

	public void setInterfaceType(String interfaceType) {
		this.interfaceType = interfaceType;
	}

	public String getErrorCodeOther() {
		return errorCodeOther;
	}

	public void setErrorCodeOther(String errorCodeOther) {
		this.errorCodeOther = errorCodeOther;
	}

	public String getErrorInMsg() {
		return errorInMsg;
	}

	public void setErrorInMsg(String errorInMsg) {
		this.errorInMsg = errorInMsg;
	}

	public String getErrorOutMsg() {
		return errorOutMsg;
	}

	public void setErrorOutMsg(String errorOutMsg) {
		this.errorOutMsg = errorOutMsg;
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
