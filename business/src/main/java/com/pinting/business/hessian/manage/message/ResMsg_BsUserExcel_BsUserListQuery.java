package com.pinting.business.hessian.manage.message;

import java.util.ArrayList;
import java.util.HashMap;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_BsUserExcel_BsUserListQuery extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -410693416613591846L;
	
    private ArrayList<HashMap<String,Object>> userList;

	public ArrayList<HashMap<String, Object>> getUserList() {
		return userList;
	}

	public void setUserList(ArrayList<HashMap<String, Object>> userList) {
		this.userList = userList;
	}
}
