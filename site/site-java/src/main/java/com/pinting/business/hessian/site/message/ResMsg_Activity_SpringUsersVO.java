package com.pinting.business.hessian.site.message;

import java.util.HashMap;
import java.util.List;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_Activity_SpringUsersVO extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3896359045012449653L;

	/* 用户活动期间投资额 */
	private Double userInvestAmount;
	
	/* 用户活动期间邀请列表 */
	private List<HashMap<String, Object>> userInvitedList;
	
	/* 用户在排行榜中名次 */
	private Integer rankingNo;

	public Double getUserInvestAmount() {
		return userInvestAmount;
	}

	public void setUserInvestAmount(Double userInvestAmount) {
		this.userInvestAmount = userInvestAmount;
	}

	public List<HashMap<String, Object>> getUserInvitedList() {
		return userInvitedList;
	}

	public void setUserInvitedList(List<HashMap<String, Object>> userInvitedList) {
		this.userInvitedList = userInvitedList;
	}

	public Integer getRankingNo() {
		return rankingNo;
	}

	public void setRankingNo(Integer rankingNo) {
		this.rankingNo = rankingNo;
	}
	
}
