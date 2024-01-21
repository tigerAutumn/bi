package com.pinting.gateway.hfbank.out.model;

import java.util.List;
/**
 * 
 * @project gateway
 * @title PublishResModel.java
 * @author Dragon & cat
 * @date 2017-4-1
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description 恒丰银行存管 ，标的发布返回
 */
public class PublishResModel extends BaseResModel {
	/*返回业务数据*/
	private 	PublishResData data;


	public PublishResData getData() {
		return data;
	}

	public void setData(PublishResData data) {
		this.data = data;
	}
}
