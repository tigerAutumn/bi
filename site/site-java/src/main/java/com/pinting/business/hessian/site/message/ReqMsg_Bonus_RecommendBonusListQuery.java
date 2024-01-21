package com.pinting.business.hessian.site.message;

import java.util.Map;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 获取用户奖励金列表 入参
 * @author dingpengfeng
 * @2015-1-16 下午7:17:23
 */
public class ReqMsg_Bonus_RecommendBonusListQuery extends ReqMsg {

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
	
}
