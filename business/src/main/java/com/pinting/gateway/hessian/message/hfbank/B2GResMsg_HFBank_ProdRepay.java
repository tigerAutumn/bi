package com.pinting.gateway.hessian.message.hfbank;

import java.util.List;

import com.pinting.core.hessian.msg.ResMsg;
import com.pinting.gateway.hessian.message.hfbank.model.ProdRepayResData;

public class B2GResMsg_HFBank_ProdRepay extends ResMsg {
	/**
	 * 
	 */
	private static final long serialVersionUID = -47499037240846217L;


	/*业务数据*/
	private 	ProdRepayResData		data;
	

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

	public ProdRepayResData getData() {
		return data;
	}

	public void setData(ProdRepayResData data) {
		this.data = data;
	}
	
}
