package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_Product_ProductListStatusFinish30 extends ReqMsg {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7640946451924053542L;

	private  String   returnType; //回款类型
	
	private  Integer  page; // 页码  （从1开始）

	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}
	
	
}
