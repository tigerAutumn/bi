package com.pinting.business.hessian.manage.message;

import java.util.Date;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_MRole_roleQuery extends ReqMsg {


	
	

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5969391076949461769L;

	private Integer id;//	用户编号	必填		
		
	private String name;//	用户姓名	可选		
	
	private Integer mUserId;//登录用户id 选填，在角色权限分配时填写登录者信息
	
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


	public Integer getmUserId() {
		return mUserId;
	}


	public void setmUserId(Integer mUserId) {
		this.mUserId = mUserId;
	}

	
	
}
