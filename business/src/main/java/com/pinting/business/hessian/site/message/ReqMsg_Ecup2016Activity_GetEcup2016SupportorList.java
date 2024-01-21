package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;
/**
 * 好友助力列表查询 入参
 * @author bianyatian
 * @2016-6-23 上午10:58:55
 */
public class ReqMsg_Ecup2016Activity_GetEcup2016SupportorList extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4011483286291334111L;

	private Integer userId;
	
	private Integer pageIndex;
	
	private Integer pageSize;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	
	
}
