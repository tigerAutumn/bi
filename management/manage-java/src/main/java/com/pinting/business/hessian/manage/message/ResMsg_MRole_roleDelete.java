package com.pinting.business.hessian.manage.message;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_MRole_roleDelete extends ResMsg {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7724059908875159101L;
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
