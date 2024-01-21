package com.pinting.gateway.qidian.in.model;

import java.util.List;

/**
 * 
 * @project gateway
 * @title FranchiseeRegistReqModel.java
 * @author Dragon & cat
 * @date 2018-3-20
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description 店主创建ReqModel
 */
public class FranchiseeRegistReqModel extends BaseReqModel {
	/*店主开通申请列表*/
	private  	List<Franchisees>  	franchisees;

	public List<Franchisees> getFranchisees() {
		return franchisees;
	}

	public void setFranchisees(List<Franchisees> franchisees) {
		this.franchisees = franchisees;
	}
	
}
