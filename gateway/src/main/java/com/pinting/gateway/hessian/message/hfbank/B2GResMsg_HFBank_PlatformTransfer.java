package com.pinting.gateway.hessian.message.hfbank;

import com.pinting.core.hessian.msg.ResMsg;

public class B2GResMsg_HFBank_PlatformTransfer extends ResMsg {
    /**
	 * 
	 */
	private static final long serialVersionUID = 8560641871954105705L;

	/* 返回码，10000为成功 */
    private String recode;

    /* 返回结果描述 */
    private String remsg;

    	public String getRecode() {
		return recode;
	}

	public void setRecode(String recode) {
		this.recode = recode;
	}

	public String getRemsg() {
		return remsg;
	}

	public void setRemsg(String remsg) {
		this.remsg = remsg;
	}

}
