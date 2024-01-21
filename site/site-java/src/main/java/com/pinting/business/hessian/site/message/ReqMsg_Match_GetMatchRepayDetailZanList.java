package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_Match_GetMatchRepayDetailZanList extends ReqMsg {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1983889317921773955L;
	/*债权匹配表id*/
	private Integer matchId;

	public Integer getMatchId() {
		return matchId;
	}

	public void setMatchId(Integer matchId) {
		this.matchId = matchId;
	}

}
