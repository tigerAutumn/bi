package com.pinting.business.hessian.site.message;

import javax.validation.constraints.NotNull;

import com.pinting.core.hessian.msg.ReqMsg;
/**
 * 据userId查询我的投资记录 入参
 * @author shiyulong
 * @2015-12-21 下午12:43:43
 */
public class ReqMsg_Invest_InvestListQuery extends ReqMsg{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9074730809291160530L;
	/*用户id*/
	@NotNull(message="用户编号不能为空")
	private Integer userId;
	/*用户昵称*/
	private String nick;
	/*起始页*/
	private Integer startPage;
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
	public Integer getStartPage() {
		return startPage;
	}
	public void setStartPage(Integer startPage) {
		this.startPage = startPage;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	
	
	
}
