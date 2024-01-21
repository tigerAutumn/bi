package com.pinting.gateway.hessian.message.qb178;

import java.util.Date;
import com.pinting.core.hessian.msg.ReqMsg;

public class G2BReqMsg_Qb178_QueryBalanceJnl extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9213192920154947669L;

	/* 会员账号*/
	private String user_account;
	/* 创建时间开始 */
	private Date create_time_begin;
	/* 创建时间结束 */
	private Date create_time_end;
	/* 页数，起始值为1*/
	private Integer page_num;
	/* 每页记录数*/
	private Integer page_size;

	public String getUser_account() {
		return user_account;
	}
	public void setUser_account(String user_account) {
		this.user_account = user_account;
	}
	public Date getCreate_time_begin() {
		return create_time_begin;
	}
	public void setCreate_time_begin(Date create_time_begin) {
		this.create_time_begin = create_time_begin;
	}
	public Date getCreate_time_end() {
		return create_time_end;
	}
	public void setCreate_time_end(Date create_time_end) {
		this.create_time_end = create_time_end;
	}
	public Integer getPage_num() {
		return page_num;
	}
	public void setPage_num(Integer page_num) {
		this.page_num = page_num;
	}
	public Integer getPage_size() {
		return page_size;
	}
	public void setPage_size(Integer page_size) {
		this.page_size = page_size;
	}
}
