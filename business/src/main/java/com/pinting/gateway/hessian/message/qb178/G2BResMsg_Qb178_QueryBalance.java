package com.pinting.gateway.hessian.message.qb178;

import java.util.List;
import com.pinting.core.hessian.msg.ResMsg;
import com.pinting.gateway.hessian.message.qb178.model.QueryBalanceInfo;

public class G2BResMsg_Qb178_QueryBalance extends ResMsg {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5592467745376552339L;
	/* 会员账号余额情况 */
	private List<QueryBalanceInfo> data;
	/*总记录数*/
	private	Integer	total_num;
	/*当前页*/
	private	Integer	current_page;
	
	public List<QueryBalanceInfo> getData() {
		return data;
	}
	public void setData(List<QueryBalanceInfo> data) {
		this.data = data;
	}
	public Integer getTotal_num() {
		return total_num;
	}
	public void setTotal_num(Integer total_num) {
		this.total_num = total_num;
	}
	public Integer getCurrent_page() {
		return current_page;
	}
	public void setCurrent_page(Integer current_page) {
		this.current_page = current_page;
	}
	
}
