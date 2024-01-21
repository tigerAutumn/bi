package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ResMsg;

import java.util.ArrayList;
import java.util.HashMap;

public class ResMsg_MUserOperate_Index extends ResMsg {

	private static final long serialVersionUID = -4623479475625277680L;
	
	private ArrayList<HashMap<String, Object>> bankList;

	public ArrayList<HashMap<String, Object>> getBankList() {
		return bankList;
	}

	public void setBankList(ArrayList<HashMap<String, Object>> bankList) {
		this.bankList = bankList;
	}
}
