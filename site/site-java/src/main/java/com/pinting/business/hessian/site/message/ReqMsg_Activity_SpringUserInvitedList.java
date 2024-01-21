package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 踏春活动-用户邀请列表
 * @author bianyatian
 * @2017-3-24 下午7:20:27
 */
public class ReqMsg_Activity_SpringUserInvitedList extends ReqMsg {

	private static final long serialVersionUID = -574523465927586024L;
	
	private Integer userId;
    
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

}
