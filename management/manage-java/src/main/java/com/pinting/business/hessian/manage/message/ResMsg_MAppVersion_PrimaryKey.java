package com.pinting.business.hessian.manage.message;

import com.pinting.business.model.BsAppVersion;
import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_MAppVersion_PrimaryKey extends ResMsg {

	/**
	 * 序列化编号
	 */
	private static final long serialVersionUID = -827872631971374402L;

	private BsAppVersion appVersion;


	public BsAppVersion getAppVersion() {
		return appVersion;
	}


	public void setAppVersion(BsAppVersion appVersion) {
		this.appVersion = appVersion;
	}
}
