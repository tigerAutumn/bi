package com.pinting.business.hessian.manage.message;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_MRole_roleSave extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7002330930179656218L;

	private Integer id;//	用户编号	必填		
		
	private String name;//	用户姓名	可选		
	
	
	private String note;
	
	private Date create_time;
	
	public Date getCreate_time() {
		return create_time;
	}


	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getNote() {
		return note;
	}


	public void setNote(String note) {
		this.note = note;
	}

	
		

}
