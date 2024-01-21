package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_BsUserBonus_Bonus2JSH extends ReqMsg {
	/**
	 * 
	 */
	private static final long serialVersionUID = -980760136508610497L;
	private Integer id; //bs_special_jnl表id
	private Integer mUserId; //管理台用户id
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getmUserId() {
		return mUserId;
	}
	public void setmUserId(Integer mUserId) {
		this.mUserId = mUserId;
	}

}
