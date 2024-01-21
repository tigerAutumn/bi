package com.pinting.business.model.vo;

import java.util.Date;
import java.util.List;

import com.pinting.business.model.BsUser;

/**
 * 
 * @author yanwl
 * @version $Id: MUserOperateVO.java, v 0.1 2016-02-27 下午12:14:04 yanwl Exp $
 */
public class MUserOperateVO extends BsUser {
    /**  
     * 序列化编号
     */
    private static final long serialVersionUID = -5211979159096736968L;
    
    private int index;
    
    private Integer userId;
    
    private String bankName;
    
    private double investMoney;
    
    private double investTotalMoney;
    
    private int buyTimes;
    
    private Date lastBuyTime;
    
    private String agentName;
	
	private Integer bankId;
	
	private Date sLastBuyTime;
	
	private Date eLastBuyTime;
	
	private Date sFirstBuyTime;
	
	private Date eFirstBuyTime;
	
	private String sBuyTimes;
	
	private String eBuyTimes;
	
	private String sInvestMoney;
	
	private String eInvestMoney;
	
	private String sInvestTotalMoney;
	
	private String eInvestTotalMoney;
	
	private String sTotalBonus;
	
	private String eTotalBonus;
	
	private Date sRegistTime;
	
	private Date eRegistTime;
	
	private List<Object> agentIds;
	
	private String nonAgentId;
	
	private List<Object> userIds;

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public double getInvestMoney() {
		return investMoney;
	}

	public void setInvestMoney(double investMoney) {
		this.investMoney = investMoney;
	}

	public double getInvestTotalMoney() {
		return investTotalMoney;
	}

	public void setInvestTotalMoney(double investTotalMoney) {
		this.investTotalMoney = investTotalMoney;
	}

	public int getBuyTimes() {
		return buyTimes;
	}

	public void setBuyTimes(int buyTimes) {
		this.buyTimes = buyTimes;
	}

	public Date getLastBuyTime() {
		return lastBuyTime;
	}

	public void setLastBuyTime(Date lastBuyTime) {
		this.lastBuyTime = lastBuyTime;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

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

	public String getsBuyTimes() {
		return sBuyTimes;
	}

	public void setsBuyTimes(String sBuyTimes) {
		this.sBuyTimes = sBuyTimes;
	}

	public String geteBuyTimes() {
		return eBuyTimes;
	}

	public void seteBuyTimes(String eBuyTimes) {
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

	public List<Object> getAgentIds() {
		return agentIds;
	}

	public void setAgentIds(List<Object> agentIds) {
		this.agentIds = agentIds;
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

	public List<Object> getUserIds() {
		return userIds;
	}

	public void setUserIds(List<Object> userIds) {
		this.userIds = userIds;
	}
}
