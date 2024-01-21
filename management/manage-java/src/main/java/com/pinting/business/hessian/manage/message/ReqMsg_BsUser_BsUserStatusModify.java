package com.pinting.business.hessian.manage.message;



import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_BsUser_BsUserStatusModify extends ReqMsg {


	/**
	 * 
	 */
	private static final long serialVersionUID = 5186249561338867470L;
	private Integer userId;
	private Integer status;
	private String ids;
	private String controlName;
	
	public String getControlName() {
		return controlName;
	}

	public void setControlName(String controlName) {
		this.controlName = controlName;
	}
	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
