package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ReqMsg;
/**
 * 银行维护
 * @author caonengwen
 *
 */
public class ReqMsg_BsBank_BankListQuery  extends ReqMsg{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 254803388029386155L;
	private String pageNum;
	private String numPerPage;
	/** 银行名称 **/
	private String name;
	/** 是否可用**/
	private Integer status;//1-正常  2-不可用
	
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
}
