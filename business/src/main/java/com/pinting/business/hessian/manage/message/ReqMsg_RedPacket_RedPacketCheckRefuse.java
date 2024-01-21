package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_RedPacket_RedPacketCheckRefuse extends ReqMsg {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4045572418160826232L;
	private Integer id;
	private Integer muserId;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getMuserId() {
		return muserId;
	}
	public void setMuserId(Integer muserId) {
		this.muserId = muserId;
	}
	
	
}
