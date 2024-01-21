package com.pinting.gateway.hessian.message.qidian;

import java.util.List;

import com.pinting.core.hessian.msg.ReqMsg;
import com.pinting.gateway.hessian.message.qidian.model.Customers;

public class B2GReqMsg_QiDianNotice_CustomerInfoSync extends ReqMsg {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5583474082263420419L;
	/*1.客户注册；2.用户绑卡；3.用户登录*/
	private		Integer		type;
	/*客户信息列表*/
	private 	List<Customers>  customers;
	
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public List<Customers> getCustomers() {
		return customers;
	}
	public void setCustomers(List<Customers> customers) {
		this.customers = customers;
	}
	
	
}
