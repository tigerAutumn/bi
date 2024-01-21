package com.pinting.gateway.hessian.message.hfbank;

import java.util.List;

import com.pinting.core.hessian.msg.ResMsg;
import com.pinting.gateway.hessian.message.hfbank.model.EstablishOrAbandonResData;

public class B2GResMsg_HFBank_EstablishOrAbandon extends ResMsg {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4563903164685815134L;


	/*业务数据*/
	private		EstablishOrAbandonResData  data;
	

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


	public EstablishOrAbandonResData getData() {
		return data;
	}

	public void setData(EstablishOrAbandonResData data) {
		this.data = data;
	}
}
