package com.pinting.gateway.hfbank.out.model;

import java.io.Serializable;

/**
 * 
 * @project gateway
 * @title PublishResData.java
 * @author Dragon & cat
 * @date 2017-4-1
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description 恒丰银行存管 ，标的发布请求返回业务数据
 */
public class PublishResData  implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3903545003893844679L;
	/*产品编号*/
	private		String 		prod_id;

	public String getProd_id() {
		return prod_id;
	}

	public void setProd_id(String prod_id) {
		this.prod_id = prod_id;
	}
	
}
