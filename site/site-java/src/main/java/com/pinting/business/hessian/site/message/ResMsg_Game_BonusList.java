package com.pinting.business.hessian.site.message;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_Game_BonusList extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7781882400973075437L;
	
	private ArrayList<HashMap<String, Object>> List;
	private String mobile;
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public ArrayList<HashMap<String, Object>> getList() {
		return List;
	}

	public void setList(ArrayList<HashMap<String, Object>> list) {
		List = list;
	}


	
}
