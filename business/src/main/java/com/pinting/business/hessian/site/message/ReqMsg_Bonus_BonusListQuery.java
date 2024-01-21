package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 用户奖励金列表查询 入参
 * @author yanwenlong
 * @2015-12-22 下午5:45:28
 */
public class ReqMsg_Bonus_BonusListQuery extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7602944036064662853L;
	/*用户id*/
	private Integer userId;
	/*用户昵称*/
	private String nick;
	/*收益类型 默认 2：推荐奖励收益明细*/
	private String earningsType = "2";
	/*可提现标志 默认 1：查询所有推荐奖励收益明细*/
	private String withdrawFlag = "1"; 
	/*开始页数*/
	private Integer pageIndex;
	/*每页数量*/
	private Integer pageSize;
	/*状态标志 奖励金/推荐人 */
	private String status;
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	public String getEarningsType() {
		return earningsType;
	}
	public void setEarningsType(String earningsType) {
		this.earningsType = earningsType;
	}
	public String getWithdrawFlag() {
		return withdrawFlag;
	}
	public void setWithdrawFlag(String withdrawFlag) {
		this.withdrawFlag = withdrawFlag;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
