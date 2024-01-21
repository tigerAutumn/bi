package com.pinting.open.pojo.response.asset;

import com.pinting.open.base.response.Response;

public class MessageInfoResponse extends Response {

	private String title;
	
	private String pushTime;
	
	private String content;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPushTime() {
		return pushTime;
	}

	public void setPushTime(String pushTime) {
		this.pushTime = pushTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
