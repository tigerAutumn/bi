package com.pinting.gateway.hessian.message.youbei;

import java.util.Map;

import com.pinting.core.hessian.msg.ResMsg;

public class B2GResMsg_YouBei_CheckRealName extends ResMsg {

	private static final long serialVersionUID = -792340299653100064L;

	private Map<String, Object> realNameModel;

	public Map<String, Object> getRealNameModel() {
		return realNameModel;
	}

	public void setRealNameModel(Map<String, Object> realNameModel) {
		this.realNameModel = realNameModel;
	}
}
