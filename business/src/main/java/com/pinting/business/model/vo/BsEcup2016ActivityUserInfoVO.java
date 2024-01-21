package com.pinting.business.model.vo;

import org.apache.commons.lang.StringUtils;

import com.pinting.business.model.BsEcup2016ActivityUser;

/**
 * 用户竞猜结果查询及用户助力值查询
 * @author bianyatian
 * @2016-6-21 下午4:07:00
 */
public class BsEcup2016ActivityUserInfoVO extends BsEcup2016ActivityUser {
	
	private Integer supportRanking; //我的助力值排名
	private Integer championRate; //冠军支持率
	private Integer silverRate; //亚军支持率
	private Integer championCount; //选择冠军的数量
	private Integer silverCount; //选择亚军的数量
	private Integer count;//总选择数
	
	private String mobile;//用户手机号
	private Integer luckyNum;//已进入排行榜人数
	
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
	public Integer getChampionCount() {
		return championCount;
	}
	public void setChampionCount(Integer championCount) {
		this.championCount = championCount;
	}
	public Integer getSilverCount() {
		return silverCount;
	}
	public void setSilverCount(Integer silverCount) {
		this.silverCount = silverCount;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public String getMobile() {
		if(StringUtils.isNotBlank(this.mobile)&&this.mobile.length() == 11){
			mobile = mobile.substring(0,3) + "****" +mobile.substring(7);
		}
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Integer getLuckyNum() {
		return luckyNum;
	}
	public void setLuckyNum(Integer luckyNum) {
		this.luckyNum = luckyNum;
	}
	
	
}
