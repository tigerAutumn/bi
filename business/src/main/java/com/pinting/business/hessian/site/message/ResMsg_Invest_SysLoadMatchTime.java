package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_Invest_SysLoadMatchTime extends ResMsg{

	private static final long serialVersionUID = -418987401112818136L;
	
	private String loadMatchTime;

	public String getLoadMatchTime() {
		return loadMatchTime;
	}

	public void setLoadMatchTime(String loadMatchTime) {
		this.loadMatchTime = loadMatchTime;
	}
}
