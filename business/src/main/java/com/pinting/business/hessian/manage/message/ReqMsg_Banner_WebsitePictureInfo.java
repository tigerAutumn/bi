package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_Banner_WebsitePictureInfo extends ReqMsg {

	private static final long serialVersionUID = 3667281437631088095L;
	

	private Integer bannerId;

	public Integer getBannerId() {
		return bannerId;
	}

	public void setBannerId(Integer bannerId) {
		this.bannerId = bannerId;
	}
	
}
