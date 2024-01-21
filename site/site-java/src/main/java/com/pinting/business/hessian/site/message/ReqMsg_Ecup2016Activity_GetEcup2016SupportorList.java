package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;
/**
 * 欧洲杯活动-好友助力列表查询 入参
 * @author bianyatian
 * @2016-6-23 上午11:10:55
 */
public class ReqMsg_Ecup2016Activity_GetEcup2016SupportorList extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4011483286291334111L;

	/*用户id*/
	private Integer userId;
	/*起始页*/
	private Integer pageIndex;
	/*每页条数*/
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
