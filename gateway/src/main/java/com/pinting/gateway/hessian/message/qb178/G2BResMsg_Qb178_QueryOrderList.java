package com.pinting.gateway.hessian.message.qb178;

import java.util.List;

import com.pinting.core.hessian.msg.ResMsg;
import com.pinting.gateway.qb178.in.model.OrderListDataResModel;

public class G2BResMsg_Qb178_QueryOrderList extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2764426429805440274L;
	/*总记录数*/
	private	Integer	total_num;
	/*当前页*/
	private	Integer	current_page;
	/*产品列表集合对象*/
	private		List<OrderListDataResModel> 	data;
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
	public List<OrderListDataResModel> getData() {
		return data;
	}
	public void setData(List<OrderListDataResModel> data) {
		this.data = data;
	}

}
