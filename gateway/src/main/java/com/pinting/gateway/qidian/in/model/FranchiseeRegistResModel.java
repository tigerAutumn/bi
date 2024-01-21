package com.pinting.gateway.qidian.in.model;

import java.util.List;

/**
 * 
 * @project gateway
 * @title FranchiseeRegistResModel.java
 * @author Dragon & cat
 * @date 2018-3-20
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description 店主创建ResModel
 */
public class FranchiseeRegistResModel extends BaseResModel {
	/*店主开通结果列表*/
	private 	List<FranchiseeResult> 		franchiseeResult;

	public List<FranchiseeResult> getFranchiseeResult() {
		return franchiseeResult;
	}

	public void setFranchiseeResult(List<FranchiseeResult> franchiseeResult) {
		this.franchiseeResult = franchiseeResult;
	}
	
}
