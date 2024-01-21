package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;
/**
 * 欧洲杯活动-保存用户猜冠亚军记录（检查数据完整）入参
 * @author bianyatian
 * @2016-6-22 下午1:35:35
 */
public class ReqMsg_Ecup2016Activity_SaveUserChampionSilver extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1077588500374468563L;
	/*用户id*/
	private Integer userId;
	/*支持的冠军*/
	private String champion;
	/*支持的亚军*/
	private String silver;
	    
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getChampion() {
		return champion;
	}

	public void setChampion(String champion) {
		this.champion = champion;
	}

	public String getSilver() {
		return silver;
	}

	public void setSilver(String silver) {
		this.silver = silver;
	}
}
