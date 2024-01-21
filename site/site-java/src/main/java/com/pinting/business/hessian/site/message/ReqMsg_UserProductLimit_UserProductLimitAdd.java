package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_UserProductLimit_UserProductLimitAdd extends ReqMsg {
    
	/**
	 * 
	 */
	private static final long serialVersionUID = -3299478217797631117L;


    private Integer userId;

    private String event;   // 触发类型   REGISTER：注册 SHARE:分享  RECOMMEND：邀请

	public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }


	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

    
    
}
