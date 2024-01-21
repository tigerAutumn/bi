package com.pinting.business.hessian.manage.message;

import java.util.List;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_BsSpecialJnl_ListQuery extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6099922397214988977L;
	
	private String pageNum;
	private String numPerPage;
	/**状态**/
	private Integer status;
	/**被操作用户手机姓名**/
	private String userName;
	/**被操作用户手机号码**/
	private String mobile;
	/**订单号**/
	private String orderNo;
	/**操作类型**/
	private String type;
	
	private List<String> typeList;
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<String> getTypeList() {
		return typeList;
	}
	public void setTypeList(List<String> typeList) {
		this.typeList = typeList;
	}

}
