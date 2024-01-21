package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_Product_SelectProductInform extends ResMsg {

	private static final long serialVersionUID = 2090231981966133028L;

	private boolean isExist;

	public boolean isExist() {
		return isExist;
	}

	public void setExist(boolean isExist) {
		this.isExist = isExist;
	}
}
