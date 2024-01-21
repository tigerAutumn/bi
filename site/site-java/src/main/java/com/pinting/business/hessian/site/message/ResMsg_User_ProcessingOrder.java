package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_User_ProcessingOrder extends ResMsg {


	
	/**
	 * 
	 */
	private static final long serialVersionUID = 367680642060689662L;
	private Integer processingNum;//当前用户处理中订单的数量

	public Integer getProcessingNum() {
		return processingNum;
	}

	public void setProcessingNum(Integer processingNum) {
		this.processingNum = processingNum;
	}
	
}
