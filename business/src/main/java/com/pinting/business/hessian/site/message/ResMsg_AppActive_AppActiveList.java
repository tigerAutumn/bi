package com.pinting.business.hessian.site.message;

import java.util.List;
import java.util.Map;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_AppActive_AppActiveList extends ResMsg {

	private static final long serialVersionUID = -1388866740184526328L;

	private List<Map<String, Object>> activeList;

	public List<Map<String, Object>> getActiveList() {
		return activeList;
	}

	public void setActiveList(List<Map<String, Object>> activeList) {
		this.activeList = activeList;
	}
}
