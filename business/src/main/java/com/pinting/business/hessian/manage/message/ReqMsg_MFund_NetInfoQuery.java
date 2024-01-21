package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_MFund_NetInfoQuery extends ReqMsg{

	

	/**
	 * 
	 */
	private static final long serialVersionUID = -1576895309792169199L;

	private Integer id;
	private String operateType;
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

	
}
