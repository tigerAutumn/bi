package com.pinting.business.model.vo;

import com.pinting.business.model.BsProduct;

public class BsProductUserVO extends BsProduct {
	
	private String userMobile; //用户手机号
	
	private Integer userId; //用户id
	
	private Integer pInformId; //productInform表id

	public String getUserMobile() {
		return userMobile;
	}

	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getpInformId() {
		return pInformId;
	}

	public void setpInformId(Integer pInformId) {
		this.pInformId = pInformId;
	}
}
