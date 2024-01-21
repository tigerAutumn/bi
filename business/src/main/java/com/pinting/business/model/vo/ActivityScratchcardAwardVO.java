package com.pinting.business.model.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 刮刮乐获奖名单VO
 * @author SHENGUOPING
 * @date  2017年8月17日 下午5:52:14
 */
public class ActivityScratchcardAwardVO implements Serializable {

	private static final long serialVersionUID = 1650721627359217880L;
	
	/**
	 * 获奖时间
	 */
	private String createTime;
	
	/**
	 * 手机号码
	 */
	private String mobile;
	
	/**
	 * 奖品
	 */
	private String content;

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}
