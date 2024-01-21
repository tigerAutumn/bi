package com.pinting.gateway.hessian.message.hfbank;

import com.pinting.core.hessian.msg.ResMsg;
import com.pinting.gateway.hessian.message.hfbank.model.RepayCompensateResData;


public class B2GResMsg_HFBank_RepayCompensate extends ResMsg {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 456800929201066192L;
	/*业务数据*/
	private		RepayCompensateResData  	data;
	
	
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

	public RepayCompensateResData getData() {
		return data;
	}

	public void setData(RepayCompensateResData data) {
		this.data = data;
	}
	
}
