package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 公众号渠道二维码
 *
 * @author shh
 * @date 2018/6/20 11:37
 * @Copyright: 2018 BiGangWan.com Inc. All rights reserved.
 */
public class ReqMsg_WxUser_SubscribeWxAgent extends ReqMsg {

	private static final long serialVersionUID = -6818578870384190091L;
	private String openId;
	private String userId;
	private String accessToken;
	/* 公众号渠道二维码编号 */
	private Integer wxAgentId;
	
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public Integer getWxAgentId() { return wxAgentId; }
	public void setWxAgentId(Integer wxAgentId) { this.wxAgentId = wxAgentId; }
}
