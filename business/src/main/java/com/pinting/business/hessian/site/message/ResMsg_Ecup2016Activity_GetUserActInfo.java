package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;
/**
 * 用户竞猜结果查询 出参
 * @author bianyatian
 * @2016-6-22 下午1:35:37
 */
public class ResMsg_Ecup2016Activity_GetUserActInfo extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4695978136425049281L;
	
	/*用户id*/
    private Integer userId;
    /*用户猜的冠军*/
    private String champion;
    /*用户猜的亚军*/
    private String silver;
    
	private Integer supportRanking; //我的助力值排名,-1暂无排名
	
	private Integer championRate; //冠军支持率
	
	private Integer silverRate; //冠军支持率
	
	private Integer overLuckyNum; //还剩助力值排行榜名额
	
	private Integer supportNum; //助力值

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

	public Integer getSupportRanking() {
		return supportRanking;
	}

	public void setSupportRanking(Integer supportRanking) {
		this.supportRanking = supportRanking;
	}

	public Integer getChampionRate() {
		return championRate;
	}

	public void setChampionRate(Integer championRate) {
		this.championRate = championRate;
	}

	public Integer getSilverRate() {
		return silverRate;
	}

	public void setSilverRate(Integer silverRate) {
		this.silverRate = silverRate;
	}

	public Integer getSupportNum() {
		return supportNum;
	}

	public void setSupportNum(Integer supportNum) {
		this.supportNum = supportNum;
	}

	public Integer getOverLuckyNum() {
		return overLuckyNum;
	}

	public void setOverLuckyNum(Integer overLuckyNum) {
		this.overLuckyNum = overLuckyNum;
	}
	

}
