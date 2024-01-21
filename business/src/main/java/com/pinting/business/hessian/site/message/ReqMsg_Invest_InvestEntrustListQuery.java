package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;
/**
 * 我的投资_明细ReqMsg
 * @author Dragon & cat
 * @date 2016-8-30
 */
public class ReqMsg_Invest_InvestEntrustListQuery extends ReqMsg {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1413208063477340241L;
	/**
	 * 用户ID
	 */
	private Integer userId;
	/**
	 * 港湾计划起始页
	 */
	private Integer startPageBgw;
	/**
	 * 港湾计划每页条数
	 */
	private Integer pageSizeBgw;
	
	/**
	 * 委托计划起始页
	 */
	private Integer startPageEntrust;
	/**
	 * 委托计划每页条数
	 */
	private Integer pageSizeEntrust;
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getStartPageBgw() {
		return startPageBgw;
	}
	public void setStartPageBgw(Integer startPageBgw) {
		this.startPageBgw = startPageBgw;
	}
	public Integer getPageSizeBgw() {
		return pageSizeBgw;
	}
	public void setPageSizeBgw(Integer pageSizeBgw) {
		this.pageSizeBgw = pageSizeBgw;
	}
	public Integer getStartPageEntrust() {
		return startPageEntrust;
	}
	public void setStartPageEntrust(Integer startPageEntrust) {
		this.startPageEntrust = startPageEntrust;
	}
	public Integer getPageSizeEntrust() {
		return pageSizeEntrust;
	}
	public void setPageSizeEntrust(Integer pageSizeEntrust) {
		this.pageSizeEntrust = pageSizeEntrust;
	}
}
