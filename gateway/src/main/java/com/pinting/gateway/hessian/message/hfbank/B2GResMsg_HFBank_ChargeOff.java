package com.pinting.gateway.hessian.message.hfbank;

import java.util.List;

import com.pinting.core.hessian.msg.ResMsg;
import com.pinting.gateway.hessian.message.hfbank.model.ChargeOffResData;

public class B2GResMsg_HFBank_ChargeOff extends ResMsg {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4035718184611652210L;

	/*业务数据*/
	private		ChargeOffResData  data ;

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

	public ChargeOffResData getData() {
		return data;
	}

	public void setData(ChargeOffResData data) {
		this.data = data;
	}
	
}
