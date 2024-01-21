package com.pinting.gateway.hessian.message.qb178;

import java.util.Date;
import java.util.List;

import com.pinting.core.hessian.msg.ReqMsg;

public class G2BReqMsg_Qb178_QueryBalance extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9087268994413545224L;
	/* 会员账号列表*/
	private List<String> user_account_ary_list;
	/* 创建时间开始 */
	private Date create_time_begin;
	/* 创建时间结束 */
	private Date create_time_end;
	/* 页数，起始值为1*/
	private Integer page_num;
	/* 每页记录数*/
	private Integer page_size;
	
	public List<String> getUser_account_ary_list() {
		return user_account_ary_list;
	}
	public void setUser_account_ary_list(List<String> user_account_ary_list) {
		this.user_account_ary_list = user_account_ary_list;
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
