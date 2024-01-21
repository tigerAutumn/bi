package com.pinting.business.hessian.manage.message;

import java.util.Date;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_MUserOperate_UserOperateQuery extends ReqMsg {
	/**
	 * 序列化编号
	 */
	private static final long serialVersionUID = -4623479475625277680L;
	
	private int pageNum;
	
	private int numPerPage = 20;
	
	private Integer bankId;
	
	private Date sLastBuyTime;
	
	private Date eLastBuyTime;
	
	private Date sFirstBuyTime;
	
	private Date eFirstBuyTime;
	
	private Integer sBuyTimes;
	
	private Integer eBuyTimes;
	
	private String sInvestMoney;
	
	private String eInvestMoney;
	
	private String sInvestTotalMoney;
	
	private String eInvestTotalMoney;
	
	private String sTotalBonus;
	
	private String eTotalBonus;
	
	private Date sRegistTime;
	
	private Date eRegistTime;
	
	private String agentIds;
	
	private String nonAgentId;
	
	private String userIds;

	public Integer getBankId() {
		return bankId;
	}

	public void setBankId(Integer bankId) {
		this.bankId = bankId;
	}

	public Date getsLastBuyTime() {
		return sLastBuyTime;
	}

	public void setsLastBuyTime(Date sLastBuyTime) {
		this.sLastBuyTime = sLastBuyTime;
	}

	public Date geteLastBuyTime() {
		return eLastBuyTime;
	}

	public void seteLastBuyTime(Date eLastBuyTime) {
		this.eLastBuyTime = eLastBuyTime;
	}

	public Date getsFirstBuyTime() {
		return sFirstBuyTime;
	}

	public void setsFirstBuyTime(Date sFirstBuyTime) {
		this.sFirstBuyTime = sFirstBuyTime;
	}

	public Date geteFirstBuyTime() {
		return eFirstBuyTime;
	}

	public void seteFirstBuyTime(Date eFirstBuyTime) {
		this.eFirstBuyTime = eFirstBuyTime;
	}

	public Integer getsBuyTimes() {
		return sBuyTimes;
	}

	public void setsBuyTimes(Integer sBuyTimes) {
		this.sBuyTimes = sBuyTimes;
	}

	public Integer geteBuyTimes() {
		return eBuyTimes;
	}

	public void seteBuyTimes(Integer eBuyTimes) {
		this.eBuyTimes = eBuyTimes;
	}

	public String getsInvestMoney() {
		return sInvestMoney;
	}

	public void setsInvestMoney(String sInvestMoney) {
		this.sInvestMoney = sInvestMoney;
	}

	public String geteInvestMoney() {
		return eInvestMoney;
	}

	public void seteInvestMoney(String eInvestMoney) {
		this.eInvestMoney = eInvestMoney;
	}

	public String getsInvestTotalMoney() {
		return sInvestTotalMoney;
	}

	public void setsInvestTotalMoney(String sInvestTotalMoney) {
		this.sInvestTotalMoney = sInvestTotalMoney;
	}

	public String geteInvestTotalMoney() {
		return eInvestTotalMoney;
	}

	public void seteInvestTotalMoney(String eInvestTotalMoney) {
		this.eInvestTotalMoney = eInvestTotalMoney;
	}

	public Date getsRegistTime() {
		return sRegistTime;
	}

	public void setsRegistTime(Date sRegistTime) {
		this.sRegistTime = sRegistTime;
	}

	public Date geteRegistTime() {
		return eRegistTime;
	}

	public void seteRegistTime(Date eRegistTime) {
		this.eRegistTime = eRegistTime;
	}

	public String getAgentIds() {
		return agentIds;
	}

	public void setAgentIds(String agentIds) {
		this.agentIds = agentIds;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getNumPerPage() {
		return numPerPage;
	}

	public void setNumPerPage(int numPerPage) {
		this.numPerPage = numPerPage;
	}

	public String getNonAgentId() {
		return nonAgentId;
	}

	public void setNonAgentId(String nonAgentId) {
		this.nonAgentId = nonAgentId;
	}

	public String getsTotalBonus() {
		return sTotalBonus;
	}

	public void setsTotalBonus(String sTotalBonus) {
		this.sTotalBonus = sTotalBonus;
	}

	public String geteTotalBonus() {
		return eTotalBonus;
	}

	public void seteTotalBonus(String eTotalBonus) {
		this.eTotalBonus = eTotalBonus;
	}

	public String getUserIds() {
		return userIds;
	}

	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}
}
