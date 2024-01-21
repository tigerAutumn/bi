package com.pinting.business.model.vo;

/**
 * 运营管理-赞时贷可用债权查询
 * @author SHENGUOPING
 * @date  2017年11月3日 上午10:14:31
 */
public class ZsdAvailableClaimVO {
	
	/*周期*/
	private		String    period;
	/*实际可转*/
	private		Double    availableClaim;
	/*今日VIP*/
	private		Double    vipClaim;
	/*用户站岗*/
	private		Double    userStandAmount;
	
	
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	public Double getAvailableClaim() {
		return availableClaim;
	}
	public void setAvailableClaim(Double availableClaim) {
		this.availableClaim = availableClaim;
	}
	public Double getVipClaim() {
		return vipClaim;
	}
	public void setVipClaim(Double vipClaim) {
		this.vipClaim = vipClaim;
	}
	public Double getUserStandAmount() {
		return userStandAmount;
	}
	public void setUserStandAmount(Double userStandAmount) {
		this.userStandAmount = userStandAmount;
	}
	
}
