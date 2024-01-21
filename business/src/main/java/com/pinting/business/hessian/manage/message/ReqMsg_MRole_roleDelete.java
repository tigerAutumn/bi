package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_MRole_roleDelete extends ReqMsg {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8750853412999869303L;

	private Integer id;//	用户编号	必填		
		

	
	
	private String msg;




	public Integer getId() {
		return id;
	}




	public void setId(Integer id) {
		this.id = id;
	}




	public String getMsg() {
		return msg;
	}




	public void setMsg(String msg) {
		this.msg = msg;
	}


	
}
