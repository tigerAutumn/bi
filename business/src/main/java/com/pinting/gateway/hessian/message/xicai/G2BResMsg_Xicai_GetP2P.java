package com.pinting.gateway.hessian.message.xicai;

import java.util.ArrayList;
import java.util.HashMap;

import com.pinting.core.hessian.msg.ResMsg;
import com.pinting.gateway.hessian.message.xicai.model.ProductInfo;

public class G2BResMsg_Xicai_GetP2P extends ResMsg {

	private static final long serialVersionUID = 5983778143455445981L;

	private Integer code;
	
	private HashMap<String, Object> data;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public HashMap<String, Object> getData() {
		return data;
	}

	public void setData(HashMap<String, Object> data) {
		this.data = data;
	}

	

	
}
