package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * Created by Administrator on 2016/10/31.
 */
public class ReqMsg_ActivityLuckyDraw_JudgeUserDrawed161Packet extends ReqMsg {
    private static final long serialVersionUID = -4333201328292366727L;

    private Integer userId;
    
    private String redPacketName;//红包名称
    
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

	public String getRedPacketName() {
		return redPacketName;
	}

	public void setRedPacketName(String redPacketName) {
		this.redPacketName = redPacketName;
	}
}
