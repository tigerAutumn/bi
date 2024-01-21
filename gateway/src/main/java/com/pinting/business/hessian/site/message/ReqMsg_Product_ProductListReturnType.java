package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_Product_ProductListReturnType extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5207894359013566425L;
	
	private  String   returnType; //回款类型

	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}
	
	

}
