package com.pinting.business.hessian.site.message;

import java.util.HashMap;
import java.util.List;

import com.pinting.core.hessian.msg.ResMsg;
/**
 * 踏春活动-用户邀请列表
 * @author bianyatian
 * @2017-3-24 下午7:21:11
 */
public class ResMsg_Activity_SpringUserInvitedList extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2379217109384762009L;

	/* 用户邀请列表 */
    private List<HashMap<String, Object>> invitedList;

	public List<HashMap<String, Object>> getInvitedList() {
		return invitedList;
	}

	public void setInvitedList(List<HashMap<String, Object>> invitedList) {
		this.invitedList = invitedList;
	}
}
