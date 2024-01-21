package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;
/**
 * 根据matchId查询债权关系还款明细 入参
 * @author bianyatian
 * @2016-4-22 上午10:37:08
 */
public class ReqMsg_Match_GetMatchRepayDetailList extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6066289488369001881L;
	/*债权匹配表id*/
	private Integer matchId;

	/*资产合作方类型*/
	private String propertySymbol;

	/*子账户id*/
	private String subAccountId;
	
	/*迁移前的还款标志*/
	private String repayFlag;

	public Integer getMatchId() {
		return matchId;
	}

	public void setMatchId(Integer matchId) {
		this.matchId = matchId;
	}

	public String getPropertySymbol() {
		return propertySymbol;
	}

	public void setPropertySymbol(String propertySymbol) {
		this.propertySymbol = propertySymbol;
	}

	public String getSubAccountId() {
		return subAccountId;
	}

	public void setSubAccountId(String subAccountId) {
		this.subAccountId = subAccountId;
	}

	public String getRepayFlag() {
		return repayFlag;
	}

	public void setRepayFlag(String repayFlag) {
		this.repayFlag = repayFlag;
	}
}
