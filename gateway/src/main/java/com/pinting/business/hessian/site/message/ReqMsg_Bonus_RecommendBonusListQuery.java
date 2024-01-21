package com.pinting.business.hessian.site.message;

import java.util.Map;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.pinting.core.hessian.msg.ReqMsg;
/**
 * 获取用户奖励金列表 入参
 * @author shiyulong
 * @2015-12-18 下午4:28:23
 */
public class ReqMsg_Bonus_RecommendBonusListQuery extends ReqMsg {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6987597407504516173L;
	/*用户id*/
	@NotNull(message="用户编号不能为空")
	private Integer userId;
	/*用户昵称*/
	private String nick;
	/*收益类型 默认 2：推荐奖励收益明细*/
	@Pattern(regexp="^[2]{1}$",message="收益类型有误")
	private String earningsType = "2";
	/*可提现标志 默认 1：查询所有推荐奖励收益明细*/
	@Pattern(regexp="^[12]{1}$",message="可提现标志有误")
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
