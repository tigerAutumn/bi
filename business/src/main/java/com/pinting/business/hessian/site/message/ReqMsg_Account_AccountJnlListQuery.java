package com.pinting.business.hessian.site.message;

import java.util.Map;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_Account_AccountJnlListQuery extends ReqMsg {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8539270536684458803L;
	@NotNull(message="用户编号不能为空")
	private Integer userId;
	private String nick;
	private Integer pageIndex;
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
}
