package com.pinting.gateway.hessian.message.qb178;

import java.util.List;

import com.pinting.core.hessian.msg.ResMsg;
import com.pinting.gateway.hessian.message.qb178.model.ProductListDataResModel;

public class G2BResMsg_Qb178_QueryProductList extends ResMsg{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6168387981580204286L;
	/*总记录数*/
	private	Integer	total_num;
	/*当前页*/
	private	Integer	current_page;
	/*产品列表集合对象*/
	private		List<ProductListDataResModel> 	data;
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
	public List<ProductListDataResModel> getData() {
		return data;
	}
	public void setData(List<ProductListDataResModel> data) {
		this.data = data;
	}
	
	
}
