package com.pinting.business.hessian.site.message;

import java.util.ArrayList;
import java.util.HashMap;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_Fund_NetValueListQuery extends ResMsg{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8450995860539760152L;

	private ArrayList<HashMap<String,Object>> value;

	public ArrayList<HashMap<String, Object>> getValue() {
		return value;
	}

	public void setValue(ArrayList<HashMap<String, Object>> value) {
		this.value = value;
	}
	
	
	
}
