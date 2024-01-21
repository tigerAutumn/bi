package com.pinting.gateway.hessian.message.hfbank;

import java.util.List;

import com.pinting.core.hessian.msg.ResMsg;
import com.pinting.gateway.hessian.message.hfbank.model.PublishResData;

public class B2GResMsg_HFBank_Publish extends ResMsg {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1325293787508956683L;
	/*返回业务数据*/
	private 	PublishResData data;
	

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


	public PublishResData getData() {
		return data;
	}

	public void setData(PublishResData data) {
		this.data = data;
	}
}
