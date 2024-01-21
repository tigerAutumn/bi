package com.pinting.business.model.vo;

import java.util.Date;

import com.pinting.business.model.Bs2016AnnualMeetingEmployee;

/**
 * 2016公司年会抽奖列表查询VO
 * Created by shh on 2017/01/13 15:28.
 */
public class AnnualMeetingEmpVO extends Bs2016AnnualMeetingEmployee {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1433400364463636515L;

	/* 是否中奖 */
	private String isWin;
	
	/* 后台抽奖时间 */
	private Date backDrawTime;
	
	/* 奖项 */
	private String content;
	
	/* 奖品id */
	private Integer activityAwardId;
	
	/* 奖品名称 */
	private String note;

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

	public Integer getActivityAwardId() {
		return activityAwardId;
	}

	public void setActivityAwardId(Integer activityAwardId) {
		this.activityAwardId = activityAwardId;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
}
