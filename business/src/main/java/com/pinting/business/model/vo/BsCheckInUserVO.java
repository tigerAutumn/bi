package com.pinting.business.model.vo;

import java.util.Date;

import com.pinting.business.model.Bs2016CheckInUser;

/**
 * 2016客户年终答谢会签到查询VO
 * Created by shh on 2016/11/28 10:03.
 */
public class BsCheckInUserVO extends Bs2016CheckInUser {
	
	private static final long serialVersionUID = 8121140671194254631L;

	/* 注册时间-判断是否注册*/
	private Date registerTime;
	
	/* 是否中奖 */
	private String isWin;
	
	/* 后台抽奖时间 */
	private Date backDrawTime;
	
	/* 奖项 */
	private String content;
	
	/* 是否注册*/
	private String isRegisterTime;
	
	/* 奖品id */
	private Integer activityAwardId;

	public Date getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}

	public String getIsWin() {
		return isWin;
	}

	public void setIsWin(String isWin) {
		this.isWin = isWin;
	}

	public Date getBackDrawTime() {
		return backDrawTime;
	}

	public void setBackDrawTime(Date backDrawTime) {
		this.backDrawTime = backDrawTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getIsRegisterTime() {
		return isRegisterTime;
	}

	public void setIsRegisterTime(String isRegisterTime) {
		this.isRegisterTime = isRegisterTime;
	}

	public Integer getActivityAwardId() {
		return activityAwardId;
	}

	public void setActivityAwardId(Integer activityAwardId) {
		this.activityAwardId = activityAwardId;
	}
	
}
