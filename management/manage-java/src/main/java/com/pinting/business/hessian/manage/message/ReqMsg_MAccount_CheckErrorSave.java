package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_MAccount_CheckErrorSave extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5586775182029995410L;
	
	
	private int id;
	
	private int mUserid;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getmUserid() {
		return mUserid;
	}

	public void setmUserid(int mUserid) {
		this.mUserid = mUserid;
	}
	
	
}
