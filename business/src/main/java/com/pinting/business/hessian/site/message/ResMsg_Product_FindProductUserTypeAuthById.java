package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_Product_FindProductUserTypeAuthById extends ResMsg {
    
	/**
	 * 
	 */
	private static final long serialVersionUID = -3890621651464808563L;

	private Integer id;

    private String userType;

    private Integer productId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}
    
    
}
