package com.pinting.gateway.hessian.message.qb178;

import java.util.List;
import com.pinting.core.hessian.msg.ResMsg;
import com.pinting.gateway.hessian.message.qb178.model.PositionProductInfo;

public class G2BResMsg_Qb178_QueryPositionBalance extends ResMsg {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6255576350069299266L;
	
	/* 产品列表 */
	private List<PositionProductInfo> data;
	/*总记录数*/
	private	Integer	total_num;
	/*当前页*/
	private	Integer	current_page;

	public List<PositionProductInfo> getData() {
		return data;
	}
	public void setData(List<PositionProductInfo> data) {
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
