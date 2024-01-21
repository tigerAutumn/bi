package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_Product_BannerQuery extends ReqMsg {

	private static final long serialVersionUID = 1499730549439275807L;
   
    private String returnType; //回款方式

	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}
	
}
