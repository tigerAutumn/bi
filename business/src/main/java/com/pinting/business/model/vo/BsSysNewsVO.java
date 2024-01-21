package com.pinting.business.model.vo;

import com.pinting.business.model.BsSysNews;

public class BsSysNewsVO extends BsSysNews {

	private String mUserName;

	/* 公告发布时间年/月 2017-07 */
	private String publishTimeYM;

	/* 公告发布时间天 24 */
	private String publishTimeD;

	public String getmUserName() {
		return mUserName;
	}

	public void setmUserName(String mUserName) {
		this.mUserName = mUserName;
	}

	public String getPublishTimeYM() {
		return publishTimeYM;
	}

	public void setPublishTimeYM(String publishTimeYM) {
		this.publishTimeYM = publishTimeYM;
	}

	public String getPublishTimeD() {
		return publishTimeD;
	}

	public void setPublishTimeD(String publishTimeD) {
		this.publishTimeD = publishTimeD;
	}
}
