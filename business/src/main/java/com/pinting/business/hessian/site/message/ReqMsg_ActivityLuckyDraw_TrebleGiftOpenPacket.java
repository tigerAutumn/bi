package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_ActivityLuckyDraw_TrebleGiftOpenPacket extends ReqMsg {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2689843146297179993L;
	
	private Integer userId;
	
	/*活动表主键id*/
	private Integer activityId;
	
	/*红包名称*/
	private String redPacketName; 

	public Integer getActivityId() {
		return activityId;
	}

	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}
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
