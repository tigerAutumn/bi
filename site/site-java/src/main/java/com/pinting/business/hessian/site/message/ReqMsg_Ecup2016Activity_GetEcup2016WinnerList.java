package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;
/**
 * 欧洲杯活动-助力值排行榜查询 入参
 * @author bianyatian
 * @2016-6-23 上午11:10:11
 */
public class ReqMsg_Ecup2016Activity_GetEcup2016WinnerList extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8712952132628532143L;

	/*起始页*/
	private Integer pageIndex;
	/*每页条数*/
	private Integer pageSize;


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
