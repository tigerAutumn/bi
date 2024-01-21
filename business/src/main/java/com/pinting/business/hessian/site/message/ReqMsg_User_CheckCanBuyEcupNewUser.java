package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_User_CheckCanBuyEcupNewUser extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 203948751849686240L;
	
	private int userId;
	
	private int productId;

	public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

}
